package com.Fitory.fitory.service;

import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private RlikeRepository rlikerepository;
    @Autowired
    private RepliesRepository repliesrepository;

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
    public Integer blike(Integer pnum) {
        boardrepository.plike(pnum);
       Board board= boardrepository.findByPnum(pnum);
       return board.getPlike();
    }

    @Transactional
    @Override
    public void phate(Integer pnum, String uid) {
        plikerepository.deleteByPnumAndUid(pnum,uid);
    }
    @Transactional
    @Override
    public Board bhate(Integer pnum) {
        boardrepository.bhate(pnum);
       return boardrepository.findByPnum(pnum);
    }

    @Transactional
    @Override
    public Comment clike(Clike clike) {
        Integer cnum = clike.getCnum();
        commentrepository.clike(cnum);
        clikerepository.save(clike);
        return commentrepository.findByCnum(cnum);
    }

    @Transactional
    @Override
    public Comment chate(Clike clike) {
        commentrepository.chate(clike.getCnum());
        clikerepository.deleteByCnumAndUid(clike.getCnum(),clike.getUid());
       Comment comment= commentrepository.findByCnum(clike.getCnum());
        return comment;
    }
    @Transactional
    @Override
    public void rhate(String uid ,Integer rnum) {
        rlikerepository.deleteByUidAndRnum(uid , rnum);
    }

    @Transactional
    @Override
    public void replieslike(Integer rnum) {
        repliesrepository.update(rnum);
    }
    @Transactional
    @Override
    public void replieshate(Integer rnum) {
        repliesrepository.replieshate(rnum);
    }
    @Transactional
    @Override
    public void board_mod(Board board) {

       Integer pnum=board.getPnum();
        String ptitle=board.getPtitle();
        String pcategory=board.getPcategory();
        String pbody=board.getPbody();
        String uid=board.getUid();

        boardrepository.modpost(pnum,ptitle,pcategory,pbody);
        filerepository.deleteByPnum(board.getPnum());
    }

    @Override
    public void boarddelete(Integer pnum) {
        boardrepository.deleteById(pnum);
    }

    @Transactional
    @Override
    public void commentdelete(Integer cnum) {
        commentrepository.deleteById(cnum);
    }
    @Transactional
    @Override
    public void commentmod(Comment comment) {
        Integer cnum=comment.getCnum();
        String cbody=comment.getCbody();
        commentrepository.commentmod(cnum,cbody);
    }

    @Override
    public void repliedelete(Integer rnum) {

        repliesrepository.deleteById(rnum);
    }
    @Transactional
    @Override
    public void replymod(Replies replie) {
        Integer rnum=replie.getRnum();
        String rbody=replie.getRbody();
        repliesrepository.replymod(rnum,rbody);
    }

    @Override
    public int rlike(Integer rnum) {
       Replies r= repliesrepository.findByRnum(rnum);
       int num=r.getRlikes();
       return num;
    }


}
