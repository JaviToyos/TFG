package com.tfg.gestorcuentas.security;

import com.tfg.gestorcuentas.utils.MessageErrors;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final static String HEADER = "Authorization";
    private final static String PREFIX = "Bearer ";
    private final static String SECRET = "mySecretKey";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {

        final String origin = request.getHeader("Origin");
        final String method = request.getMethod();

        if (origin != null && isAllowedRequestOrigin(origin)) {

            response.setHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", method);
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        }
        try {
            if (existeJWTToken(request, response)) {
                final Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (final ExpiredJwtException expiredJwtException) {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, expiredJwtException.getMessage());
            throw new ServletException(MessageErrors.JWT_EXPIRED.getErrorCode());
        } catch (final UnsupportedJwtException unsupportedJwtException) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, unsupportedJwtException.getMessage());
            throw new ServletException(MessageErrors.JWT_NOT_ALLOWED.getErrorCode());
        } catch (final MalformedJwtException malformedJwtException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, malformedJwtException.getMessage());
            throw new ServletException(MessageErrors.JWT_MALFORMED.getErrorCode());
        }
    }

    private Claims validateToken(final HttpServletRequest request) {
        final String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(final Claims claims) {
        final List<String> authorities = (List) claims.get("authorities");

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean existeJWTToken(final HttpServletRequest request, final HttpServletResponse res) {
        final String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
            return false;
        }

        return true;
    }

    private boolean isAllowedRequestOrigin(final String origin) {
        return origin.matches(".*");
    }

}