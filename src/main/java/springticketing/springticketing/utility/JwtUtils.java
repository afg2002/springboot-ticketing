package springticketing.springticketing.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.User;
import springticketing.springticketing.service.UserService;
import springticketing.springticketing.service.impl.ResponseServiceImpl;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtils extends ResponseServiceImpl {

    @Autowired
    UserService userService;

    private String secret;
    private final long accessTokenValidity = 10000 ; // 1 menit

    @Autowired
    public void setSecret(@Value("${jwt.secret-key}") String secret){
        this.secret = secret;
    }

    public SecretKey getKey(){
        if(secret == null){
            throw new IllegalStateException("JWT Secret is not configured");
        }
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public  String generateJwtToken(String username) {
        return Jwts.builder().subject(username).expiration(new Date(System.currentTimeMillis() +
                accessTokenValidity)).signWith(getKey()).compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
            return true;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }
 }

    public boolean isTokenValid(String token) {
        return token != null && validateToken(token);
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();

            return claims.getSubject();
        } catch (JwtException e) {
            return "Invalid" + e.getMessage();
        }
    }

    public String getUserIdFromToken(String token){
        try {
            String usernameFromToken = getUsernameFromToken(token);
            User user = userService.findByUsername(usernameFromToken);
            return user.getId();
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
