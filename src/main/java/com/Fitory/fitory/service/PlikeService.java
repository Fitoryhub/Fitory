package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Plike;
import com.Fitory.fitory.repository.PlikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlikeService implements IF_PlikeService{

    @Autowired
    private PlikeRepository plikeRepository;

    @Override
    public Plike findplike(String uid, Integer pnum) {
        return plikeRepository.findByUidAndPnum(uid, pnum);
    }

    @Override
    public void saveplike(Plike plike) {
        plikeRepository.save(plike);
    }

    @Transactional
    @Override
    public void phate(Integer pnum, String uid) {
        plikeRepository.deleteByPnumAndUid(pnum, uid);
    }


}
