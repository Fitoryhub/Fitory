package com.Fitory.fitory.controller;

import com.Fitory.fitory.DTO.CommentDTO;
import com.Fitory.fitory.DTO.PtitlePcategoryDTO;
import com.Fitory.fitory.DTO.RepliesDTO;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.repository.*;
import com.Fitory.fitory.service.BoardService;
import com.Fitory.fitory.service.UserService;
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
import java.util.ArrayList;
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
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    ClikeRepository clikeRepository;
    private UserService userService;
    @Autowired
    RepliesRepository repliesRepository;
    @Autowired
    RlikeRepository rlikeRepository;
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

        boardService.updateplook(pnum);
        Board board = boardService.searchoneboard(pnum);
        String uid = session.getAttribute("uid").toString();
        Plike plike=plikeRepository.findByUidAndPnum(uid ,pnum);
       List<Files>files = fileRepository.findByPnum(pnum);
        Integer num = board.getPnum();
        List<Comment> comments =commentRepository.findByPnumOrderByCnumAsc(num);
       List<CommentDTO> commentDTOS=new ArrayList<>();
        for(Comment comment:comments){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setCnum(comment.getCnum());
            commentDTO.setPnum(comment.getPnum());
            commentDTO.setCbody(comment.getCbody());
            commentDTO.setNickname(comment.getNickname());
            commentDTO.setUid(comment.getUid());
            commentDTO.setCdate(comment.getCdate());
            commentDTO.setClike(comment.getClike());
            commentDTOS.add(commentDTO);
        }
        List<Clike> clike=clikeRepository.findByPnum(num);
        for(CommentDTO commentDTO:commentDTOS){
            for(Clike c:clike){
                if(c.getUid().equals(uid)&&commentDTO.getCnum()==(c.getCnum())){
                    boolean liked=true;
                    commentDTO.setLiked(liked);
                }
            }
        }
        List<Replies> replie =repliesRepository.findByPnum(num);
        List<RepliesDTO> replies= new ArrayList<>();

        for(Replies onereplie : replie){
                          RepliesDTO repliesDTO=new RepliesDTO();
                          repliesDTO.setCnum(onereplie.getCnum());
                          repliesDTO.setPnum(onereplie.getPnum());
                          repliesDTO.setRlikes(onereplie.getRlikes());
                          repliesDTO.setRbody(onereplie.getRbody());
                          repliesDTO.setRdate(onereplie.getRdate());
                          repliesDTO.setNickname(onereplie.getNickname());
                          repliesDTO.setUid(onereplie.getUid());
                          repliesDTO.setRdate(onereplie.getRdate());
                          repliesDTO.setRnum(onereplie.getRnum());
                          replies.add(repliesDTO);
        }

        List<Rlikes> rlikes =rlikeRepository.findByPnum(num);
        for(RepliesDTO repliesDTO : replies){
            for(Rlikes rlike:rlikes){
                if(rlike.getUid() != null && rlike.getRnum() != null && repliesDTO.getRnum() != null&&
                        rlike.getUid().equals(uid)&&
                        rlike.getRnum().equals(repliesDTO.getRnum())){
                    repliesDTO.setCheck(true);
                }
            }
        }

        model.addAttribute("replies", replies);
        model.addAttribute("commentlist", commentDTOS);
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        model.addAttribute("plike", plike);
        model.addAttribute("clike", clike);



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
    @PostMapping("comment")
    public String comment(@ModelAttribute Comment comment , Model model) {
        commentRepository.save(comment);
        return "redirect:/detailview?pnum="+comment.getPnum();
    }
    @PostMapping("/clike")
    public String clike(@ModelAttribute Clike clike) {
        boardService.clike(clike);
        return "redirect:/detailview?pnum="+clike.getPnum();
    }
    @PostMapping("/chate")
    public  String chate(@ModelAttribute Clike clike) {
                boardService.chate(clike);
        return "redirect:/detailview?pnum="+clike.getPnum();
    }
    @PostMapping("/replies")
    public String replies(@ModelAttribute Replies replies) {
    repliesRepository.save(replies);

           return "redirect:/detailview?pnum="+replies.getPnum();
    }
    @PostMapping("/rlike")
    public String rlike(@ModelAttribute Rlikes rlikes) {
        rlikeRepository.save(rlikes);
        Integer rnum =rlikes.getRnum();
        boardService.replieslike(rnum);
        return "redirect:/detailview?pnum="+rlikes.getPnum();
    }
    @PostMapping("/rhate")
    public String rhate(@ModelAttribute Rlikes rlikes) {
        String uid =rlikes.getUid();
        Integer rnum =rlikes.getRnum();
        boardService.rhate(uid ,rnum);
        boardService.replieshate(rnum);
        return "redirect:/detailview?pnum="+rlikes.getPnum();
    }
    @GetMapping("/boardmod")
    public String boardmod(Model model ,@RequestParam("pnum") Integer pnum ) {

        Board board=new Board();
        board.setPnum(pnum);
        model.addAttribute("mod",true);
        model.addAttribute("board", board);
        return "write";
    }
    @PostMapping("/mod_post")
    public String modpost(@ModelAttribute Board board ,
                          @RequestParam("files")MultipartFile[] filelist) throws Exception {
        boardService.board_mod(board);

        String ProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        System.out.println(board.getPnum());

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

        return "redirect:/detailview?pnum="+board.getPnum();
    }

    @GetMapping("/boarddelete")
    public String boarddelete(@RequestParam("pnum") Integer pnum) {
        boardService.boarddelete(pnum);
        return "redirect:/boardlist";
    }
    @PostMapping("/commentdelete")
    public String commentdelete(@RequestParam("pnum") Integer pnum ,@RequestParam("cnum") Integer cnum) {
        boardService.commentdelete(cnum);
        return "redirect:/detailview?pnum="+pnum;
    }
    @PostMapping("/commentmod")
    public String commentmod(@ModelAttribute Comment comment ) {


        boardService.commentmod(comment);
        return "redirect:/detailview?pnum="+comment.getPnum();
    }
    @PostMapping("/repliedelete")
    public String repliedelete(@RequestParam("rnum") Integer rnum,@RequestParam("pnum") Integer pnum) {

        boardService.repliedelete(rnum);
        return "redirect:/detailview?pnum="+pnum;
    }
   @PostMapping("/replymod")
    public String replymod(@ModelAttribute Replies replie) {
     boardService.replymod(replie);
     return "redirect:/detailview?pnum="+replie.getPnum();
   }
}