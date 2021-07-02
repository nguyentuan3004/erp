package com.samsung.erp.repositories;

import com.samsung.erp.entities.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryRepository extends ARepository{
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        this.getSession().save(productCategory);
        return productCategory;
    }

    public List<ProductCategory> findAll() {
        return this.getSession().createQuery("FROM ProductCategory").getResultList();
    }

    public ProductCategory findById(Long id) {
        return this.getSession().get(ProductCategory.class, id);
    }
}
