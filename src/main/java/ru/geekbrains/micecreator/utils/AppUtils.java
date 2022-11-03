package ru.geekbrains.micecreator.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
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

	public static Object parseStringToObject (Class outcomeClass, String incomeString) {
		StringReader reader = new StringReader(incomeString);
		Object result = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(outcomeClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			result = unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException("XML parsing error");
		}
		return result;
	}
}
