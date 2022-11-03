package ru.geekbrains.micecreator.repository.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.currency.Course;
import ru.geekbrains.micecreator.models.currency.Currency;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Integer> {

	List<Course> findByCurrencyAndCourseDate (Currency currency, LocalDate date);
}
