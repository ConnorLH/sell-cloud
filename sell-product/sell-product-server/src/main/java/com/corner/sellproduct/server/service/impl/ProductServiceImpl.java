package com.corner.sellproduct.server.service.impl;

import com.corner.sellproduct.common.dataobject.ProductInfo;
import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import com.corner.sellproduct.server.enums.ProductStatusEnum;
import com.corner.sellproduct.server.enums.ResultEnum;
import com.corner.sellproduct.server.exception.ProductException;
import com.corner.sellproduct.server.repository.ProductInfoRepository;
import com.corner.sellproduct.server.service.ProductService;
import com.corner.sellproduct.server.utils.JsonUtil;
import com.google.gson.Gson;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        List<ProductInfo> productInfos = productInfoRepository.findByProductIdIn(productIdList);
        List<ProductInfoOutput> productInfoOutputs = new ArrayList<>();
        productInfos.forEach(item -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(item, productInfoOutput);
            productInfoOutputs.add(productInfoOutput);
        });
        return productInfoOutputs;
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {
        // 先全部扣完库存再发送消息
        List<ProductInfo> productInfos = decreaseStockProcess(decreaseStockInputs);
        List<ProductInfoOutput> productInfoOutputs = productInfos.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        // 发送MQ消息，扣库存
        log.info("发送扣库存消息:{}", JsonUtil.toJson(productInfoOutputs));
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputs));
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfo> productInfos = new ArrayList<>();
        decreaseStockInputs.forEach(item -> {
            Optional<ProductInfo> optional = productInfoRepository.findById(item.getProductId());
            // 判断商品是否存在
            if (!optional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 判断库存是否足够
            ProductInfo productInfo = optional.get();
            int result = productInfo.getProductStock() - item.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfos.add(productInfo);
        });
        return productInfos;
    }
}
