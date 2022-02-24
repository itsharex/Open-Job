package org.open.job.starter.security.store.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.json.JSON;
import org.open.job.starter.security.authentication.Authentication;
import org.open.job.starter.security.exception.AuthenticationException;
import org.open.job.starter.security.properties.UserSecurityProperties;
import org.open.job.starter.security.service.UserDetails;
import org.open.job.starter.security.store.TokenStore;
import org.open.job.starter.security.token.Token;

import java.util.Date;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: Jwt token store，把 token 加密传给客户端
 */
@Slf4j
public class JwtTokenStore implements TokenStore {

    private final UserSecurityProperties securityProperties;

    public JwtTokenStore(UserSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public Token generateToken(Authentication authentication) {
        return createJwtToken(authentication.getUserDetails());
    }

    @Override
    public Boolean removeToken(String accessToken) {
        return false;
    }

    @Override
    public Authentication readAuthentication(String accessToken) throws AuthenticationException {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(securityProperties.getSecretKeyBytes()).build().parseClaimsJws(accessToken).getBody();
            final String subject = claims.getSubject();
            UserDetails userDetails = JSON.parse(subject, UserDetails.class);
            Authentication authentication = new Authentication();
            authentication.setUserDetails(userDetails);
            return authentication;
        }catch (Exception e){
            throw new AuthenticationException(e.getMessage());
        }
    }

    private Token createJwtToken(UserDetails userDetails){
        Token token = new Token();
        long now = System.currentTimeMillis();
        String userDetailsStr = JSON.toJSON(userDetails);
        Claims claims = Jwts.claims().setSubject(userDetailsStr);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(now + securityProperties.getAccessTokenExpiresIn()))
                .signWith(Keys.hmacShaKeyFor(securityProperties.getSecretKeyBytes()), SignatureAlgorithm.HS256)
                .compact();

        token.setAccessToken(accessToken);
        return token;
    }


}
