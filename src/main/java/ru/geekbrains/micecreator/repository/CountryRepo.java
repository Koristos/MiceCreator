package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Country;

import java.util.List;

public interface CountryRepo extends JpaRepository<Country, Integer> {

	@Query("select c from Country c where c.name LIKE :namePart")
	List<Country> findByNameStartingWith(@Param("namePart")String namePart);

}
