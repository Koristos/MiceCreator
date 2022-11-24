package ru.geekbrains.micecreator.controllers.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.controllers.SimpleTypesController;
import ru.geekbrains.micecreator.service.security.SecurityService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user_list")
@AllArgsConstructor
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	private SecurityService service;

	@GetMapping()
	@Operation(summary = "Запрос всех имен пользователей")
	public List<String> getAllCurrencyTypes() {
		logger.info("User names requested requested");
		return service.getAllManagerNames();
	}

}
