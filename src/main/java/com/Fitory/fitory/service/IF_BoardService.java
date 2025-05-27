package com.Fitory.fitory.service;

import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.Board;
import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Files;
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

    void blike(Integer pnum);

    void phate(Integer pnum, String uid);

    void bhate(Integer pnum);

    void clike(Clike clike);

    void chate(Clike clike);


    void rhate(String uid , Integer rnum);

    void replieslike(Integer rnum);

    void replieshate(Integer rnum);
}
