package ru.geekbrains.micecreator.dto.complex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.exceptions.BadInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexParams {

	private static final DateTimeFormatter RANGE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private LocalDate firstDate;
	private LocalDate secondDate;
	private Integer roomId;
	private Integer accTypeId;
	private Integer departureAirportId;
	private Integer arrivalAirportId;
	private Integer airlineId;
	private Integer hotelServId;
	private Integer regionServId;
	private Integer countryId;
	private Integer hotelId;

	public void setFirstDateFromString(String firstDate) {
		this.firstDate = convertRangeDate(firstDate);
	}

	public void setSecondDateFromString(String secondDate){
		this.secondDate = convertRangeDate(secondDate);
	}

	private LocalDate convertRangeDate (String string) {
		try {
			return LocalDate.parse(string, RANGE_DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new BadInputException("Input range dates must follow pattern dd.MM.yyyy");
		}
	}
}
