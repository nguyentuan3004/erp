package com.samsung.erp.repositories;

import com.samsung.erp.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepository extends ARepository {
    public Role createRole(Role role) {
        this.getSession().save(role);
        return role;
    }

    public Role findById(Long id) {
        return this.getSession().get(Role.class, id);
    }

    public List<Role> getRoles() {
        Query query = this.getSession().createQuery("FROM Role");
        return query.getResultList();
    }

    public List<Role> getRoles2() {
        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root _role = criteriaQuery.from(Role.class);

        criteriaQuery.select(_role);

        Query query = this.getSession().createQuery(criteriaQuery);

        return query.getResultList();
    }
}
