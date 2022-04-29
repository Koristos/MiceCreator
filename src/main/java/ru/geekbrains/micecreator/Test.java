//package ru.geekbrains.micecreator;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import ru.geekbrains.micecreator.dto.basic.full.AccommTypeDto;
//import ru.geekbrains.micecreator.service.basic.AccommodationTypeService;
//import ru.geekbrains.micecreator.service.listItem.ListItemService;

//@Component
//@Data
//@AllArgsConstructor
//public class Test {
//	@Autowired
//	AccommodationTypeService accommodationTypeService;
//	@Autowired
//	ListItemService listItemService;
//
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void runAfterStartup() {
//		AccommTypeDto typeById = accommodationTypeService.findTypeById(1);
//		//ListItemDto listItemDto = listItemService.convertToListItemDto(typeById);
//
//		System.out.println("Yaaah, I am running........");
//	}
//}
