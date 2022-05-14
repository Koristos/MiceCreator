package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Region;

import java.util.List;

public interface RegionRepo extends JpaRepository<Region, Integer> {

	@Query("select r from Region r where r.name LIKE :namePart")
	List<Region> findByNamePart(@Param("namePart")String namePart);

	List<Region> findByCountryId(Integer countryId);

	@Query("select r from Region r where r.name LIKE :namePart and r.country.id = :countryId")
	List<Region> findByCountryIdAndNamePart(@Param("countryId")Integer countryId, @Param("namePart")String namePart);

}
