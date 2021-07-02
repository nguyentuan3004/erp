package com.samsung.erp.controllers;

import com.samsung.erp.entities.ProductCategory;
import com.samsung.erp.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product-categories")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

//    @PostMapping("")
//    public ResponseEntity<ProductCategory> createProductCategory(@RequestParam("name") String name,
//                                                                 @RequestParam(value="parentId", required = false) Long id) {
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setName(name);
//
//        if (id != null) {
//            ProductCategory parent = new ProductCategory();
//            parent.setId(id);
//
//            productCategory.setParent(parent);
//        }
//
//        return ResponseEntity.ok().body(productCategoryService.createProductCategory(productCategory));
//    }

    @PostMapping("")
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.ok().body(productCategoryService.createProductCategory(productCategory));
    }

    @GetMapping("")
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok().body(productCategoryService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductCategory> getProductCategories(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(productCategoryService.findById(id));
    }
}
