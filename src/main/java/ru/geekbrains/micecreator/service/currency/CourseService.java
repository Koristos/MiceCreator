package ru.geekbrains.micecreator.service.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.currency.Course;
import ru.geekbrains.micecreator.models.currency.Currency;
import ru.geekbrains.micecreator.repository.currency.CourseRepo;
import ru.geekbrains.micecreator.schedule.ScheduleList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private CurrencyService currencyService;

	public Course getCourse (String currencyName){
		Currency currency = currencyService.findCurrencyByName(currencyName);
		List<Course> courses = courseRepo.findByCurrencyAndCourseDate(currency, LocalDate.now());
		if (courses != null && !courses.isEmpty()){
			return courses.get(0);
		}
		return null;
	}

	public Course getCourse (Currency currency){
		List<Course> courses = courseRepo.findByCurrencyAndCourseDate(currency, LocalDate.now());
		if (courses != null && !courses.isEmpty()){
			return courses.get(0);
		}
		return null;
	}

	public Course addCourse (String currencyName, BigDecimal courseValue) {
		Currency currency = currencyService.findCurrencyByName(currencyName);
		return addCourse(currency, courseValue);
	}

	public Course addCourse (Currency currency, BigDecimal courseValue) {
		Course course = getCourse(currency);
		if (course != null) {
			return course;
		}
		course = new Course();
		course.setCourseDate(LocalDate.now());
		course.setCurrency(currency);
		course.setRate(courseValue);
		return courseRepo.save(course);
	}
}
