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
    private CommentRepository commentrepository;

    @Autowired
    private RlikeRepository rlikerepository;
    @Autowired
    private RepliesRepository repliesrepository;

    @Override
    public void savepost(Board board) {
        boardrepository.save(board);
    }

    // 게시판을 눌렀을떄 전체 게시글 출력하기위한 메서드
    @Override
    public Page<Board> alllist(Pageable pageable) {
        return boardrepository.findAll(pageable);
    }

    //게시판 카테고리,제목으로 검색구현 메서드
    @Override
    public Page<Board> searchboard(PtitlePcategoryDTO searchboard, Pageable pageable) {
        String pcategory = searchboard.getPcategory();
        String ptitle = searchboard.getPtitle();
        return boardrepository.findByPcategoryAndPtitleContaining(pcategory, ptitle, pageable);
    }


    // 글번호로 게시글 정보 가져오는 메서드
    @Override
    public Board searchoneboard(Integer pnum) {
        return boardrepository.findByPnum(pnum);
    }


    // 게시글 타이틀 만으로 검색하는 메서드 구현
    @Override
    public Page<Board> searchptitle(PtitlePcategoryDTO searchboard, Pageable pageable) {
        String ptitle = searchboard.getPtitle();

        return boardrepository.findByPtitleContaining(ptitle, pageable);
    }

    //게시글 조회수 올려주는 메서드
    @Transactional
    @Override
    public void updateplook(Integer pnum) {
        boardrepository.updateplook(pnum);
    }

    //게시글 좋아요 +1 후 좋아요 갯수 리턴하는 메서드
    @Transactional
    @Override
    public Integer blike(Integer pnum) {
        boardrepository.plike(pnum);
        Board board = boardrepository.findByPnum(pnum);
        return board.getPlike();
    }


    //게시글 좋아요 -1 후 좋아요 갯수 리턴하는 메서드
    @Transactional
    @Override
    public Board bhate(Integer pnum) {
        boardrepository.bhate(pnum);
        return boardrepository.findByPnum(pnum);
    }





    // 게시글 정보를 수정한후 기존 게시물 파일정보를 삭제 (컨트롤러에서 새로운 파일 추가함)
    @Transactional
    @Override
    public void board_mod(Board board) {

        Integer pnum = board.getPnum();
        String ptitle = board.getPtitle();
        String pcategory = board.getPcategory();
        String pbody = board.getPbody();
        String uid = board.getUid();

        boardrepository.modpost(pnum, ptitle, pcategory, pbody);
        filerepository.deleteByPnum(board.getPnum());
    }

    //게시글 글번호로 삭제함
    @Override
    public void boarddelete(Integer pnum) {
        boardrepository.deleteById(pnum);
    }

    @Override
    public List<Board> myboardlist(String uid) {
       return boardrepository.findByUid(uid);
    }


}
