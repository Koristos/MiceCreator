package ru.geekbrains.micecreator.utils;

public final class StringUtils {

	private StringUtils(){};

	public static boolean isBlank(String string){
		if (string == null){
			return true;
		}
		return string.isBlank();
	}
}
