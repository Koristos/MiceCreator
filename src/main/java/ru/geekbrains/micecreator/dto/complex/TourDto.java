package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class TourDto implements IdPositive {

	private Integer id;
	private Integer pax;
	private Date startDate;
	private Date endDate;
	private BigDecimal totalPrice;
	private ListItemDto country;

}
