package com.Fitory.fitory.service;

import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IF_BoardService {






    void savepost(Board board);

    void savefile(Files fileone);

    Page<Board> alllist(Pageable pageable);

    Page<Board> searchboard(PtitlePcategoryDTO searchboard, Pageable pageable);

    Board searchoneboard(Integer pnum);

    Page<Board> searchptitle(PtitlePcategoryDTO searchboard ,Pageable pageable);

    void updateplook(Integer pnum);

    Integer blike(Integer pnum);

    void phate(Integer pnum, String uid);

    Board bhate(Integer pnum);

    Comment clike(Clike clike);

    Comment chate(Clike clike);

    void rhate(String uid , Integer rnum);

    void replieslike(Integer rnum);

    void replieshate(Integer rnum);

    void board_mod(Board board);

    void boarddelete(Integer pnum);

    void commentdelete(Integer cnum);

    void commentmod(Comment comment);

    void repliedelete(Integer rnum);

    void replymod(Replies replie);

    int rlike(Integer rnum);

    void filesave(Files files);

    Plike findplike(String uid, Integer pnum);

    List<Files> findboard(Integer pnum);

    List<Comment> findcomment(Integer num);

    List<Clike> findclike(Integer num);

    List<Replies> findreplies(Integer num);

    List<Rlikes> findrlike(Integer num);

    void saveplike(Plike plike);

    void commentsave(Comment comment);

    void repliessave(Replies replies);

    void rlikesave(Rlikes rlikes);
}
