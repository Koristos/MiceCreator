package ru.geekbrains.micecreator.dto.securiry;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.security.Role;
import ru.geekbrains.micecreator.models.security.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {
	private String name;
	private List<String> role = new ArrayList<>();
	private String token;

	public UserDto(User user) {
		this.name = user.getManager();
		this.role = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
	}
}
