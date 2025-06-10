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

    // 세션에 저장되어있는 아이디와 비교해 좋아요 여부를 판단하기위한 메서드
    @Override
    public Plike findplike(String uid, Integer pnum) {
        return plikeRepository.findByUidAndPnum(uid, pnum);
    }

    // 게시글에 좋아요를 누른 회원정보와 글번호 저장하는 메서드
    @Override
    public void saveplike(Plike plike) {
        plikeRepository.save(plike);
    }

    // 게시글 에 좋아요를 취소한 회원의 정보와 글정보를 삭제하는 메서드
    @Transactional
    @Override
    public void phate(Integer pnum, String uid) {
        plikeRepository.deleteByPnumAndUid(pnum, uid);
    }

    public Plike findplike2(String uid, int dnum) {
        return plikeRepository.findByUidAndDnum(uid, dnum);
    }

    public void deletdplike(Plike plike) {
        plikeRepository.delete(plike);
    }

    public void deletdplike(int did) {
        plikeRepository.deleteAllByDnum(did);
    }


}
