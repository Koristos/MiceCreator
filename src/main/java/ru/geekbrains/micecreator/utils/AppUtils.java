package ru.geekbrains.micecreator.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class AppUtils {

	private AppUtils(){};

	public static boolean isBlank(String string){
		if (string == null){
			return true;
		}
		return string.isBlank();
	}

	public static Long countDaysDifference(Date first, Date second) {
		long diffInMillies = Math.abs(second.getTime() - first.getTime());
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
