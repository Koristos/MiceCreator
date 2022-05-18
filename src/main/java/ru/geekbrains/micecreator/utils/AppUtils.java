package ru.geekbrains.micecreator.utils;

import java.time.Duration;
import java.time.LocalDate;

public final class AppUtils {

	private AppUtils(){};

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
}
