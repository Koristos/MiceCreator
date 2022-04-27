package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Airport;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.repository.AirportRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class AirportService {

	@Autowired
	private AirportRepo airportRepo;

	public List<Airport> findAll() {
		return airportRepo.findAll();
	}

	public Airport findById(Integer id) {
		return airportRepo.findById(id).orElse(null);
	}

	public List<Airport> findByNamePart(String namePart) {
		return airportRepo.findByNameStartingWith(namePart);
	}

	public List<Airport> findByRegion(Region region) {
		return airportRepo.findByRegion(region);
	}

	public List<Airport> findByRegion(Integer regionId) {
		return airportRepo.findByRegionId(regionId);
	}

	public Airport findByCode(String code) {
		List<Airport> list = airportRepo.findByCode(code);
		if (list.size() != 1) {
			throw new RuntimeException("База не консистентна!");
		}
		return list.get(0);
	}

	public Airport addNew(Airport newAirport) {
		return airportRepo.save(newAirport);
	}

	public boolean deleteById(Integer id) {
		airportRepo.deleteById(id);
		return !airportRepo.existsById(id);
	}

}
