package com.samsung.erp.services;

import com.samsung.erp.entities.ProductCategory;
import com.samsung.erp.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.createProductCategory(productCategory);
    }

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findById(Long id) {
        return productCategoryRepository.findById(id);
    }
}
