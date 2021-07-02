package com.samsung.erp.repositories;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.Role;
import com.samsung.erp.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProfileRepository extends ARepository {
    public Profile createProfile(Profile profile) {
        this.getSession().persist(profile);
        return profile;
    }

    public List<Profile> findAll(String roleName) {
        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Profile.class);

        Root _profile = criteriaQuery.from(Profile.class);

        if (roleName != null) {
            Join<Profile, Role> _role = (Join)_profile.join("roles");
            criteriaQuery.where(criteriaBuilder.equal(_role.get("name"), roleName));
        }

        return this.getSession().createQuery(criteriaQuery).getResultList();
    }

    public Profile findById(Long id) {
        return this.getSession().get(Profile.class, id);
    }

    public Profile findByIdAndRoleName(Long id, String roleName) {
        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Profile.class);

        Root _profile = criteriaQuery.from(Profile.class);

        criteriaQuery.where(criteriaBuilder.equal(_profile.get("id"), id));

        if (roleName != null) {
            Join<Profile, Role> _role = (Join)_profile.join("roles");
            criteriaQuery.where(criteriaBuilder.equal(_role.get("name"), roleName));
        }

        return (Profile) this.getSession().createQuery(criteriaQuery).getSingleResult();
    }
}
