package com.tfg.gestorcuentas.service.login.model;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetJWTToken {

    private static final String SECRET_KEY = "mySecretKey";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String SOFTTEK_JWT = "softtekJWT";
    private static final String AUTHORITIES = "authorities";
    private static final String BEARER = "Bearer ";

    public GetJWTToken() {
    }


    public String getJWTToken(final String username) {
        final String secretKey = SECRET_KEY;
        final List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_USER);

        final String token = Jwts.builder().setId(SOFTTEK_JWT).setSubject(username)
                .claim(AUTHORITIES,
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return BEARER + token;
    }
}