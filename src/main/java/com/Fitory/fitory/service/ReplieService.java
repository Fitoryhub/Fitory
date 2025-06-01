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

    @Override
    public List<Replies> findreplies(Integer num) {
        return repliesRepository.findByPnum(num);
    }

    @Override
    public void repliessave(Replies replies) {
        repliesRepository.save(replies);
    }

    @Transactional
    @Override
    public void replieslike(Integer rnum) {
        repliesRepository.update(rnum);
    }

    @Override
    public int rlike(Integer rnum) {
        Replies r = repliesRepository.findByRnum(rnum);
        int num = r.getRlikes();
        return num;
    }

    @Transactional
    @Override
    public void replieshate(Integer rnum) {
        repliesRepository.replieshate(rnum);
    }

    @Override
    public void repliedelete(Integer rnum) {
        repliesRepository.deleteById(rnum);
    }

    @Transactional
    @Override
    public void replymod(Replies replie) {
        Integer rnum = replie.getRnum();
        String rbody = replie.getRbody();
        repliesRepository.replymod(rnum, rbody);
    }

}
