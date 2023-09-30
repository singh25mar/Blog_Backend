package com.BlogApplaction.backend.service.impl;

import com.BlogApplaction.backend.repository.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String UploadImage(String path, MultipartFile file) throws IOException {

        //first Get Orignal File Nmae
        String name = file.getOriginalFilename();
        
        // now gernate Random name for file 
        String randamID = UUID.randomUUID().toString();
        String filename1 = randamID.concat(name.substring(name.lastIndexOf(".")));

        // Full file Path
        String filePath = path + File.separator + filename1;

        // create Folder if Not Exists
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        // copy file

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return filename1;
    }

    @Override
    public InputStream getResource(String path, String fileNmae) throws FileNotFoundException {

        String fullPath = path + File.separator + fileNmae;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
