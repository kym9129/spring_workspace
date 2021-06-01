package com.kh.spring.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.memo.model.service.MemoService;
import com.kh.spring.memo.model.vo.Memo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	private MemoService memoService;
	
	@GetMapping("/memo.do")
	public ModelAndView selectMemoList(ModelAndView mav) {
		log.debug("memoService = {}", memoService.getClass());
		
		//1. 비즈니스 로직 : memo 목록 조회
		List<Memo> memoList = memoService.selectMemoList();
		log.debug("list = {}", memoList);
		mav.addObject("memoList", memoList);
		
		//2. jsp위임
		mav.setViewName("memo/memo");
		return mav;
	}
	
	@PostMapping("/insertMemo.do")
	public String insertMemo(@RequestParam(name="memo", required=true) String memo) {
		log.debug("memo = {}", memo);
		try {
			//1. 비즈니스 로직
			int result = memoService.insertMemo(memo);
			
		}catch(Exception e) {
			log.error("메모 등록 오류!");
			throw e;
		}
		
		return "redirect:/memo/memo.do";
	}

}
