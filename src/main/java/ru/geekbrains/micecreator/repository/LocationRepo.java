package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Location;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Integer> {

	@Query("select l from Location l where l.name LIKE :namePart")
	List<Location> findByNameStartingWith(@Param("namePart")String namePart);

	List<Location> findByRegionId(Integer regionId);

	@Query("select l from Location l where l.name LIKE :namePart and l.region.id = :regionId")
	List<Location> findByRegionIdAndNameStartsWith(@Param("regionId")Integer regionId, @Param("namePart")String namePart);
}
