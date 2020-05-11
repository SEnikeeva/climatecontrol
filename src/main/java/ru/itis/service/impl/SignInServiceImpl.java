package ru.itis.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.model.User;

import java.util.Optional;
import ru.itis.repository.UserRepository;
import ru.itis.service.SignInService;


@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDto signIn(SignInDto signInData) {
        // получаем пользователя по его email
        Optional<User> userOptional = usersRepository.findByEmail(signInData.getEmail());
        // если у меня есть этот пользвователь
        if (userOptional.isPresent()) {
            // получаем его
            User user = userOptional.get();
            // если пароль подходит
            if (passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
                // создаем токен
                String token = Jwts.builder()
                        .setSubject(user.getId().toString()) // id пользователя
                        .claim("email", user.getEmail()) // имя
                        .claim("role", user.getRole().name()) // роль
                        .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
                        .compact(); // преобразовали в строку
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");
    }

//    @Override
//    public UserConfirmDto signIn(HttpServletRequest request) {
//        return null;
//    }
}
