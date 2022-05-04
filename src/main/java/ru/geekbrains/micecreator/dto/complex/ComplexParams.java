package ru.geekbrains.micecreator.dto.complex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexParams {

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

}
