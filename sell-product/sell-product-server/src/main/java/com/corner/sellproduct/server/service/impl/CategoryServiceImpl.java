package com.corner.sellproduct.server.service.impl;

import com.corner.sellproduct.common.dataobject.ProductCategory;
import com.corner.sellproduct.server.repository.ProductCategoryRepository;
import com.corner.sellproduct.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return productCategoryRepository.findByCategoryTypeIn(categoryType);
    }
}
