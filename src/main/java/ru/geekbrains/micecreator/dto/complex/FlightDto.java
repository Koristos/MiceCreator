package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FlightDto implements IdPositive {

	private Integer id;
	private LocalDateTime departureDate;
	private LocalDateTime arrivalDate;
	private Integer pax;
	private BigDecimal price;
	private Integer tourId;
	private ListItemDto airline;
	private ListItemDto departureAirport;
	private ListItemDto arrivalAirport;
	private BigDecimal total;
}
