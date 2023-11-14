package com.example.pruebaexcel.controller;

import com.example.pruebaexcel.services.IUploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class IUploadFilesController {

    @Autowired
    private IUploadFiles filesService;

    @PostMapping("/file")
    private ResponseEntity<String> uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {

        return ResponseEntity.ok(filesService.handleFileUpdate(file));
    }
}
