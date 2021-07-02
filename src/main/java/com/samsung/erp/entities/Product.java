package com.samsung.erp.entities;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    String description;
    String content;
    String thumbnail;
    String screenshots;
    String resources;

    @ManyToOne
    Profile writer;

    String slug;

    @ManyToOne
    ProductCategory productCategory;

    Long createdAt;
    Long updatedAt;
    Boolean archived;
    Boolean published;
    Double originalPrice;
    Double salePrice;
}
