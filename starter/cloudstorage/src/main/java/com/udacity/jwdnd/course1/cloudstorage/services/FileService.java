package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public Integer addFile(MultipartFile file, Integer userId) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileToDB = new File(fileName, file.getContentType(), Long.toString(file.getSize()), userId, file.getBytes());

        return fileMapper.addFile(fileToDB);
    }

    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
