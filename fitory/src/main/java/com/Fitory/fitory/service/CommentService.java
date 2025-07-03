package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.CommentDTO;
import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Comment;
import com.Fitory.fitory.mapper.CommentMapper;
import com.Fitory.fitory.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements IF_CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    //댓글 작성시 저장하는 메서드
    @Override
    public void commentsave(Comment comment) {
        commentRepository.save(comment);
    }

    //댓글 좋아요 눌렀을시 댓글 테이블 좋아요 컬럼에 +1을 해주고 좋아요 수를 RETURN 해주는 메서드
    @Transactional
    @Override
    public Comment clike(Clike clike) {
        Integer cnum = clike.getCnum();
        commentRepository.clike(cnum);
        return commentRepository.findByCnum(cnum);
    }

    //댓글 좋아요 취소시 댓글 테이블 좋아요 컬럼에 -1을 해주고 좋아요 수를 RETURN 해주는 메서드
    @Transactional
    @Override
    public Comment chate(Clike clike) {
        commentRepository.chate(clike.getCnum());
        Comment comment = commentRepository.findByCnum(clike.getCnum());
        return comment;
    }

    // 댓글 삭제 메서드 구현
    @Transactional
    @Override
    public void commentdelete(Integer cnum) {
        commentRepository.deleteById(cnum);
    }

    //댓글 수정 메서드
    @Transactional
    @Override
    public void commentmod(Comment comment) {
        Integer cnum = comment.getCnum();
        String cbody = comment.getCbody();
        commentRepository.commentmod(cnum, cbody);
    }

    @Override
    public List<Comment> mycomment(String uid) {
        return commentRepository.findByUid(uid);
    }

    //게시글 상세보기 했을시 글번호에 작성한 댓글 리스트를 가져오는 메서드
    @Override
    public List<CommentDTO> findcomment(Integer num , String uid) {

        return commentMapper.commentlistandlike(num , uid);
    }
}

