package com.corner.sellproduct.server.service.impl;

import com.corner.sellproduct.common.dataobject.ProductInfo;
import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import com.corner.sellproduct.server.SellProductApplicationTests;
import com.corner.sellproduct.server.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductServiceImplTest extends SellProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        List<ProductInfo> allList = productService.findUpAll();
        Assert.assertTrue(allList.size()>0);
    }

    @Test
    public void findList() {
        List<ProductInfoOutput> list = productService.findList(Arrays.asList("157875196366160022", "157875227953464068"));
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void decreaseStock() {
        DecreaseStockInput cartDTO = new DecreaseStockInput("157875196366160022",2);
        productService.decreaseStock(Arrays.asList(cartDTO));
    }
}
