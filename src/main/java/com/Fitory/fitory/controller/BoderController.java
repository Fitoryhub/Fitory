package com.Fitory.fitory.controller;

import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.entity.Board;
import com.Fitory.fitory.entity.Files;
import com.Fitory.fitory.entity.Plike;
import com.Fitory.fitory.repository.FileRepository;
import com.Fitory.fitory.repository.PlikeRepository;
import com.Fitory.fitory.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class BoderController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PlikeRepository plikeRepository;


    @GetMapping("/boardlist")
    public String boardlist(Model model ,@PageableDefault(page=0,size = 10 ,sort="pnum"
            ,direction = Sort.Direction.DESC) Pageable pageable
            ,@RequestParam(required = false) String pcategory,
             @RequestParam(required = false) String ptitle) {

        PtitlePcategoryDTO searchboard = new PtitlePcategoryDTO();
        searchboard.setPcategory(pcategory);
        searchboard.setPtitle(ptitle);

            Page<Board> board;

          if(searchboard.getPcategory()==null){
              board = boardService.alllist(pageable);
          }else {
              if (searchboard.getPcategory().equals("all")) {
                    board = boardService.searchptitle(searchboard , pageable);
              }else{

                  board = boardService.searchboard(searchboard, pageable);
              }
                  }
        int nowpage= board.getPageable().getPageNumber()+1;
        int startpage = Math.max(nowpage - 2, 1);  // 현재 페이지를 중심으로 2개 앞뒤
        int endpage = Math.min(startpage + 4, board.getTotalPages()); // 총 5개만 보이도록 조정

        model.addAttribute("list", board);
        model.addAttribute("nowpage", nowpage);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);
        return "/boardlist";
    }





    @PostMapping("/submit_post")
    public String submitPost(@ModelAttribute("board") Board board,
                             @RequestParam("files")MultipartFile[] filelist) throws Exception {

        System.out.println("확인한다" + board.getNickname());

        boardService.savepost(board);

        String ProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";


        for (MultipartFile onefile : filelist) {
            if(!onefile.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + onefile.getOriginalFilename();
                File savfile = new File(ProjectPath, filename);
                onefile.transferTo(savfile);
                Files files = new Files();
                files.setFilename(filename);
                files.setPnum(board.getPnum());
                fileRepository.save(files);
            }
        }

        return "redirect:/boardlist";

    }

    @GetMapping("/detailview")
    public String detailview(@RequestParam("pnum")Integer pnum, Model model ,HttpSession session) {
        System.out.println(pnum);
        boardService.updateplook(pnum);
        Board board = boardService.searchoneboard(pnum);
        String uid = session.getAttribute("uid").toString();
        Plike plike=plikeRepository.findByUidAndPnum(uid ,pnum);
       List<Files>files = fileRepository.findByPnum(pnum);
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        model.addAttribute("plike", plike);
       Integer num = board.getPnum();
        return "/detailview";
    }

    @GetMapping("/write")
    public String write(Board board, Model model) {
        model.addAttribute("board", board);
        return "write";

    }
    @PostMapping("/plike")
    public String plike(@ModelAttribute Plike plike ,Model model) {
        Integer pnum = plike.getPnum();
        boardService.blike(pnum);
        plikeRepository.save(plike);

    return "redirect:/detailview?pnum="+pnum;

    }
    @PostMapping("/phate")
    public String phate(@ModelAttribute Plike plike ,Model model) {

        Integer pnum = plike.getPnum();
        boardService.bhate(pnum);
        String uid=plike.getUid();
        boardService.phate(pnum,uid);
        model.addAttribute("pnum", pnum);
        return "redirect:/detailview?pnum="+pnum;
    }
}