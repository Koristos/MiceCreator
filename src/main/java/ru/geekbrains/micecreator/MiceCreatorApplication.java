package ru.geekbrains.micecreator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.geekbrains.micecreator.service.files.FileService;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;

@SpringBootApplication
public class MiceCreatorApplication implements CommandLineRunner {

	@Resource
	FileService fileService;

	public static void main(String[] args) {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		SpringApplication.run(MiceCreatorApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		fileService.clearUploads();
		fileService.init();
	}



}
