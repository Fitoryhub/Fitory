package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Comment;
import com.Fitory.fitory.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class CommentService implements IF_CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void commentsave(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment clike(Clike clike) {
        Integer cnum = clike.getCnum();
        commentRepository.clike(cnum);
        return commentRepository.findByCnum(cnum);
    }

    @Transactional
    @Override
    public Comment chate(Clike clike) {
        commentRepository.chate(clike.getCnum());
        Comment comment = commentRepository.findByCnum(clike.getCnum());
        return comment;
    }

    @Transactional
    @Override
    public void commentdelete(Integer cnum) {
        commentRepository.deleteById(cnum);
    }

    @Transactional
    @Override
    public void commentmod(Comment comment) {
        Integer cnum = comment.getCnum();
        String cbody = comment.getCbody();
        commentRepository.commentmod(cnum, cbody);
    }


    @Override
    public List<Comment> findcomment(Integer num) {
         return commentRepository.findByPnumOrderByCnumAsc(num);
    }
}

