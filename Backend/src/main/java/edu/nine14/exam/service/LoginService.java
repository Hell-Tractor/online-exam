package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.entity.User;
import edu.nine14.exam.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.ttl}")
    private int expirationTime;
    @Value("${jwt.secret}")
    private String secret;

    public String login(String username, String password) throws FailedLoginException {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new FailedLoginException("User not found");
        if (!user.get().getPassword().equals(password))
            throw new FailedLoginException("Password wrong");
        System.out.println("login: " + username + " " + password);
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime * 1000L * 60L))
                .sign(Algorithm.HMAC256(secret));
    }
}
