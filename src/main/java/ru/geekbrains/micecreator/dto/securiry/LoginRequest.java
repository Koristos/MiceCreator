package ru.geekbrains.micecreator.dto.securiry;

import lombok.Data;

@Data
public class LoginRequest {

	private String login;
	private String password;

}
