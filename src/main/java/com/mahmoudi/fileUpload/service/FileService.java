package com.mahmoudi.fileUpload.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public List<Path> listAll();
	public Path getPath(String filname);
	public void save(MultipartFile file);
	public Resource download(String filname);
}
