package ru.geekbrains.micecreator.controllers.files;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageFormData {
	private MultipartFile image;
	private String entityType;
	private Integer entityId;
	private Integer imageNum;
}
