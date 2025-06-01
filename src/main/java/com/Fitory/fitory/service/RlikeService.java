package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Rlikes;
import com.Fitory.fitory.repository.RlikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RlikeService implements IF_RlikeService{

    @Autowired
    private RlikeRepository rlikeRepository;

    @Override
    public List<Rlikes> findrlike(Integer num) {
        return rlikeRepository.findByPnum(num);
    }

    @Override
    public void rlikesave(Rlikes rlikes) {
            rlikeRepository.save(rlikes);
    }

    @Transactional
    @Override
    public void rhate(String uid, Integer rnum) {
        rlikeRepository.deleteByUidAndRnum(uid, rnum);
    }
}
