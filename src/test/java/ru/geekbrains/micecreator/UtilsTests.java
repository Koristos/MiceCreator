package ru.geekbrains.micecreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class UtilsTests {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	@ParameterizedTest
	@ValueSource(strings = { "1", "Test", "." })
	void isNotBlankTest(String income){
		Assertions.assertFalse(AppUtils.isBlank(income));
	}

	@ParameterizedTest
	@ValueSource(strings = {""})
	@NullSource
	void isBlankTest(String income){
		Assertions.assertTrue(AppUtils.isBlank(income));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"01.01.2020, 05.01.2020, 4",
			"29.12.2021, 03.01.2022, 5",
			"01.05.2020, 01.05.2021, 365",
			"15.02.2020, 15.03.2020, 29"
	}, ignoreLeadingAndTrailingWhitespace = true)
	void countDaysDifferenceTest(String first, String second, long difference){
		LocalDate firstDate = LocalDate.parse(first, formatter);
		LocalDate secondDate = LocalDate.parse(second, formatter);
		Assertions.assertEquals(AppUtils.countDaysDifference(firstDate, secondDate), difference);
	}

	@ParameterizedTest
	@CsvSource(value = {
			"hotel, 1, 1, hotel_1_1.jpg",
			"room, 110, 2, room_110_2.jpg",
			"location, 37, 15, location_37_15.jpg"
	}, ignoreLeadingAndTrailingWhitespace = true)
	void createImageNameTest(String type, Integer id, Integer num, String result){
		Assertions.assertEquals(AppUtils.createImageName(type, id, num), result);
	}

	@ParameterizedTest
	@CsvSource(value = {
			"country, 1, 1",
			"currency, 110, 2"
	}, ignoreLeadingAndTrailingWhitespace = true)
	void createImageNameExceptionTest(String type, Integer id, Integer num){
		Assertions.assertThrows(RuntimeException.class, () -> AppUtils.createImageName(type, id, num));
	}
}
