package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccommodationDto implements IdPositive {

	private Integer id;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Integer pax;
	private BigDecimal price;
	private Integer tourId;
	private ListItemDto room;
	private ListItemDto accType;
	private ListItemDto hotel;
	private Long nights;
	private Integer roomCount;
	private BigDecimal total;

}
