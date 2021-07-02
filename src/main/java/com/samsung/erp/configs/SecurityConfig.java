package com.samsung.erp.configs;

import com.samsung.erp.security.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO configure authentication manager
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO configure web security

        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();

        http
                .csrf()
                .disable()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and()
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/request-recover-password").permitAll()
                .antMatchers(HttpMethod.POST, "/change-password").authenticated()
//                .antMatchers(HttpMethod.GET, "/superadmins", "/superadmins/").hasAuthority("SUPERADMIN")
                .regexMatchers(HttpMethod.GET, "/superadmins/?").hasAuthority("SUPERADMIN")
                .regexMatchers(HttpMethod.POST, "/editors/?").hasAuthority("SUPERADMIN")
                .regexMatchers(HttpMethod.GET, "/editors/?").hasAuthority("SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/admins/root").hasAuthority("SUPERADMIN")
                .antMatchers("/**").permitAll()
//                .antMatchers("/cms").authenticated()
//                .antMatchers("/admin").hasRole("ADMIN")
                .and()
                .httpBasic()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}