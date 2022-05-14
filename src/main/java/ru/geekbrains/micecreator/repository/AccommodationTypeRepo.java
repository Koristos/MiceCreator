package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.AccommodationType;

import java.util.List;

public interface AccommodationTypeRepo extends JpaRepository<AccommodationType, Integer> {

	@Query("select a from AccommodationType a where a.name LIKE :namePart")
	List<AccommodationType> findByNameStartingWith(@Param("namePart")String namePart);

}
