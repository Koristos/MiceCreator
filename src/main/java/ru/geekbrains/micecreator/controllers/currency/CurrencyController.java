package ru.geekbrains.micecreator.controllers.currency;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.service.currency.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("api/v1/basic/currency")
@AllArgsConstructor
public class CurrencyController {

	private CurrencyService service;

	@GetMapping()
	public List<ListItemDto> getAllCurrencyTypes() {
		return service.getAllCurrencyDto();
	}

}
