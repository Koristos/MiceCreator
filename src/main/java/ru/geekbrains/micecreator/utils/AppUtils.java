package ru.geekbrains.micecreator.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public final class AppUtils {

	private AppUtils(){};

	private static final List<String> IMAGE_ENTITIES = List.of("hotel", "hotel_service", "region_service", "room", "region", "location");
	private static final StringBuilder sb = new StringBuilder();
	private static final Path IMAGE_DIR = Paths.get("C:/MiceCreator/images");

	public static boolean isBlank(String string){
		if (string == null){
			return true;
		}
		return string.isBlank();
	}

	public static Long countDaysDifference(LocalDate first, LocalDate second) {
		Duration duration = Duration.between(first.atStartOfDay(), second.atStartOfDay());
		return duration.toDays();
	}

	public static String createImageName (String entityType, Integer entityId, Integer imageId) {
		if (!IMAGE_ENTITIES.contains(entityType)){
			throw new RuntimeException("This entity has no images!");
		}
		sb.setLength(0);
		sb.append(entityType);
		sb.append("_");
		sb.append(entityId);
		sb.append("_");
		sb.append(imageId);
		sb.append(".jpg");
		return sb.toString();
	}

	public static boolean isImageExist(String imageName){
		return Files.exists(IMAGE_DIR.resolve(imageName));
	}
}
