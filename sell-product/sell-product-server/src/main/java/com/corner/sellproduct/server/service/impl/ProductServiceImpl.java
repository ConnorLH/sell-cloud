package com.corner.sellproduct.server.service.impl;

import com.corner.sellproduct.common.dataobject.ProductInfo;
import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import com.corner.sellproduct.server.enums.ProductStatusEnum;
import com.corner.sellproduct.server.enums.ResultEnum;
import com.corner.sellproduct.server.exception.ProductException;
import com.corner.sellproduct.server.repository.ProductInfoRepository;
import com.corner.sellproduct.server.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        List<ProductInfo> productInfos = productInfoRepository.findByProductIdIn(productIdList);
        List<ProductInfoOutput> productInfoOutputs = new ArrayList<>();
        productInfos.forEach( item -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(item,productInfoOutput);
            productInfoOutputs.add(productInfoOutput);
        });
        return productInfoOutputs;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {
        decreaseStockInputs.forEach(item -> {
            Optional<ProductInfo> optional = productInfoRepository.findById(item.getProductId());
            // 判断商品是否存在
            if(!optional.isPresent()){
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
        });
    }
}
