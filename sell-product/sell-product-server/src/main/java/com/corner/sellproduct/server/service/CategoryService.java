package com.corner.sellproduct.server.service;


import com.corner.sellproduct.common.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
