package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.repository.ClikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClikeService implements IF_ClikeService{

    @Autowired
    private ClikeRepository clikeRepository;

    @Override
    public List<Clike> findclike(Integer num) {
        return clikeRepository.findByPnum(num);
    }

    @Override
    public void save(Clike clike) {
        clikeRepository.save(clike);
    }

    @Transactional
    @Override
    public void delete(Clike clike) {
        clikeRepository.deleteByCnumAndUid(clike.getCnum(), clike.getUid());
    }
}
