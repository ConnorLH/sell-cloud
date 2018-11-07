package com.corner.sellproduct.server.controller;

import com.corner.sellproduct.common.dataobject.ProductCategory;
import com.corner.sellproduct.common.dataobject.ProductInfo;
import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import com.corner.sellproduct.server.service.CategoryService;
import com.corner.sellproduct.server.service.ProductService;
import com.corner.sellproduct.server.utils.ResultVOUtil;
import com.corner.sellproduct.server.vo.ProductInfoVO;
import com.corner.sellproduct.server.vo.ProductVO;
import com.corner.sellproduct.server.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @CrossOrigin
    public ResultVO<List<ProductVO>> list(){
        List<ProductInfo> productInfoList = productService.findUpAll();
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> typeList = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        typeList.forEach(t -> {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(t.getCategoryName());
            productVO.setCategoryType(t.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            productInfoList.forEach(info -> {
                if(info.getCategoryType().equals(t.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(info,productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            });
            productVO.setProductInfoVOList(productInfoVOS);
            productVOList.add(productVO);
        });

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取订单的商品列表
     * @param productIdList
     * @return
     */
    @RequestMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputs){
        productService.decreaseStock(decreaseStockInputs);
    }
}
