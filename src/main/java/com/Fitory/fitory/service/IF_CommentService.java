package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Comment;

import java.util.List;

public interface IF_CommentService {

    List<Comment> findcomment(Integer num);

    public void commentsave(Comment comment);

    Comment clike(Clike clike);

    Comment chate(Clike clike);

    void commentdelete(Integer cnum);

    void commentmod(Comment comment);
}
