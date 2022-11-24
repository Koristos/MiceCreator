package ru.geekbrains.micecreator.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public final class AppUtils {

	private AppUtils(){};

	private static final List<String> IMAGE_ENTITIES = List.of("hotel", "hotel_service", "region_service", "room", "region", "location");
	private static final StringBuilder sb = new StringBuilder();

	/**
	 * Проверяет строку на пустоту. Null также соответствует пустой строке и не вызывает ошибку
	 * @param string строка для проверки
	 * @return true, если строка пустая, false, если нет
	 */
	public static boolean isBlank(String string){
		if (string == null){
			return true;
		}
		return string.isBlank();
	}

	/**
	 * Метод вычисляет разницу в днях между двумя датами
	 * @param first первая дата
	 * @param second вторая дата
	 * @return long разница между датами
	 */
	public static Long countDaysDifference(LocalDate first, LocalDate second) {
		Duration duration = Duration.between(first.atStartOfDay(), second.atStartOfDay());
		return duration.toDays();
	}

	/**
	 * Формирует имя(id) для изображения, прикрепляемого к сущности
	 * @param entityType тип сущности
	 * @param entityId id сущности
	 * @param imageId слот картинки
	 * @return стандартизированное имя
	 * @exception RuntimeException если переданная сущность не поддерживает хранение фотографий
	 */
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

	/**
	 * Метод для парсинга XML в объект
	 * @param outcomeClass Класс, который должен получиться на выходе
	 * @param incomeString входящий XML в виде строки
	 * @return распаршенный в класс XML
	 */
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
