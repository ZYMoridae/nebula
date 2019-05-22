package com.jz.nebula.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

//import javax.annotation.Resource;
//import org.springframework.data.redis.core.ListOperations;
//import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {
    private final Logger logger = LogManager.getLogger(TokenService.class);

    private Key secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor("766e5dc6241769058a9bdae0bec468d9".getBytes());
    }

    /**
     * Create token based on username and roles
     *
     * @param username
     * @param roles
     * @return
     */
    public Map<String, String> createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(secretKey)
                .compact();

        template.opsForHash().put(username, username, token);
        template.expire(username, 15, TimeUnit.MINUTES);

        String refreshToken = refreshTokenService.createRefreshToken(username, roles, secretKey);
        Map<String, String> tokenMap = new ConcurrentHashMap<>();
        tokenMap.put("accessToken", token);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }

    /**
     * Get authentication object according to token
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Get username
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolve token from request
     *
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Validate token
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            // This logic is to suppress exception when public endpoints are accessed
            if (token.equals("null")) {
                return false;
            }

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            Object redisToken = template.opsForHash().get(username, username);

            logger.info("The redis access token is: [{}]", redisToken);

            if (redisToken == null || !token.equals(redisToken)) {
                return false;
            }

            logger.info("Valid token received");

            // TODO: Make expired time configurable
            template.expire(username, 15, TimeUnit.MINUTES);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("Expired or Invalid JWT access token");
            return false;
        }
    }
}
