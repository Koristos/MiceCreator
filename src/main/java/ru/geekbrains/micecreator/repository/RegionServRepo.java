package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.models.basic.RegionServ;

import java.util.List;

public interface RegionServRepo extends JpaRepository<RegionServ, Integer> {

	List<RegionServ> findByNameStartingWith(String namePart);

	List<RegionServ> findByRegion(Region region);

	List<RegionServ> findByRegionId(Integer regionId);

	List<RegionServ> findByRegionAndNameStartingWith(Region region, String namePart);

	List<RegionServ> findByRegionIdAndNameStartsWith(Integer regionId, String namePart);
}
