package com.corner.sellproduct.client;

import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String productMsg() {
        return null;
    }

    @Override
    public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
        return null;
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> cartDTOList) {

    }
}
