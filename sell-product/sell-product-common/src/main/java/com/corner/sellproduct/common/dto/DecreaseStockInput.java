package com.corner.sellproduct.common.dto;

import lombok.Data;

/**
 * 暴露给外部使用
 * 减库存入参
 */
@Data
public class DecreaseStockInput {

    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
