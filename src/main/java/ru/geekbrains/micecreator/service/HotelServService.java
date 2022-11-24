package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.HotelServ;
import ru.geekbrains.micecreator.repository.HotelServRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;
import ru.geekbrains.micecreator.utils.PathUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelServService extends SimpleTypeService<HotelServiceDto, HotelServ> {

	@Autowired
	private final HotelServRepo hotelServRepo;
	@Autowired
	private final HotelService hotelService;
	@Autowired
	private final PathUtils pathUtils;
	private final SimpleTypes simpleType = SimpleTypes.HOTEL_SERVICE;

	/**
	 * Метод ищет Отельные услуги по параметрам:
	 * - по id Отеля, если указан
	 * - по части имени, если указано
	 * - если параметры пустые, ищет без ограничений
	 * @param params параметры для поиска
	 * @return список отельных услуг в виде DTO
	 */
	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getHotelId() != null && !AppUtils.isBlank(params.getNamePart())){
			return findServiceByHotelAndNamePart(params.getHotelId(), params.getNamePart());
		}else if (params.getHotelId() != null) {
			return findServiceByHotel(params.getHotelId());
		}else {
			return super.findBySearchParams(params);
		}
	}

	protected List<ListItemDto> findServiceByHotel(Integer hotelId) {
		checkInputId(hotelId);
		return findByHotel(hotelId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findServiceByHotelAndNamePart(Integer hotelId, String namePart) {
		checkInputId(hotelId);
		return findByHotelAndNamePart(hotelId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public Integer findParentId (Integer id) {
		return findById(id).getHotel().getId();
	}


	protected List<HotelServ> findByHotel(Integer hotelId) {
		return hotelServRepo.findByHotelId(hotelId);
	}

	protected List<HotelServ> findByHotelAndNamePart(Integer hotelId, String namePart) {
		return hotelServRepo.findByHotelIdAndNameStartsWith(hotelId, nameToStandard(namePart));
	}

	@Override
	protected List<HotelServ> findAll() {
		return hotelServRepo.findAll();
	}

	@Override
	protected List<HotelServ> findByNamePart(String namePart) {
		return hotelServRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected HotelServ findById(Integer id) {
		return hotelServRepo.findById(id).orElse(null);
	}

	@Override
	protected HotelServ save(HotelServ service) {
		return hotelServRepo.save(service);
	}

	@Override
	protected boolean deleteById(Integer id) {
		hotelServRepo.deleteById(id);
		return !hotelServRepo.existsById(id);
	}

	@Override
	protected HotelServiceDto mapToDto(HotelServ entity) {
		HotelServiceDto dto = new HotelServiceDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setHotelId(entity.getHotel().getId());
		dto.setImageOne(entity.getImageOne());
		dto.setImageTwo(entity.getImageTwo());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(HotelServ entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected HotelServ mapToEntity(HotelServiceDto dto) {
		HotelServ entity = new HotelServ();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setHotel(hotelService.findById(dto.getHotelId()));
		if (AppUtils.isBlank(dto.getImageOne()) || dto.getId() == null || !isImageExist(dto.getId(), 1)) {
			entity.setImageOne(null);
		} else {
			entity.setImageOne(AppUtils.createImageName("hotel_service", dto.getId(), 1));
		}
		if (AppUtils.isBlank(dto.getImageTwo()) || dto.getId() == null || !isImageExist(dto.getId(), 2)) {
			entity.setImageTwo(null);
		} else {
			entity.setImageTwo(AppUtils.createImageName("hotel_service", dto.getId(), 2));
		}
		return entity;
	}
	private boolean isImageExist(Integer entityId, Integer imageNum) {
		return pathUtils.isImageExist(AppUtils.createImageName("hotel_service", entityId, imageNum));
	}


}
