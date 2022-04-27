package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Country;

import java.util.List;

public interface CountryRepo extends JpaRepository<Country, Integer> {

	List<Country> findByNameStartingWith(String namePart);
}
