package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class HotelEventDto implements IdPositive {

	private Integer id;
	private Date date;
	private Integer pax;
	private BigDecimal price;
	private Integer tourId;
	private ListItemDto service;

}
