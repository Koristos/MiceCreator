package ru.geekbrains.micecreator.repository.security;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.micecreator.models.security.Role;

public interface RoleRepo extends CrudRepository<Role, Integer> {
}
