package com.samsung.erp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        JWTVerifier jwtVerifier;

        try {
            jwtVerifier = JWT.require(Algorithm.HMAC256(Constants.jwtKey)).build();
            final String authorization = httpRequest.getHeader("Authorization");

            if (authorization != null) {
                String accessToken[] = authorization.split(" ", 0);


                DecodedJWT jwt = jwtVerifier.verify(accessToken[1]);

                if (jwt != null) {
                    String username = jwt.getClaim("username").asString();
                    Long id = jwt.getClaim("id").asLong();
                    String[] roles = jwt.getClaim("roles").asArray(String.class);

                    List<GrantedAuthority> authorities = Stream.of(roles)
                            .map(role -> new GrantedAuthority() {
                                @Override
                                public String getAuthority() {
                                    return role;
                                }
                            }).collect(Collectors.toList());

                    final CustomUser customUser = new CustomUser(id, username, "", authorities);
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser, customUser.getPassword(), customUser.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch(IllegalArgumentException exception){
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } catch(JWTVerificationException exception){
            exception.printStackTrace();
        } catch(AuthenticationException exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
