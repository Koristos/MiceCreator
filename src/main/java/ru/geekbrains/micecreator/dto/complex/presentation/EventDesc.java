package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;

import java.nio.file.Path;

@Data
@NoArgsConstructor
public class EventDesc {
	private String name;
	private String desc;
	private Path imageOneLink;
	private Path imageTwoLink;

	public EventDesc(HotelServiceDto service) {
		this.name = service.getName();
		this.desc = service.getDescription();
	}

	public EventDesc(RegionServiceDto service) {
		this.name = service.getName();
		this.desc = service.getDescription();
	}
}
