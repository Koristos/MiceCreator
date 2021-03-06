package ru.geekbrains.micecreator.dto.basic.full;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;

@Data
@NoArgsConstructor
public class RegionDto extends BasicDto {

	private Integer countryId;
	private String imageOne;
}
