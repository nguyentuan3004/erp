package com.samsung.erp.repositories;

import com.samsung.erp.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;


@Repository
public class UserRepository extends ARepository {
    public User findByEmail(String email) {
        Query query = this.getSession().createQuery("FROM User WHERE email=:email");
        query.setParameter("email", email);

        return (User)query.getSingleResult();
    }

    public User createUser(User user) {
        this.getSession().save(user);
        return user;
    }

    public User findById(Long id) {
        return this.getSession().get(User.class, id);
    }
}
