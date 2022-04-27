package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.repository.CountryRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class CountryService {

	@Autowired
	private CountryRepo countryRepo;

	public List<Country> findAll() {
		return countryRepo.findAll();
	}

	public Country findById(Integer id) {
		return countryRepo.findById(id).orElse(null);
	}

	public List<Country> findByNamePart(String namePart) {
		return countryRepo.findByNameStartingWith(namePart);
	}

	public Country addNew(Country newCountry) {
		return countryRepo.save(newCountry);
	}

	public boolean deleteById(Integer id) {
		countryRepo.deleteById(id);
		return !countryRepo.existsById(id);
	}
}
