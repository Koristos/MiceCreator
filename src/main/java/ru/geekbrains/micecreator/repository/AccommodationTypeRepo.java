package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.AccommodationType;

import java.util.List;

public interface AccommodationTypeRepo extends JpaRepository<AccommodationType, Integer> {

	List<AccommodationType> findByNameStartingWith(String namePart);

}
