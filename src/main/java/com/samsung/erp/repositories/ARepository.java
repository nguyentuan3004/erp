package com.samsung.erp.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

public abstract class ARepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    protected Session getSession() {
        return sessionFactory.getObject().getCurrentSession();
    }
}

