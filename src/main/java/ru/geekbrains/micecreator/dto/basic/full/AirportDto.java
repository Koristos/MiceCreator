package ru.geekbrains.micecreator.dto.basic.full;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;

@Data
@NoArgsConstructor
public class AirportDto extends BasicDto {

	private String code;
	private Integer regionId;

}
