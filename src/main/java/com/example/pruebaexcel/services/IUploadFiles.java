package com.example.pruebaexcel.services;

import com.example.pruebaexcel.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


public interface IUploadFiles {

    String handleFileUpdate(MultipartFile file) throws Exception;

    String generateUsuarioCsv () throws Exception;
    String generateUsuarioExcel (ArrayList<Usuario> usuarios) throws Exception;
}
