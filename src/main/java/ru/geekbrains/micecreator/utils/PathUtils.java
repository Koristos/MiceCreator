package ru.geekbrains.micecreator.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PathUtils {

	@Value("${micecreator.pathconst.root}")
	private String rootPath;
	@Value("${micecreator.pathconst.uploads}")
	private String uploadsPath;
	@Value("${micecreator.pathconst.images}")
	private String imagePath;

	/**
	 * Метод проверяет фактическое наличие файла с картинкой в папке, предназначенной для хранения картинок
	 * @param imageName имя(id) картинки
	 * @return true, если картинка существует, false - если нет
	 */
	public boolean isImageExist(String imageName){
		return Files.exists(getImagesPath().resolve(imageName));
	}

	/**
	 * @return путь до хранилища файлов
	 */
	public Path getRootPath() {
		return Paths.get(rootPath);
	}

	/**
	 * @return путь до хранилища изображений
	 */
	public Path getImagesPath() {
		return Paths.get(rootPath, imagePath);
	}

	/**
	 * @return путь до хранилища выгружаемых файлов
	 */
	public Path getUploadsPath() {
		return Paths.get(rootPath, uploadsPath);
	}
}
