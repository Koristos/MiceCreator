package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.RoomDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Room;
import ru.geekbrains.micecreator.repository.RoomRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService extends SimpleTypeService<RoomDto, Room> {

	@Autowired
	private RoomRepo roomRepo;
	@Autowired
	private final HotelService hotelService;
	private final SimpleTypes simpleType = SimpleTypes.ROOM;

	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getHotelId() != null && !params.getNamePart().isBlank()){
			return findServiceByHotelAndNamePart(params.getHotelId(), params.getNamePart());
		}else if (params.getHotelId() != null) {
			return findServiceByHotel(params.getHotelId());
		}else {
			return super.findBySearchParams(params);
		}
	}

	public List<ListItemDto> findServiceByHotel(Integer hotelId) {
		return findByHotel(hotelId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findServiceByHotelAndNamePart(Integer hotelId, String namePart) {
		return findByHotelAndNamePart(hotelId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}


	protected List<Room> findByHotel(Integer hotelId) {
		checkInputId(hotelId);
		return roomRepo.findByHotelId(hotelId);
	}

	protected List<Room> findByHotelAndNamePart(Integer hotelId, String namePart) {
		checkInputId(hotelId);
		return roomRepo.findByHotelIdAndNameStartsWith(hotelId, nameToStandard(namePart));
	}

	@Override
	protected List<Room> findAll() {
		return roomRepo.findAll();
	}

	@Override
	protected List<Room> findByNamePart(String namePart) {
		return roomRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Room findById(Integer id) {
		return roomRepo.findById(id).orElse(null);
	}

	@Override
	protected Room save(Room room) {
		return roomRepo.save(room);
	}

	@Override
	protected boolean deleteById(Integer id) {
		roomRepo.deleteById(id);
		return !roomRepo.existsById(id);
	}

	@Override
	protected RoomDto mapToDto(Room entity) {
		RoomDto dto = new RoomDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setHotelId(entity.getHotel().getId());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Room entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Room mapToEntity(RoomDto dto) {
		Room entity = new Room();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setHotel(hotelService.findById(dto.getHotelId()));
		return entity;
	}

}
