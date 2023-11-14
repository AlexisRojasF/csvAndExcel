package com.example.pruebaexcel.services.impl;

import com.example.pruebaexcel.entity.Usuario;
import com.example.pruebaexcel.services.IUploadFiles;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

@Service
public class IUploadFilesImpl implements IUploadFiles {


    @Override
    public String handleFileUpdate(MultipartFile file) throws Exception {

        try {
            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            Long fileSize = file.getSize();
            long maxFileSize = 10 * 1024 * 1024 * 1024;

            if (fileSize < maxFileSize) {
                return "File size must be less then or equal 10kb";
            }

            if (!fileOriginalName.endsWith(".csv")) {
                return "CVS  files are allowed!";
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            File folder = new File("src/main/resources/files");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get("src/main/resources/files/" + newFileName);
            Files.write(path, bytes);
            generateUsuarioCsv();
            return "File upload seccesfully!";
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

    }

    @Override
    public String generateUsuarioCsv() throws Exception {

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        Usuario usuario = null;
        String[] datos;

        try (Scanner csv = new Scanner(new File("src/main/resources/files/pp.csv"))) {
            while (csv.hasNextLine()) {
                datos = csv.nextLine().split(",");
                usuario = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4]);
                usuarios.add(usuario);
            }

            assert usuario != null;

            System.out.println(usuario.getNombre());
            System.out.println(usuario.getApellido());
            System.out.println(usuario.getContacto());
            System.out.println(usuario.getId());
            System.out.println(usuario.getNumeroId());


            generateUsuarioExcel(usuarios);
            return "Listo";
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String generateUsuarioExcel(ArrayList<Usuario> usuarios) throws Exception {

        Workbook libro = new XSSFWorkbook();
        final String nombreArchivo = "usuarios.xlsx";
        Sheet hoja = libro.createSheet("Hoja 1");

        String[] encabezados = {"Nombre", "Apellido", "Contacto", "Id", "Numero Id"};
        int indiceFila = 0;

        Row fila = hoja.createRow(indiceFila);

        for (int i = 0; i < encabezados.length; i++) {
            String encabezado = encabezados[i];
            Cell celda = fila.createCell(i);
            celda.setCellValue(encabezado);
        }

        indiceFila++;

        for (Usuario value : usuarios) {
            fila = hoja.createRow(indiceFila);
            fila.createCell(0).setCellValue(value.getNombre());
            fila.createCell(1).setCellValue(value.getApellido());
            fila.createCell(2).setCellValue(value.getContacto());
            fila.createCell(3).setCellValue(value.getId());
            fila.createCell(4).setCellValue(value.getNumeroId());
            indiceFila++;
        }

        // Guardamos

        FileOutputStream outputStream;

        try {

            outputStream = new FileOutputStream("src/main/resources/files/" + nombreArchivo);
            libro.write(outputStream);
            libro.close();
            System.out.println("Libro de personas guardado correctamente");
            return "ok";
        } catch (FileNotFoundException ex) {
            System.out.println("Error de filenotfound");
            throw new Exception(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de IOException");
            throw new Exception(ex.getMessage());
        }


    }

}
