package ru.geekbrains.micecreator.dto.complex.estimate;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FlightEstimate {

	private LocalDateTime departureDate;
	private LocalDateTime arrivalDate;
	private Integer pax;
	private BigDecimal price;
	private ListItemDto airline;
	private ListItemDto departureAirport;
	private ListItemDto arrivalAirport;
}
