package ru.geekbrains.micecreator.dto.basic.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto extends BasicDto {

	private String description;

}
