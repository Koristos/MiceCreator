package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.repository.RegionRepo;

import java.util.List;


@Service
@AllArgsConstructor
@Data
public class RegionService {

	@Autowired
	private RegionRepo regionRepo;

	public List<Region> findAll() {
		return regionRepo.findAll();
	}

	public Region findById(Integer id) {
		return regionRepo.findById(id).orElse(null);
	}

	public List<Region> findByNamePart(String namePart) {
		return regionRepo.findByNameStartingWith(namePart);
	}

	public List<Region> findByCountry(Country country) {
		return regionRepo.findByCountry(country);
	}

	public List<Region> findByCountry(Integer countryId) {
		return regionRepo.findByCountryId(countryId);
	}

	public List<Region> findByCountryAndNamePart(Country country, String namePart) {
		return regionRepo.findByCountryAndNameStartingWith(country, namePart);
	}

	public List<Region> findByCountryAndNamePart(Integer countryId, String namePart) {
		return regionRepo.findByCountryIdAndNameStartsWith(countryId, namePart);
	}

	public Region addNew(Region newRegion) {
		return regionRepo.save(newRegion);
	}

	public Region edit(Region region) {
		return regionRepo.save(region);
	}

	public boolean deleteById(Integer id) {
		regionRepo.deleteById(id);
		return !regionRepo.existsById(id);
	}

}
