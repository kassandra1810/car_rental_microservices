package github.kassandra1810.user_service.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

//    @PostMapping("/login")
//    public String login(@RequestBody Credentials credentials) {
//        long currentTimeMillis = System.currentTimeMillis();
//        return Jwts.builder()
//                .setSubject(credentials.getLogin())
//                .claim("roles", "user")
//                .setIssuedAt(new Date(currentTimeMillis))
//                .setExpiration(new Date(currentTimeMillis + 20000))
//                .signWith(SignatureAlgorithm.HS512, credentials.getPassword())
//                .compact();
//    }
}
