package com.upc.g1tf.security.controllers;

import com.upc.g1tf.security.dtos.AuthRequestDTO;
import com.upc.g1tf.security.dtos.AuthResponseDTO;
import com.upc.g1tf.security.dtos.ChangePasswordResponse;
import com.upc.g1tf.security.dtos.ChangePasswordRequest;
import com.upc.g1tf.security.entities.User;
import com.upc.g1tf.security.repositories.UserRepository;
import com.upc.g1tf.security.services.CustomUserDetailsService;
import com.upc.g1tf.security.services.PasswordService;
import com.upc.g1tf.security.util.JwtUtil;
import org.hibernate.engine.spi.ManagedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
//@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);
        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }
    @PutMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 1. Verificar contraseña actual
        if (!passwordService.checkOldPassword(user, request.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ChangePasswordResponse("Contraseña actual incorrecta"));
        }

        // 2. Validar nueva contraseña
        if (passwordService.validateNewPassword(request.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ChangePasswordResponse("Nueva contraseña insegura"));
        }

        // 3. Guardar nueva contraseña
        passwordService.updatePassword(user, request.getNewPassword());

        // 4. invalidar tokens anteriores (lista negra)
        // tokenBlacklistService.invalidateUserTokens(user.getId());

        return ResponseEntity.ok(new ChangePasswordResponse("Contraseña actualizada correctamente"));
    }
}