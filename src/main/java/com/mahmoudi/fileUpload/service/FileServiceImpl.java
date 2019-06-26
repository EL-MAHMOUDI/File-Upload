package com.mahmoudi.fileUpload.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{
	
	private static final String ROOT_DIRECTORY = "upload-dir";
	@Override
	public List<Path>listAll() {
		try {
			return Files.walk(Paths.get(ROOT_DIRECTORY), 1)
			.filter(path -> !path.equals(Paths.get(ROOT_DIRECTORY)))
			.map(Paths.get(ROOT_DIRECTORY)::relativize).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("Enable to load files.");
		}
	}

	@Override
	public Path getPath(String filename) {
		return Paths.get(ROOT_DIRECTORY).resolve(filename);
	}

	@Override
	public void save(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
			try (InputStream source = file.getInputStream();){
				Files.createDirectories(Paths.get(ROOT_DIRECTORY));
				Path targetFile = Paths.get(ROOT_DIRECTORY).resolve(filename);
				Files.copy(source, targetFile, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public Resource download(String filname) {
		Path file = getPath(filname);
		try {
			Resource resource = new UrlResource(file.toUri());
			return resource;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Could not read file:",e);
		}
	}

}
