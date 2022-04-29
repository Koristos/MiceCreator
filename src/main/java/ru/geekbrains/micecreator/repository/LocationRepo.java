package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Location;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Integer> {

	List<Location> findByNameStartingWith(String namePart);

	List<Location> findByRegionId(Integer regionId);

	List<Location> findByRegionIdAndNameStartsWith(Integer regionId, String namePart);
}
