package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.AccommodationType;
import ru.geekbrains.micecreator.repository.AccommodationTypeRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class AccommodationTypeService {

	@Autowired
	private AccommodationTypeRepo accommodationTypeRepo;

	public List<AccommodationType> findAll() {
		return accommodationTypeRepo.findAll();
	}

	public AccommodationType findById(Integer id) {
		return accommodationTypeRepo.findById(id).orElse(null);
	}

	public List<AccommodationType> findByNamePart(String namePart) {
		return accommodationTypeRepo.findByNameStartingWith(namePart);
	}

	public AccommodationType addNew(AccommodationType newType) {
		return accommodationTypeRepo.save(newType);
	}

	public boolean deleteById(Integer id) {
		accommodationTypeRepo.deleteById(id);
		return !accommodationTypeRepo.existsById(id);
	}

}
