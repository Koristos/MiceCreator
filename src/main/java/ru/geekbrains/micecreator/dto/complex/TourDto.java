package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TourDto implements IdPositive {

	private Integer id;
	private Integer pax;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal totalPrice;
	private BigDecimal totalPriceInBasicCurrency;
	private String tourCurrency;
	private ListItemDto country;

}
