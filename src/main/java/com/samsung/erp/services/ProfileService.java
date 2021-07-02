package com.samsung.erp.services;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public List<Profile> findAll(String roleName) {
        return profileRepository.findAll(roleName);
    }

    public Profile getSuperAdmin(Long id) {
        return profileRepository.findById(id);
    }

    public Profile findByIdAndRoleName(Long id, String roleName) {
        return profileRepository.findByIdAndRoleName(id, roleName);
    }
}
