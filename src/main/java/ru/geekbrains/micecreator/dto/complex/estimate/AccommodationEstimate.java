package ru.geekbrains.micecreator.dto.complex.estimate;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccommodationEstimate {
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Integer pax;
	private BigDecimal price;
	private ListItemDto room;
	private ListItemDto accType;
	private ListItemDto hotel;
	private Long nights;
	private Integer roomCount;
}
