package ru.geekbrains.micecreator.dto.basic.full;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;

@Data
@NoArgsConstructor
public class RoomDto extends BasicDto {

	private String description;
	private Integer hotelId;
	private String imageOne;
	private String imageTwo;

}
