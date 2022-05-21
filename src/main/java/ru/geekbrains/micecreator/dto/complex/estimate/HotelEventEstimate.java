package ru.geekbrains.micecreator.dto.complex.estimate;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class HotelEventEstimate {
	private LocalDate eventDate;
	private Integer pax;
	private BigDecimal price;
	private ListItemDto service;
	private ListItemDto hotel;
}
