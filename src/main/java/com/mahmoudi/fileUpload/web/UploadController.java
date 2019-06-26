package com.mahmoudi.fileUpload.web;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mahmoudi.fileUpload.service.FileServiceImpl;


@Controller
public class UploadController {
	
	@Autowired
	private FileServiceImpl fileServiceImpl;

	@RequestMapping("/")
	public String home() {
		return "upload";
	}
	@PostMapping("/upload")
	public String upload(@RequestParam("fileToUpload") MultipartFile file) {
		fileServiceImpl.save(file);
		return "success";
	}
	@GetMapping("/listAll")
	public String loadAll(Model model) {
		model.addAttribute("files",fileServiceImpl.listAll());
		return "listAll";
	}
	@GetMapping("/{filename}")
	@ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileServiceImpl.download(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
