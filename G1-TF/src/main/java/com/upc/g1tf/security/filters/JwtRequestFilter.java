package com.upc.g1tf.security.filters;

import com.upc.g1tf.security.services.CustomUserDetailsService;
import com.upc.g1tf.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 (1)
    JwtRequestFilter es un filtro de seguridad personalizado que se encarga de procesar
    las solicitudes HTTP entrantes para verificar la validez de un token JWT (JSON Web Token).
    Este filtro se ejecuta una vez por cada solicitud y se utiliza para autenticar al usuario
    y establecer el contexto de seguridad en la aplicación.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Extraemos roles directamente desde el JWT
            List<String> roles = jwtUtil.extractRoles(jwt); // Método que definimos en JwtUtil
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Se establece en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            System.out.println("JWT válido para usuario: " + username);
            System.out.println("Roles cargados: " + roles);
        }

        chain.doFilter(request, response);
    }
}
