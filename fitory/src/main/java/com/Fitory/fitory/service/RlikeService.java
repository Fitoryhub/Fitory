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

    // 대댓글 좋아요한 사람들의 정보를 가져오기위한 메서드
    @Override
    public List<Rlikes> findrlike(Integer num) {
        return rlikeRepository.findByPnum(num);
    }

    //대댓글에 좋아요를 누른 사람의 정보를 저장하기 위한 메서드
    @Override
    public void rlikesave(Rlikes rlikes) {
            rlikeRepository.save(rlikes);
    }
    //대댓글에 좋아요를 취소한 사람의 정보를 삭제하기 위한 메서드
    @Transactional
    @Override
    public void rhate(String uid, Integer rnum) {
        rlikeRepository.deleteByUidAndRnum(uid, rnum);
    }
}
