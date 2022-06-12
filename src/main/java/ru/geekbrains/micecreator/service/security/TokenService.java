package ru.geekbrains.micecreator.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.security.Token;
import ru.geekbrains.micecreator.repository.TokenRepo;

@Service
@RequiredArgsConstructor
public class TokenService {

	@Autowired
	private TokenRepo tokenRepo;

	public void deleteToken(String token){
		Token accToken = this.tokenRepo.findTokenByToken(token);
		if (accToken != null){
			this.tokenRepo.delete(accToken);
		}
	}

	public boolean isExist(String token){
		return this.tokenRepo.existsByToken(token);
	}

	public Token addToken(String token){
		return this.tokenRepo.save(new Token(token));
	}
}
