package com.samsung.erp.services;

import com.samsung.erp.entities.Role;
import com.samsung.erp.repositories.ARepository;
import com.samsung.erp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role createRole(Role role) {
        String newName = role.getName().trim();

        if (newName.isEmpty())
            return null;

        newName = newName.toUpperCase();
        role.setName(newName);

        return roleRepository.createRole(role);
    }

    public Role updateRole(Role role) {
        Role pRole = roleRepository.findById(role.getId());

        if (role.getName() != null) {
            String newName = role.getName().trim();

            if (newName.isEmpty())
                return null;

            newName = newName.toUpperCase();

            pRole.setName(newName);
        }

        if (role.getArchived() != null) {
            pRole.setArchived(role.getArchived());
        }

        return pRole;
    }

    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id);
    }
}