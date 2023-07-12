package com.example.pjtraining.auth;

import com.example.pjtraining.config.JwtService;
import com.example.pjtraining.loginHistory.LoginHistory;
import com.example.pjtraining.loginHistory.LoginHistoryRepository;
import com.example.pjtraining.token.Token;
import com.example.pjtraining.token.TokenRepository;
import com.example.pjtraining.token.TokenType;
import com.example.pjtraining.user.Role;
import com.example.pjtraining.user.User;
import com.example.pjtraining.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private final HttpServletRequest request;

    public <T> T registerUser(RegisterRequest request) {


        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        user.add(insertLoginHistory());

        try {
            var savedUser = repository.save(user);
            var accessToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, accessToken);
            T repose_token =(T) AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            return repose_token;
        }
        catch (ConstraintViolationException ex) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.add(violation.getMessage());
            }

            return (T) errors;
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws IOException {


        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(
                userToken
        );


        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();


        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);


        user.add(insertLoginHistory());
        repository.save(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }



    private void revokeAllUserTokens(User user) {

        System.out.println("validUserTokens");
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());


        if(validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow(() -> new IOException("User Not Found"));

            if(jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }

    public LoginHistory insertLoginHistory() {
        var loginHistory = LoginHistory.builder()
                .loginTime(LocalDateTime.now())
                .ipAddress(getIpAddress())
                .userAgent(getUserAgent())
                .build();

        return loginHistory;
    }

    public String getIpAddress() {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public String getUserAgent() {
        return request.getHeader("User-Agent");
    }
}
