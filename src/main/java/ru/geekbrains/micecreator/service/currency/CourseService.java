package ru.geekbrains.micecreator.service.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.currency.Course;
import ru.geekbrains.micecreator.models.currency.Currency;
import ru.geekbrains.micecreator.repository.currency.CourseRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private CurrencyService currencyService;

	/**
	 * Получение курса валюты из базы по названию валюты
	 * @param currencyName название валюты
	 * @return курс указанной валюты на текущую дату, если есть в базе
	 */
	public Course getCourse (String currencyName){
		Currency currency = currencyService.findCurrencyByName(currencyName);
		List<Course> courses = courseRepo.findByCurrencyAndCourseDate(currency, LocalDate.now());
		if (courses != null && !courses.isEmpty()){
			return courses.get(0);
		}
		return null;
	}

	/**
	 * Получение курса валюты из базы по валюте
	 * @param currency валюта, по которой нужен курс
	 * @return курс указанной валюты на текущую дату, если есть в базе
	 */
	public Course getCourse (Currency currency){
		List<Course> courses = courseRepo.findByCurrencyAndCourseDate(currency, LocalDate.now());
		if (courses != null && !courses.isEmpty()){
			return courses.get(0);
		}
		return null;
	}

	/**
	 * Добавление нового курса в базу по названию валюты
	 * @param currencyName название валюты
	 * @param courseValue курс валюты
	 * @return добавленный курс валюты
	 */
	public Course addCourse (String currencyName, BigDecimal courseValue) {
		Currency currency = currencyService.findCurrencyByName(currencyName);
		return addCourse(currency, courseValue);
	}

	/**
	 * Добавление нового курса в базу по валюте
	 * @param currency валюта, для которой добавляется курс
	 * @param courseValue курс валюты
	 * @return добавленный курс валюты
	 */
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
