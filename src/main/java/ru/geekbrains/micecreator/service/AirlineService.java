package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Airline;
import ru.geekbrains.micecreator.repository.AirlineRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class AirlineService {

	@Autowired
	private AirlineRepo airlineRepo;

	public List<Airline> findAll() {
		return airlineRepo.findAll();
	}

	public Airline findById(Integer id) {
		return airlineRepo.findById(id).orElse(null);
	}

	public List<Airline> findByNamePart(String namePart) {
		return airlineRepo.findByNameStartingWith(namePart);
	}

	public Airline addNew(Airline newAirline) {
		return airlineRepo.save(newAirline);
	}

	public Airline edit(Airline airline) {
		return airlineRepo.save(airline);
	}

	public boolean deleteById(Integer id) {
		airlineRepo.deleteById(id);
		return !airlineRepo.existsById(id);
	}

}
