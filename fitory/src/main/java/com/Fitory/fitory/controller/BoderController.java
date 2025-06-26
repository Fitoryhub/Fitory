package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.CommentDTO;
import com.Fitory.fitory.dto.PtitlePcategoryDTO;
import com.Fitory.fitory.dto.RepliesDTO;
import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;


@Controller
public class BoderController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;
    @Autowired
    private PlikeService plikeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ClikeService clikeService;
    @Autowired
    private ReplieService replieService;
    @Autowired
    private RlikeService rlikeService;

    //게시판 들어온창(검색기준으로 출력될수있게 구현)
    @GetMapping("/boardlist")
    public String boardlist(HttpSession session, Model model, @PageableDefault(page = 0, size = 10, sort = "pnum"
                                    , direction = Sort.Direction.DESC) Pageable pageable
            , @RequestParam(required = false) String pcategory,
                            @RequestParam(required = false) String ptitle) {

        PtitlePcategoryDTO searchboard = new PtitlePcategoryDTO();
        searchboard.setPcategory(pcategory);
        searchboard.setPtitle(ptitle);

        Page<Board> board;

        if (searchboard.getPcategory() == null||searchboard.getPcategory().equals("")) {
            board = boardService.alllist(pageable);
        } else {
            if (searchboard.getPcategory().equals("all")) {
                board = boardService.searchptitle(searchboard, pageable);
            } else {

                board = boardService.searchboard(searchboard, pageable);
            }
        }

        int nowpage = board.getPageable().getPageNumber() + 1;
        int startpage = Math.max(nowpage - 2, 1);  // 현재 페이지를 중심으로 2개 앞뒤
        int endpage = Math.min(startpage + 4, board.getTotalPages()); // 총 5개만 보이도록 조정

        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("list", board);
        model.addAttribute("nowpage", nowpage);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);
        model.addAttribute("selectedCategory", pcategory);
        return "/boardlist";
    }


    // 게시글 저장 메서드
    @PostMapping("/submit_post")
    public String submitPost(@ModelAttribute("board") Board board,
                             @RequestParam("files") MultipartFile[] filelist) throws Exception {

        System.out.println("확인한다" + board.getNickname());

        boardService.savepost(board);
        String ProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";



        for (MultipartFile onefile : filelist) {
            if (!onefile.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + onefile.getOriginalFilename();
                File savfile = new File(ProjectPath, filename);
                onefile.transferTo(savfile);
                Files files = new Files();
                files.setFilename(filename);
                files.setPnum(board.getPnum());
                fileService.filesave(files);

            }
        }

        return "redirect:/boardlist";

    }

    //게시글 상세보기 메서드
    @GetMapping("/detailview")
    public String detailview(@RequestParam("pnum") Integer pnum, Model model, HttpSession session) {

        boardService.updateplook(pnum);
        Board board = boardService.searchoneboard(pnum);
        SessionUserDTO userdto = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = userdto.getId();
        Plike plike = plikeService.findplike(uid, pnum);
        List<Files> files = fileService.findfile(pnum);


        Integer num = board.getPnum();
        List<Comment> comments = commentService.findcomment(num);


        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
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
        List<Clike> clike = clikeService.findclike(num);


        for (CommentDTO commentDTO : commentDTOS) {
            for (Clike c : clike) {
                if (c.getUid().equals(uid) && commentDTO.getCnum().equals(c.getCnum())) {
                    commentDTO.setLiked(true);
                }
            }
        }
        List<Replies> replie = replieService.findreplies(num);


        List<RepliesDTO> replies = new ArrayList<>();

        for (Replies onereplie : replie) {
            RepliesDTO repliesDTO = new RepliesDTO();
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

        List<Rlikes> rlikes = rlikeService.findrlike(num);


        for (RepliesDTO repliesDTO : replies) {
            for (Rlikes rlike : rlikes) {
                if (rlike.getUid().equals(uid) && rlike.getRnum().equals(repliesDTO.getRnum())) {
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


    //게시글작성창으로 넘어가는 메서드
    @GetMapping("/write")
    public String write(Board board, Model model) {
        model.addAttribute("board", board);
        return "write";

    }


    //비동기통신으로 게시글 좋아요 누르는 메서드 (return값은 게시글 좋아요 갯수)
    @PostMapping("/plike")
    @ResponseBody
    public Integer plike(@ModelAttribute Plike plike) {
        Integer pnum = plike.getPnum();
        Integer num = boardService.blike(pnum);
        plikeService.saveplike(plike);


        return num;

    }

    // 비동기 통신으로 게시글 좋아요 취소하는 메서드 (return값은 게시글 좋아요갯수)
    @PostMapping("/phate")
    @ResponseBody
    public Integer phate(@ModelAttribute Plike plike, Model model) {

        Integer pnum = plike.getPnum();
        Board board = boardService.bhate(pnum);
        String uid = plike.getUid();
        plikeService.phate(pnum, uid);
        Integer num = board.getPlike();
        return num;

    }

    @PostMapping("/comment")
    @ResponseBody
    public Map<String, Object> comment(@ModelAttribute Comment comment, HttpSession session) {
        // 댓글 저장
        if (!isEmpty(comment)) {
            commentService.commentsave(comment);
        }

        // 로그인 정보 추출
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(comment.getPnum(), uid, nickname);
    }


    private Map<String, Object> buildCommentResponse(Integer pnum, String uid, String loginNickname) {
        Map<String, Object> response = new HashMap<>();

        // 댓글 리스트 조회
        List<Comment> comments = commentService.findcomment(pnum);
        List<CommentDTO> commentDTOS = comments.stream().map(c -> {
            CommentDTO dto = new CommentDTO();
            dto.setCnum(c.getCnum());
            dto.setPnum(c.getPnum());
            dto.setCbody(c.getCbody());
            dto.setNickname(c.getNickname());
            dto.setUid(c.getUid());
            dto.setCdate(c.getCdate());
            dto.setClike(c.getClike());
            return dto;
        }).toList();

        // 댓글 좋아요 상태
        List<Clike> clikes = clikeService.findclike(pnum);
        for (CommentDTO dto : commentDTOS) {
            for (Clike c : clikes) {
                if (c.getUid().equals(uid) && dto.getCnum().equals(c.getCnum())) {
                    dto.setLiked(true);
                }
            }
        }

        // 대댓글 리스트
        List<Replies> repliesList = replieService.findreplies(pnum);
        List<RepliesDTO> replies = repliesList.stream().map(r -> {
            RepliesDTO dto = new RepliesDTO();
            dto.setRnum(r.getRnum());
            dto.setCnum(r.getCnum());
            dto.setPnum(r.getPnum());
            dto.setRbody(r.getRbody());
            dto.setNickname(r.getNickname());
            dto.setUid(r.getUid());
            dto.setRdate(r.getRdate());
            dto.setRlikes(r.getRlikes());
            return dto;
        }).toList();

        // 대댓글 좋아요 상태
        List<Rlikes> rlikes = rlikeService.findrlike(pnum);
        for (RepliesDTO dto : replies) {
            for (Rlikes r : rlikes) {
                if (r.getUid().equals(uid) && r.getRnum().equals(dto.getRnum())) {
                    dto.setCheck(true);
                }
            }
        }

        // 응답 구성
        response.put("comments", commentDTOS);
        response.put("replies", replies);
        response.put("loginNickname", loginNickname);
        response.put("uid", uid);
        return response;
    }


    //댓글 좋아요 비동기 통신으로 누르는 메서드(return 은 좋아요 갯수)
    @PostMapping("/clike")
    @ResponseBody
    public int clike(@ModelAttribute Clike clike) {
        Integer cnum = clike.getCnum();
        clikeService.save(clike);

        Comment comment = commentService.clike(clike);

        int num = comment.getClike();
        return num;

    }


    //댓글 좋아요 비동기 통신으로 취소하는 메서드(return 은 좋아요 갯수)

    @PostMapping("/chate")
    @ResponseBody
    public int chate(@ModelAttribute Clike clike) {

        Comment comment = commentService.chate(clike);
        clikeService.delete(clike);
        int num = comment.getClike();
        return num;
    }


    //대댓글 작성구현 메서드

    @PostMapping("/replies")
    @ResponseBody
    public Map<String, Object> replies(@ModelAttribute Replies replies , HttpSession session) {
        replieService.repliessave(replies);
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(replies.getPnum(), uid, nickname);

    }


    //대댓글 좋아요 비동기 통신으로 누르는 메서드 (return 좋아요 갯수)

    @PostMapping("/rlike")
    @ResponseBody
    public int rlike(@ModelAttribute Rlikes rlikes) {

        rlikeService.rlikesave(rlikes);
        Integer rnum = rlikes.getRnum();
        replieService.replieslike(rnum);

        int num = replieService.rlike(rnum);

        return num;
    }


    //대댓글 좋아요 비동기통신으로 취소하는 메서드 ( return은 좋아요 갯수)

    @PostMapping("/rhate")
    @ResponseBody
    public int rhate(@ModelAttribute Rlikes rlikes) {
        String uid = rlikes.getUid();
        Integer rnum = rlikes.getRnum();
        rlikeService.rhate(uid, rnum);
        replieService.replieshate(rnum);
        int num = replieService.rlike(rnum);
        return num;
    }


    //게시글 수정창으로 넘어가는 메서드

    @GetMapping("/boardmod")
    public String boardmod(Model model, @RequestParam("pnum") Integer pnum) {

        Board board = new Board();
        board.setPnum(pnum);
        model.addAttribute("mod", true);
        model.addAttribute("board", board);
        return "write";
    }

    //게시글 수정내용을 저장하는 메서드

    @PostMapping("/mod_post")
    public String modpost(@ModelAttribute Board board,
                          @RequestParam("files") MultipartFile[] filelist) throws Exception {
        boardService.board_mod(board);
        Board first =boardService.searchoneboard(board.getPnum());
        List <Files> files=fileService.findfile(first.getPnum());

        String ProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        for(Files f : files) {
            String name=f.getFilename();
            String path=ProjectPath+'\\'+name;

            File f2 = new File(path);
            if (f2.exists()) {
                f2.delete(); // 실제 파일 삭제
            }
        }

        System.out.println(board.getPnum());

        for (MultipartFile onefile : filelist) {
            if (!onefile.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + onefile.getOriginalFilename();
                File savfile = new File(ProjectPath, filename);
                onefile.transferTo(savfile);
                Files file = new Files();
                file.setFilename(filename);
                file.setPnum(board.getPnum());
                fileService.filesave(file);
            }
        }

        return "redirect:/detailview?pnum=" + board.getPnum();
    }


    @GetMapping("/boarddelete")
    public String boarddelete(@RequestParam("pnum") Integer pnum) {
        // 1. 게시글 삭제


        List<Files> files = fileService.findfile(pnum);
        boardService.boarddelete(pnum);


        String basePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        for (Files file : files) {
            String filename = file.getFilename();
            String fullPath = basePath + "\\" + filename;

            File f = new File(fullPath);
            if (f.exists()) {
                f.delete(); // 실제 파일 삭제
            }
        }

        return "redirect:/boardlist";
    }

    // 댓글 삭제 구현 메서드

    @PostMapping("/commentdelete")
    @ResponseBody
    public Map<String,Object> commentdelete(@RequestParam("pnum") Integer pnum, @RequestParam("cnum") Integer cnum ,HttpSession session) {
        commentService.commentdelete(cnum);
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(pnum, uid, nickname);
    }

    //댓글 수정 구현 메서드
    @PostMapping("/commentmod")
    @ResponseBody
    public Map<String, Object> commentmod(@ModelAttribute Comment comment ,HttpSession session) {

        commentService.commentmod(comment);
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(comment.getPnum(), uid, nickname);

    }


    //대댓글 삭제 구현 메서드
    @PostMapping("/repliedelete")
    @ResponseBody
    public Map<String, Object> repliedelete(@RequestParam("rnum") Integer rnum, @RequestParam("pnum") Integer pnum , HttpSession session) {

        replieService.repliedelete(rnum);
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(pnum, uid, nickname);
    }

    //대댓글 수정 구현 메서드
    @PostMapping("/replymod")
    @ResponseBody
    public Map<String, Object> replymod(@ModelAttribute Replies replie,HttpSession session) {
        replieService.replymod(replie);
        SessionUserDTO user = (SessionUserDTO) session.getAttribute("userInfo");
        String uid = user.getId();
        String nickname = user.getNickname();

        return buildCommentResponse(replie.getPnum(), uid, nickname);
    }

    @GetMapping("/myboard")
    @ResponseBody
    public  Map<String, Object> myboard(@RequestParam("uid")String uid) {

        System.out.println(uid);

        List<Board> list= boardService.myboardlist(uid);

        System.out.println(list);

        Map<String, Object> boardlist = new HashMap<>();

        boardlist.put("list", list);

        System.out.println(boardlist);

        return boardlist;
    }
    @PostMapping("/dellistcomment")
    @ResponseBody
    public Map<String, Object> dellistcomment(@RequestParam List<Integer> list , @RequestParam String uid) {

        for (Integer i : list) {
            commentService.commentdelete(i);
        }
        List<Comment> newlist =commentService.mycomment(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("list", newlist);
        return map;
    }
    @PostMapping("/dellistboard")
    @ResponseBody
    public Map<String, Object> dellistboard(@RequestParam List<Integer> list , @RequestParam String uid) {
        for (Integer i : list) {
            boardService.boarddelete(i);
        }
        List<Board> newlist =boardService.myboardlist(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("list", newlist);
        return map;
    }

    @GetMapping("/mycomment")
    @ResponseBody
    public  Map<String, Object> mycomment(@RequestParam("uid")String uid) {
        List<Comment> comments = commentService.mycomment(uid);
        Map<String , Object> commentlist = new HashMap<>();
        commentlist.put("list", comments);
        return commentlist;
    }

}