package ru.geekbrains.micecreator.dto.complex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.exceptions.BadInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexParams {

	private static final DateTimeFormatter RANGE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private Date firstDate;
	private Date secondDate;
	private Integer roomId;
	private Integer accTypeId;
	private Integer departureAirportId;
	private Integer arrivalAirportId;
	private Integer airlineId;
	private Integer hotelServId;
	private Integer regionServId;
	private Integer countryId;

	public void setFirstDate(String firstDate) {
		this.firstDate = convertRangeDate(firstDate);
	}

	public void setSecondDate(String secondDate){
		this.secondDate = convertRangeDate(secondDate);
	}

	private Date convertRangeDate (String string) {
		try {
			LocalDate date = LocalDate.parse(string, RANGE_DATE_FORMATTER);
			return java.sql.Date.valueOf(date);
		} catch (DateTimeParseException e) {
			throw new BadInputException("Input range dates must follow pattern dd.MM.yyyy");
		}
	}
}
