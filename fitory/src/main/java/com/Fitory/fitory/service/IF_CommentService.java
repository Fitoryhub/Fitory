package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.CommentDTO;
import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Comment;

import java.util.List;

public interface IF_CommentService {

    List<CommentDTO> findcomment(Integer num , String uid);

    public void commentsave(Comment comment);

    Comment clike(Clike clike);

    Comment chate(Clike clike);

    void commentdelete(Integer cnum);

    void commentmod(Comment comment);

    List<Comment> mycomment(String uid);
}
