package com.jz.nebula.service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * [REF]:https://tools.ietf.org/html/rfc6749
 *
 * @author ZhouJ
 */
@Service
public class RefreshTokenService {
    private Key secretKey;

    private final String refreshTokenHashKeyPostfix = ".refresh.token";

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private TokenService jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor("766e5dc6241769058a9bdae0bec468d9".getBytes());
    }

    /**
     * Create refresh token for current user
     *
     * @param username
     * @param roles
     * @param secretKey
     * @return
     */
    public String createRefreshToken(String username, List<String> roles, Key secretKey) {
        String refreshToken;

        Claims claims = Jwts.claims().setSubject(username + refreshTokenHashKeyPostfix);
        claims.put("roles", roles);
        Date now = new Date();

        refreshToken = Jwts.builder().setClaims(claims).setIssuedAt(now).signWith(secretKey).compact();
        String hashKey = username + refreshTokenHashKeyPostfix;

        template.opsForHash().put(hashKey, hashKey, refreshToken);
        template.expire(hashKey, 15, TimeUnit.MINUTES);

        return refreshToken;
    }

    /**
     * Get refresh token by username
     *
     * @param username
     * @return
     */
    public String getRefreshToken(String username) {
        return (String) template.opsForHash().get(username + refreshTokenHashKeyPostfix,
                username + refreshTokenHashKeyPostfix);
    }

    /**
     * Is refresh token in the redis
     *
     * @param username
     * @param refreshToken
     * @return
     */
    public boolean isRefreshTokenValid(String username, String refreshToken) {
        String redisRefreshToken = getRefreshToken(username);
        return refreshToken.equals(redisRefreshToken);
    }

    /**
     * Refresh token if refresh token expired
     *
     * @param refreshToken
     * @return
     */
    public Map<String, String> refreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            String username = claims.getBody().getSubject().replace(refreshTokenHashKeyPostfix, "");

            if (!isRefreshTokenValid(username, refreshToken)) {
                throw new JwtException("Invalid refresh token");
            }

            User user = userRepository.findByUsername(username).get();

            Map<String, String> tokenMap = jwtTokenProvider.createToken(username, user.getRoles());

            return tokenMap;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get current user refresh token
     *
     * @return
     */
    public String getRefreshToken() {
        String refreshToken = null;
        User user = authenticationFacade.getUser();
        if (user != null) {
            // TODO: Direct type cast !!!
            refreshToken = (String) template.opsForHash().get(user.getUsername(), user.getUsername());
        }
        return refreshToken;
    }

}
