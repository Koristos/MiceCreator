package ru.geekbrains.micecreator.controllers.files;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "Контейнер для загрузки картинок")
public class ImageFormData {
	@Schema(description = "Картинка 1200*800")
	private MultipartFile image;
	@Schema(description = "Тип сущности, к которой прикрепляется картинка")
	private String entityType;
	@Schema(description = "ID сущности, к которой прикрепляется картинка")
	private Integer entityId;
	@Schema(description = "Номер слота картинки")
	private Integer imageNum;
}
