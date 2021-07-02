package com.samsung.erp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String currency;
    private String icon;
    private Double rate;

    public Currency() {}

    public Currency(String code, String currency, String icon, Double rate) {
        this.code = code;
        this.currency = currency;
        this.icon = icon;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    public String getIcon() {
        return icon;
    }

    public Double getRate() {
        return rate;
    }
}