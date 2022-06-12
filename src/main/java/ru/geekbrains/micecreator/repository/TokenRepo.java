package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.security.Token;

public interface TokenRepo extends JpaRepository<Token, Integer> {

	boolean existsByToken(String token);

	Token findTokenByToken(String token);
}
