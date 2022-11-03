package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.full.CountryDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.repository.CountryRepo;
import ru.geekbrains.micecreator.service.currency.CurrencyService;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryService extends SimpleTypeService<CountryDto, Country> {

	@Autowired
	private final CountryRepo countryRepo;
	private final SimpleTypes simpleType = SimpleTypes.COUNTRY;
	@Autowired
	private final CurrencyService currencyService;

	@Override
	protected List<Country> findAll() {
		return countryRepo.findAll();
	}

	@Override
	protected List<Country> findByNamePart(String namePart) {
		return countryRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Country findById(Integer id) {
		return countryRepo.findById(id).orElse(null);
	}

	@Override
	protected Country save(Country newCountry) {
		return countryRepo.save(newCountry);
	}

	@Override
	protected boolean deleteById(Integer id) {
		countryRepo.deleteById(id);
		return !countryRepo.existsById(id);
	}

	@Override
	protected CountryDto mapToDto(Country entity) {
		CountryDto dto = new CountryDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCurrency(entity.getCurrency().getName());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Country entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Country mapToEntity(CountryDto dto) {
		Country entity = new Country();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCurrency(currencyService.findCurrencyByName(dto.getCurrency()));
		return entity;
	}
}
