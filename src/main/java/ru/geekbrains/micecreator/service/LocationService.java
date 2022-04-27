package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Location;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.repository.LocationRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class LocationService {

	@Autowired
	private LocationRepo locationRepo;

	public List<Location> findAll() {
		return locationRepo.findAll();
	}

	public Location findById(Integer id) {
		return locationRepo.findById(id).orElse(null);
	}

	public List<Location> findByNamePart(String namePart) {
		return locationRepo.findByNameStartingWith(namePart);
	}

	public List<Location> findByRegion(Region region) {
		return locationRepo.findByRegion(region);
	}

	public List<Location> findByRegion(Integer regionId) {
		return locationRepo.findByRegionId(regionId);
	}

	public List<Location> findByRegionAndNamePart(Region region, String namePart) {
		return locationRepo.findByRegionAndNameStartingWith(region, namePart);
	}

	public List<Location> findByRegionAndNamePart(Integer regionId, String namePart) {
		return locationRepo.findByRegionIdAndNameStartsWith(regionId, namePart);
	}

	public Location addNew(Location newLocation) {
		return locationRepo.save(newLocation);
	}

	public Location edit(Location location) {
		return locationRepo.save(location);
	}

	public boolean deleteById(Integer id) {
		locationRepo.deleteById(id);
		return !locationRepo.existsById(id);
	}
}
