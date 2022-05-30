package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.full.RoomDto;

import java.nio.file.Path;

@Data
@NoArgsConstructor
public class AccRoom {
	private String name;
	private String desc;
	private Path imageOneLink;
	private Path imageTwoLink;

	public AccRoom(RoomDto room) {
		this.name = room.getName();
		this.desc = room.getDescription();
	}

	@Override
	public int hashCode() {
		return "AccRoom".hashCode() + this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.hashCode() == o.hashCode();
	}
}
