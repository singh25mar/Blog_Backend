package com.BlogApplaction.backend.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String UploadImage(String path, MultipartFile file) throws IOException;
    InputStream getResource(String path, String fileNmae) throws FileNotFoundException;
}
