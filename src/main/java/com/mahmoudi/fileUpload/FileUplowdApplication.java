package com.mahmoudi.fileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mahmoudi.fileUpload.service.FileServiceImpl;

@SpringBootApplication
public class FileUplowdApplication {
	@Autowired
	private FileServiceImpl fileServiceImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(FileUplowdApplication.class, args);
	}
	@Bean
	public CommandLineRunner run() {
		return args -> {
			fileServiceImpl.listAll();
		};
	}
}
