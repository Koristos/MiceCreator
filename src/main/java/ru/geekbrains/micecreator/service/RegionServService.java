package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.models.basic.RegionServ;
import ru.geekbrains.micecreator.repository.RegionServRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class RegionServService {

	@Autowired
	private RegionServRepo regionServRepo;


	public List<RegionServ> findAll() {
		return regionServRepo.findAll();
	}

	public RegionServ findById(Integer id) {
		return regionServRepo.findById(id).orElse(null);
	}

	public List<RegionServ> findByNamePart(String namePart) {
		return regionServRepo.findByNameStartingWith(namePart);
	}

	public List<RegionServ> findByRegion(Region region) {
		return regionServRepo.findByRegion(region);
	}

	public List<RegionServ> findByRegion(Integer regionId) {
		return regionServRepo.findByRegionId(regionId);
	}

	public List<RegionServ> findByRegionAndNamePart(Region region, String namePart) {
		return regionServRepo.findByRegionAndNameStartingWith(region, namePart);
	}

	public List<RegionServ> findByRegionAndNamePart(Integer regionId, String namePart) {
		return regionServRepo.findByRegionIdAndNameStartsWith(regionId, namePart);
	}

	public RegionServ addNew(RegionServ newRegionServ) {
		return regionServRepo.save(newRegionServ);
	}

	public RegionServ edit(RegionServ service) {
		return regionServRepo.save(service);
	}

	public boolean deleteById(Integer id) {
		regionServRepo.deleteById(id);
		return !regionServRepo.existsById(id);
	}
}
