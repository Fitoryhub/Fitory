package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Files;
import com.Fitory.fitory.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements IF_FileService{

    @Autowired
    private FileRepository fileRepository;


    //파일 정보를 저장하는 메서드
    @Override
    public void filesave(Files files) {
        fileRepository.save(files);
    }

    //글번호에 맞는 파일정보를 가져오는 메서드
    @Override
    public List<Files> findfile(Integer pnum) {
        return fileRepository.findByPnum(pnum);
    }

    @Override
    public List<Files> findfile2(Integer dnum) {
        return fileRepository.findByDnum(dnum);
    }

    @Override
    public void delete(int dnum) {
        fileRepository.deleteByDnum(dnum);
    }


}
