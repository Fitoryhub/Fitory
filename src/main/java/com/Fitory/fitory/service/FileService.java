package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Files;
import com.Fitory.fitory.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;

@Service
public class FileService implements IF_FileService{

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private View error;

    @Override
    public void filesave(Files files) {
        fileRepository.save(files);
    }

    @Override
    public List<Files> findfile(Integer pnum) {
        return fileRepository.findByPnum(pnum);
    }
}
