package ru.geekbrains.micecreator.controllers.security;

import lombok.AllArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.securiry.LoginRequest;
import ru.geekbrains.micecreator.dto.securiry.UserDto;
import ru.geekbrains.micecreator.service.security.SecurityService;
import ru.geekbrains.micecreator.service.security.TokenService;
import ru.geekbrains.micecreator.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class SecurityController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	SecurityService securityService;
	@Autowired
	TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		tokenService.addToken(jwt);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		UserDto userDto = securityService.getUserInfo(userDetails.getUsername());
		userDto.setToken(jwt);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String token = parseJwt(request);
		tokenService.deleteToken(token);
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
		return ResponseEntity.ok("Выход успешно выполнен");
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}

}
