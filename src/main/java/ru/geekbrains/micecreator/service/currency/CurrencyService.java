package ru.geekbrains.micecreator.service.currency;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.currency.Currency;
import ru.geekbrains.micecreator.repository.currency.CurrencyRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService {

	@Autowired
	private CurrencyRepo currencyRepo;

	public Currency addCurrency (String currency) {
		List<Currency> currencies = currencyRepo.findByName(currency);
		if (currencies == null || currencies.isEmpty()) {
			Currency newCur = new Currency();
			newCur.setName(currency);
			currencyRepo.save(newCur);
		}
		return findCurrencyByName(currency);
	}

	public Currency findCurrencyByName (String name) {
		List<Currency> currencies = currencyRepo.findByName(name);
		if (currencies == null || currencies.size() != 1) {
			throw new RuntimeException("No currency found by name or there is more then one record");
		}
		return currencies.get(0);
	}

	public List<Currency> findAllCurrencies (){
		return currencyRepo.findAll();
	}

	public List<ListItemDto> getAllCurrencyDto () {
		return findAllCurrencies().stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}



	private ListItemDto mapToListItemDto(Currency entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(SimpleTypes.CURRENCY);
		return dto;
	}

}
