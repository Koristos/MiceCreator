package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Location;
import ru.geekbrains.micecreator.models.basic.Region;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Integer> {

	List<Location> findByNameStartingWith(String namePart);

	List<Location> findByRegion(Region region);

	List<Location> findByRegionId(Integer regionId);

	List<Location> findByRegionAndNameStartingWith(Region region, String namePart);

	List<Location> findByRegionIdAndNameStartsWith(Integer regionId, String namePart);
}
