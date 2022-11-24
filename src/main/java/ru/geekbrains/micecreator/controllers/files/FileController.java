package ru.geekbrains.micecreator.controllers.files;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.service.files.FileService;
import ru.geekbrains.micecreator.utils.AppUtils;


@RestController
@RequestMapping("api/v1/files")
@AllArgsConstructor
@Tag(name = "Файлы", description = "Контроллер для работы с файлами - презентациями, сметами, изображениями")
public class FileController {

	private static final Logger logger = LogManager.getLogger(FileController.class);

	@Autowired
	FileService fileService;


	@GetMapping("/{type}/{id}")
	@Operation(summary = "Запрос файла по типу и id")
	public ResponseEntity<Resource> findAll(@Parameter(description = "Тип: estimate / image / presentation")@PathVariable("type") String type,
	                                        @Parameter(description = "Для estimate и presentation - ID тура, для image - id картинки") @PathVariable("id") String entityId) {
		logger.info(String.format("File with type %s requested", type));
		Resource file;
		switch (type) {
			case "estimate":
				file = fileService.loadEstimate(Integer.parseInt(entityId));
				break;
			case "image":
				file = fileService.getImage(entityId);
				break;
			case "presentation":
				file = fileService.loadPresentation(Integer.parseInt(entityId));
				break;
			default:
				logger.error(String.format("File with type %s is not supported", type));
				throw new BadInputException(String.format("Тип файла %s не поддерживается!", type));
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/image")
	@Operation(summary = "Загрузка картинки")
	public boolean postImage(@ModelAttribute ImageFormData formDataWithFile) {
		logger.info("Image uploaded");
		return fileService.saveImage(formDataWithFile.getImage(), AppUtils.createImageName(formDataWithFile.getEntityType(),
				formDataWithFile.getEntityId(), formDataWithFile.getImageNum()));
	}

	@DeleteMapping("/image/{entityType}/{entityId}/{imageNum}")
	@Operation(summary = "Удаление картинки")
	public boolean deleteImage(@PathVariable("entityType") String entityType, @PathVariable("entityId") Integer entityId,
	                           @PathVariable("imageNum") Integer imageNum) {
		logger.info("Image deleted");
		return fileService.deleteImage(AppUtils.createImageName(entityType, entityId, imageNum));
	}
}

