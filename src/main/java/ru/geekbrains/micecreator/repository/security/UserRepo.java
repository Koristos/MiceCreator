package ru.geekbrains.micecreator.repository.security;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.micecreator.models.security.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
