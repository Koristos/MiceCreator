package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDto implements IdPositive {

	private Integer id;
	private Date departureDate;
	private Date arrivalDate;
	private Integer pax;
	private BigDecimal price;
	private Integer tourId;
	private ListItemDto airline;
	private ListItemDto departureAirport;
	private ListItemDto arrivalAirport;
	private BigDecimal total;
}
