package ru.geekbrains.micecreator.dto.basic.list;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListItemDto {

	private Integer id;
	private String name;
	private SimpleTypes itemType;

}
