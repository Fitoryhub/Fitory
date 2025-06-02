package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Replies;
import com.Fitory.fitory.entity.Rlikes;
import com.Fitory.fitory.repository.RepliesRepository;
import com.Fitory.fitory.repository.RlikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReplieService implements IF_ReplieService {

    @Autowired
    private RepliesRepository repliesRepository;
    @Autowired
    private RlikeRepository rlikeRepository;

    // 글번호에 포함된 대댓글 리스트를 전부 불러오는 메서드 ( 댓글 번호에 맞게 뷰에서 배치한다)
    @Override
    public List<Replies> findreplies(Integer num) {
        return repliesRepository.findByPnum(num);
    }

    //대댓글 저장메서드 구현
    @Override
    public void repliessave(Replies replies) {
        repliesRepository.save(replies);
    }

    //대댓글 좋아요 올리는 메서드
    @Transactional
    @Override
    public void replieslike(Integer rnum) {
        repliesRepository.update(rnum);
    }

    //대댓글 좋아요 갯수를 RETURN 하는 메서드
    @Override
    public int rlike(Integer rnum) {
        Replies r = repliesRepository.findByRnum(rnum);
        int num = r.getRlikes();
        return num;
    }

    //대댓글 좋아요 -1하는 메서드
    @Transactional
    @Override
    public void replieshate(Integer rnum) {
        repliesRepository.replieshate(rnum);
    }

    //대댓글을 삭제하는 메서드
    @Override
    public void repliedelete(Integer rnum) {
        repliesRepository.deleteById(rnum);
    }

    //대댓글 정보를 수정하는 메서드
    @Transactional
    @Override
    public void replymod(Replies replie) {
        Integer rnum = replie.getRnum();
        String rbody = replie.getRbody();
        repliesRepository.replymod(rnum, rbody);
    }

}
