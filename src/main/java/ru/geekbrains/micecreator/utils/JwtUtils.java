package ru.geekbrains.micecreator.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.geekbrains.micecreator.exceptions.AuthorizationException;

import java.util.Date;

@Component
public class JwtUtils {

	@Value("${micecreator.security.jwtSecret}")
	private String jwtSecret;
	@Value("${micecreator.security.jwtExpirationMs}")
	private int jwtExpirationMs;

	/**
	 * Генерация JWT-токена
	 * @param authentication текущие данные авторизации
	 * @return JWT-токен
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * Извлекает логин пользователя из JWT-токена
	 * @param token JWT-токен
	 * @return логин пользователя
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Валидация JWT-токена
	 * @param authToken JWT-токен
	 * @return true, если прошел валидацию
	 * @exception AuthorizationException если валидация не пройдена
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			throw new AuthorizationException(String.format("Invalid JWT signature: %s", e.getMessage()));
		} catch (MalformedJwtException e) {
			throw new AuthorizationException(String.format("Invalid JWT token: %s", e.getMessage()));
		} catch (ExpiredJwtException e) {
			throw new AuthorizationException(String.format("JWT token is expired: %s", e.getMessage()));
		} catch (UnsupportedJwtException e) {
			throw new AuthorizationException(String.format("JWT token is unsupported: %s", e.getMessage()));
		} catch (IllegalArgumentException e) {
			throw new AuthorizationException(String.format("JWT claims string is empty: %s", e.getMessage()));
		}
	}
}
