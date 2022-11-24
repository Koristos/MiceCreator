package ru.geekbrains.micecreator.controllers.currency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.service.currency.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("api/v1/currency")
@AllArgsConstructor
@Tag(name = "Валюты", description = "Контроллер для работы с валютами")
public class CurrencyController {

	private CurrencyService service;

	@GetMapping()
	@Operation(summary = "Получение всех используемых типов валют")
	public List<ListItemDto> getAllCurrencyTypes() {
		return service.getAllCurrencyDto();
	}

}
