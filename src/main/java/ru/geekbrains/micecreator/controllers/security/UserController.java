package ru.geekbrains.micecreator.controllers.security;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.service.security.SecurityService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user_list")
@AllArgsConstructor
public class UserController {

	private SecurityService service;

	@GetMapping()
	public List<String> getAllCurrencyTypes() {
		return service.getAllManagerNames();
	}

}
