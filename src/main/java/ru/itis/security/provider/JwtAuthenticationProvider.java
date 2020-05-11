package ru.itis.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.security.authentification.JwtAuthentication;
import ru.itis.security.defails.UserDetailsImpl;


import java.util.Optional;

@Component(value = "provider")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private UserRepository usersRepository;

    public JwtAuthenticationProvider(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // секретный ключ, которым мы подписываем токен
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        Claims claims;
        try {
             claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }
        Integer id  = Integer.parseInt(claims.get("sub", String.class));
        Optional<User> user = usersRepository.find(id);
        if(user.isPresent()){
            UserDetailsImpl userDetails = UserDetailsImpl.builder()
                    .user(user.get())
                    .userId(id)
                    .role("ROLE_" + claims.get("role", String.class))
                    .name(claims.get("name", String.class))
                    .build();
            authentication.setAuthenticated(true);
            ((JwtAuthentication)authentication).setUserDetails(userDetails);
        }
        return authentication;
    }

    // проверяет, подходит ли текущий провайдер для этой аутентификации
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
