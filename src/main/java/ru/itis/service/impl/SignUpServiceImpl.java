package ru.itis.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserConfirmDto;
import ru.itis.model.Role;
import ru.itis.model.State;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.SendEmailService;
import ru.itis.service.SignUpService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SendEmailService emailService;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDto signUp(MultiValueMap <String, String> formData) {


        userRepository.save( User.builder().email(formData.getFirst("email"))
                .password(encoder.encode(formData.getFirst("password")))
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build());
        Optional<User> userOptional = userRepository.findByEmail(formData.getFirst("email"));


        if (userOptional.isPresent()) {
            // получаем его
            User user = userOptional.get();
            // если пароль подходит
            if (encoder.matches(formData.getFirst("password"), user.getPassword())) {

                UserConfirmDto userConfirmDto = UserConfirmDto.from(user);
                Map<String, Object> root = new HashMap<>();
                root.put("user", user);

//              создаем токен
                emailService.sendCode("Подтверждение", user.getEmail(), root);
                String token = Jwts.builder()
                        .setSubject(user.getId().toString()) // id пользователя
                        .claim("email", user.getEmail()) // имя
                        .claim("role", user.getRole().name()) // роль
                        .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
                        .compact(); // преобразовали в строку
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");
//        return UserConfirmDto.from(userRepository.findByEmail(formData.getFirst("email")).get());
    }

    @Override
    public UserConfirmDto signUp(HttpServletRequest req) {
        return null;
    }

    @Override
    public TokenDto signUp(SignUpForm form) {
        userRepository.save( User.builder().email(form.getEmail())
                .password(encoder.encode(form.getPassword()))
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build());
        Optional<User> userOptional = userRepository.findByEmail(form.getEmail());


        if (userOptional.isPresent()) {
            // получаем его
            User user = userOptional.get();
            // если пароль подходит
            if (encoder.matches(form.getPassword(), user.getPassword())) {

                Map<String, Object> root = new HashMap<>();
                root.put("user", user);

//              создаем токен
                emailService.sendCode("Подтверждение", user.getEmail(), root);
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
}
