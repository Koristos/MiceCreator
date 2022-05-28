package ru.geekbrains.micecreator.dto.basic.full;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;


@Data
@NoArgsConstructor
public class HotelDto extends BasicDto {

	private String description;
	private Integer locationId;
	private String imageOne;
	private String imageTwo;
}
