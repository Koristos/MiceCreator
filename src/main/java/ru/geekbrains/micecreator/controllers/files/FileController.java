package ru.geekbrains.micecreator.controllers.files;

import lombok.AllArgsConstructor;
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
public class FileController {

	@Autowired
	FileService fileService;


	@GetMapping("/{type}/{id}")
	public ResponseEntity<Resource> findAll(@PathVariable("type") String type, @PathVariable("id") String entityId) {
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
				throw new BadInputException(String.format("Тип файла %s не поддерживается!", type));
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/image")
	public boolean postImage(@ModelAttribute ImageFormData formDataWithFile) {
		return fileService.saveImage(formDataWithFile.getImage(), AppUtils.createImageName(formDataWithFile.getEntityType(),
				formDataWithFile.getEntityId(), formDataWithFile.getImageNum()));
	}

	@DeleteMapping("/image/{entityType}/{entityId}/{imageNum}")
	public boolean deleteImage(@PathVariable("entityType") String entityType, @PathVariable("entityId") Integer entityId,
	                           @PathVariable("imageNum") Integer imageNum) {
		return fileService.deleteImage(AppUtils.createImageName(entityType, entityId, imageNum));
	}
}

