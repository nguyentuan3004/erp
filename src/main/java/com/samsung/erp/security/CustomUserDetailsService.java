package com.samsung.erp.security;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.User;
import com.samsung.erp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User pUser = userRepository.findByEmail(username);
        Profile profile = pUser.getProfile();

        List<GrantedAuthority> authorities = profile.getRoles().stream()
                .map(role -> new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.getName();
            }
        }).collect(Collectors.toList());

        if (pUser == null)
            throw new UsernameNotFoundException(username);

        return new CustomUser(profile.getId(), pUser.getEmail(), pUser.getPassword(), authorities);
    }
}
