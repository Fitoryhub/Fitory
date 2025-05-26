package com.Fitory.fitory.service;

import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.Board;
import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Files;
import com.Fitory.fitory.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService implements IF_BoardService {

    @Autowired
    private BoardRepository boardrepository;
    @Autowired
    private FileRepository filerepository;
    @Autowired
    private PlikeRepository plikerepository;
    @Autowired
    private CommentRepository commentrepository;
    @Autowired
    private ClikeRepository clikerepository;
    @Override
    public void savepost(Board board) {
       boardrepository.save(board);
    }

    @Override
    public void savefile(Files fileone) {
    filerepository.save(fileone);
    }

    @Override
    public Page<Board> alllist(Pageable pageable) {
        return boardrepository.findAll(pageable);
    }

    @Override
    public Page<Board> searchboard(PtitlePcategoryDTO searchboard, Pageable pageable) {
        String pcategory=searchboard.getPcategory();
        String ptitle=searchboard.getPtitle();
        return boardrepository.findByPcategoryAndPtitleContaining(pcategory,ptitle,pageable);
    }

    @Override
    public Board searchoneboard(Integer pnum) {
        return boardrepository.findByPnum(pnum);
    }

    @Override
    public Page<Board> searchptitle(PtitlePcategoryDTO searchboard,Pageable pageable) {
        String ptitle=searchboard.getPtitle();

        return boardrepository.findByPtitleContaining(ptitle,pageable);
    }
    @Transactional
    @Override
    public void updateplook(Integer pnum) {
        boardrepository.updateplook(pnum);
    }

    @Transactional
    @Override
    public void blike(Integer pnum) {
        boardrepository.plike(pnum);
    }

    @Transactional
    @Override
    public void phate(Integer pnum, String uid) {
        plikerepository.deleteByPnumAndUid(pnum,uid);
    }
    @Transactional
    @Override
    public void bhate(Integer pnum) {
        boardrepository.bhate(pnum);
    }

    @Transactional
    @Override
    public void clike(Clike clike) {
        Integer cnum = clike.getCnum();
        commentrepository.clike(cnum);
        clikerepository.save(clike);
    }

    @Transactional
    @Override
    public void chate(Clike clike) {
        commentrepository.chate(clike.getCnum());
        clikerepository.deleteByCnumAndUid(clike.getCnum(),clike.getUid());
    }



}
