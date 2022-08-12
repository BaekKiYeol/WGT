package com.varxyz.wgt.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.varxyz.wgt.board.domain.Board;
import com.varxyz.wgt.board.service.BoardService;
import com.varxyz.wgt.board.service.BoardServiceImpl;

@Controller
public class MypageController {
	BoardService service = new BoardServiceImpl();
	
	// 등록하기 화면
	@GetMapping("/board/mypage")
	public String postForm(Model model) {
		return "/board/mypage";
	}
	
	@PostMapping("/board/mypage")
	public String post(Board board, Model model) {
		model.addAttribute("Board", board);
		service.create(board,"imgName");
		model.addAttribute("msg", "게시글 수정을 완료하였습니다.");
		model.addAttribute("url","home");
		return "alert/alert";
	}
	
	@PostMapping("/board/delete")
	public String delete(Board board, Model model) {
		//board.getNumber 자리에 게시글 번호 받아온거 넣으면 되염
//		service.delete(board.getNumber()); 
		model.addAttribute("Board", board);
		model.addAttribute("msg", "게시글 삭제를 완료하였습니다.");
		model.addAttribute("url","home");
		return "alert/alert";
	}
	

}