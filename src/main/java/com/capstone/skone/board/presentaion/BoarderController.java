package com.capstone.skone.board.presentaion;

import com.capstone.skone.board.application.HotDealService;
import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.domain.HotDealBoard;
import com.capstone.skone.board.dto.CreateBoardDto;
import com.capstone.skone.board.dto.CreateFileDto;
import com.capstone.skone.board.dto.DetailBoardDto;
import com.capstone.skone.board.util.MD5Generator;
import com.capstone.skone.board.application.BoardService;
import com.capstone.skone.board.application.FileService;
import com.capstone.skone.board.dto.DetailHotDealBoardDto;

import java.io.File;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class BoarderController {

  private final BoardService boardService;
  private final HotDealService hotDealService;
  private final FileService fileService;

  @GetMapping("/board")
  public String viewHome() {
    return "board/board";
  }

  @PostMapping("/post")
  public String write(@RequestParam("file") MultipartFile files, CreateBoardDto createBoardDto, Authentication auth) {
    try {
      String origFilename = files.getOriginalFilename();
      String filename = new MD5Generator(origFilename).toString();
      /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
      String savePath = System.getProperty("user.dir") + "/src/main/resources/static/files";
      /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
      if (!new File(savePath).exists()) {
        try {
          new File(savePath).mkdir();
        } catch (Exception e) {
          e.getStackTrace();
        }
      }

      String filePath = savePath + "\\" + files.getOriginalFilename();
      files.transferTo(new File(filePath));

      CreateFileDto createFileDto = new CreateFileDto();
      createFileDto.setOrigFilename(origFilename);
      createFileDto.setFilename(filename);
      createFileDto.setFilePath(filePath);

      String fileName = fileService.saveFile(createFileDto);
      createBoardDto.setFilename(fileName);
      createBoardDto.setUserEmail(auth.getName());
      boardService.createBoard(createBoardDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/";
  }

  @GetMapping("/board/{id}")
  public String detailBoard(@PathVariable("id") Long id, Model model) {
    DetailBoardDto detail = boardService.getDetailBoard(id);
    model.addAttribute("detail", detail);
    return "board/board_details";
  }

  @GetMapping("/hot_deal")
  public String hotDealAllBoard(Model model,
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<HotDealBoard> hotDealBoard = hotDealService.pageList(pageable);

    int start = (int) (Math.floor(hotDealBoard.getNumber() / 10) * 10 + 1);
    int last = Math.min(start + 9, hotDealBoard.getTotalPages());

    model.addAttribute("hotDealBoard", hotDealBoard);
    model.addAttribute("start", start);
    model.addAttribute("last", last);

    return "board/hot_deal_main";
  }

  @GetMapping("/hot_deal/{id}")
  public String detailHotDealBoard(@PathVariable("id") Long id, Model model) {
    DetailHotDealBoardDto detail = hotDealService.getDetailHotDealBoard(id);
    model.addAttribute("detail", detail);
    return "board/hot_deal_board_details";
  }

<<<<<<< HEAD
  @PostMapping("/board/remove")
  public String  boardRemove(@ModelAttribute("createBoardDto") CreateBoardDto createBoardDto, Authentication auth, HttpServletRequest request) throws Exception{

    /** 게시글 Id 받아오는 부분*/
    Long id = createBoardDto.getId();
    Board board = boardService.selectBoard(id);

    /**로그인 정보 비교부분*/
    if(!ObjectUtils.isEmpty(board)){
      String loginUser = auth.getName();
      String writer = board.getUserEmail();
      // 로그인 정보와 게시글 작성자가 동일한 경우 게시물 삭제처리
      if(StringUtils.equals(writer,loginUser)){
        boardService.deleteBoard(id);
      }
    }
    return "redirect:/";
  }
=======
  @GetMapping("/board/del")
  public String deleteBoard(@LoginSession)




>>>>>>> 627844bdc0d322ec6a31ae4d5310db661a3757ab
}