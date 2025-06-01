package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Files;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IF_FileService {

    void filesave(Files files);

    List<Files> findfile(Integer pnum);
}
