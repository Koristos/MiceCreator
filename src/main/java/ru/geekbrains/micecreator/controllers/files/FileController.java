package ru.geekbrains.micecreator.controllers.files;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.service.files.FileService;


@RestController
@RequestMapping("api/v1/files")
@AllArgsConstructor
public class FileController {

	@Autowired
	FileService fileService;


	@GetMapping("/{type}/{tour_id}")
	public ResponseEntity<Resource> findAll(@PathVariable("type") String type, @PathVariable("tour_id") Integer tourId) {
		if (type.equals("estimate")) {
			Resource file = fileService.loadEstimate(tourId);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
		}
		return null;
	}
}
