package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Region;

import java.util.List;

public interface RegionRepo extends JpaRepository<Region, Integer> {

	List<Region> findByNameStartingWith(String namePart);

	List<Region> findByCountryId(Integer countryId);

	List<Region> findByCountryIdAndNameStartsWith(Integer countryId, String namePart);

}
