package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IF_BoardService {

    void savepost(Board board);

    Page<Board> alllist(Pageable pageable);

    Page<Board> searchboard(PtitlePcategoryDTO searchboard, Pageable pageable);

    Board searchoneboard(Integer pnum);

    Page<Board> searchptitle(PtitlePcategoryDTO searchboard ,Pageable pageable);

    void updateplook(Integer pnum);

    Integer blike(Integer pnum);

    Board bhate(Integer pnum);

    void board_mod(Board board);

    void boarddelete(Integer pnum);

    List<Board> myboardlist(String uid);

    List<Board> top5(String category);
}
