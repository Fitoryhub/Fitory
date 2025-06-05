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

    // 이 댓글에 좋아요를 눌렀는지 확인하기위해 리스트를 불러오는 메서드
    @Override
    public List<Clike> findclike(Integer num) {
        return clikeRepository.findByPnum(num);
    }
     // 댓글에 좋아요를 눌렀을시 눌른사람의 정보와 댓글 정보를 저장함으로써 눌렀는지 확인용으로 사용가능
    @Override
    public void save(Clike clike) {
        clikeRepository.save(clike);
    }

    //댓글 좋아요 취소시 저장해두었던 댓글정보와 회원정보 행을 삭제
    @Transactional
    @Override
    public void delete(Clike clike) {
        clikeRepository.deleteByCnumAndUid(clike.getCnum(), clike.getUid());
    }
}
