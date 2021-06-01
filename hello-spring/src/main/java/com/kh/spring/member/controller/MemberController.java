package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;


/**
 * Model
 * 
 * - MVC패턴의 Model이 아니다.
- view단에 전달하기 위한 데이터를 저장하는 Map
1. Model <<interface>>
2. ModelMap
3. ModelAndView

→결국은 ModelAndView로 통합되서 Spring Container에 의해 관리된다.
 *
 */




@Slf4j
@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember", "next"})
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/**
	 * view단에서는 ${common.adminEmail},  ${common.adminPhone} 사용 가능
	 */
	@ModelAttribute("common")
	public Map<String, Object> common(){
		log.info("@ModelAttribute(\"common\")");
		Map<String, Object> map = new HashMap<>();
		map.put("adminEmail", "admin@kh.or.kr");
		map.put("adminPhone", "070-1234-5678");
		return map;
	}
	
	@GetMapping("/memberEnroll.do")
	public void memberEnroll() {
		// /WEB-INF/views/member/memberEnroll.jsp 로 자동 포워딩됨
		// DefaultRequestToViewNameTranslator빈이 요청 주소에서 viewName을 유추함.
	}
	
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		log.info("member = {}", member);
		try {
			//0. 비밀번호 암호화처리
			String rawPassword = member.getPassword();
			String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
			member.setPassword(encodedPassword);
			log.info("mbmer(암호화처리 이후) = {}", member);
			
			//1. 업무로직
			int result = memberService.insertMember(member);
			
			//2. 사용자피드백
			redirectAttr.addFlashAttribute("msg", "회원가입성공");
			
		}catch(Exception e){
			log.error("회원가입 오류!", e);
			throw e;
		}
		return "redirect:/";
	}
	/**
	 * java.sql.Date, java.util.Date 필드에 값 대입 시
	 * 사용자 입력값이 ""인 경우, null로 처리될 수 있도록 설정
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//org.springframework.beans.propertyeditors.CustomDateEditor.CustomDateEditor(DateFormat dateFormat, boolean allowEmpty)
		//allowEmpty true : 빈문자열이 들어오는걸 허용하겠다.
		PropertyEditor editor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, editor);
		
	}
	
	@GetMapping("/memberLogin.do")
	public void memberLogin(
						@SessionAttribute(required = false) String next,
						@RequestHeader(name = "Referer", required = false) String referer, 
						Model model) {
		log.info("referer = {}", referer);
		log.info("next = {}", next);
		if(referer != null)
			model.addAttribute("next", referer);
	}
	
	@PostMapping("/memberLogin.do")
	public String memberLogin(
					@RequestParam String id, 
					@RequestParam String password, 
					@SessionAttribute(required = false) String next,
					Model model,
					RedirectAttributes redirectAttr) {
		
		//1. 업무로직
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		//암호화 안된 비번 암호화시키려고 임시로 작성한 코드
//		log.info("encryptedPassword = {}", bcryptPasswordEncoder.encode(password));
		
		//2. 로그인 여부 분기처리
		//boolean matches(CharSequence rawPassword, String encodedPassword)
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			//로그인 성공
			//loginMember를 세션속성으로 저장하려면, class에 @SessionAttributes로 등록
			model.addAttribute("loginMember", member);
			//사용한 next값은 제거
			model.addAttribute("next", null);
		}
		else {
			//로그인 실패
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 틀립니다.");
			return "redirect:/member/memberLogin.do";
		}
		//사용한 next값은 제거
		model.addAttribute("next", null);
		
		return "redirect:" + (next != null ? next : "/");
	}
	
	/**
	 * @SessionAttributes를 통해서 등록한 session속성은
	 * SessionStatus객체에 대해서 complete 처리해야한다 
	 */
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus status) {
		if(!status.isComplete()) //complete되지 않았다면 
			status.setComplete(); //complete한다
		
		return "redirect:/";
	}
	
	/**
	 * 로그인한 사용자 정보 열람하기
	 * @return
	 */
	@GetMapping("/memberDetail.do")
	public ModelAndView memberDetail(ModelAndView mav, @SessionAttribute Member loginMember) {
		log.info("loginMember = {}", loginMember);
		//속성 저장
		mav.addObject("time", System.currentTimeMillis());
		//viewName 설정
		mav.setViewName("member/memberDetail");
		return mav;
	}
	
	//혼자할떈 Model엔 담았는데 강사님은 ModelAndView로 담으심.
	@PostMapping("/memberUpdate.do")
	public ModelAndView memberUpdate(
						ModelAndView mav,
						@ModelAttribute Member member,
						@ModelAttribute("loginMember") Member loginMember,
						RedirectAttributes redirectAttr,
						HttpServletRequest request
						) {
		log.debug("member = {}", member);
		log.debug("loginMember = {}", loginMember);
		try {
			//1. business logic
			int result = memberService.updateMember(member);
			if(result == 0)
				throw new IllegalArgumentException("존재하지 않는 사용자 정보 : " + member.getId());
			
			//memberDetail화면에 수정 정보 출력하기 위해 loginMember에 수정정보 담기
			//model.addAttribute("loginMember", member);
			
			//2. user feedback & redirect
//			mav.setViewName("redirect:/member/memberDetail.do");
			
			//리다이렉트 시 자동 생성되는 queryString 방지
			RedirectView view = new RedirectView(request.getContextPath() + "/member/memberDetail.do");
			view.setExposeModelAttributes(false); //주소창에 queryString자동생성 false처리. 이걸 위해 view객체 사용
			mav.setView(view);
			
			
			//redirectAttr.addFlashAttribute("msg", "회원 정보 수정 성공!");
			
			//ModelAndView와 RedirectAttributs가 충돌 시 FlashMap을 직접 사용
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			flashMap.put("msg", "회원 정보 수정 성공!!!");
		
		}catch(Exception e) {
			log.debug("회원정보 수정 오류!", e);
			throw e;
		}
		return mav;
	}
	
	@PostMapping("/memberDelete.do")
	public String memberDelete(@RequestParam String id,
							RedirectAttributes redirectAttr) {
		try {
			//1. 업무로직 : 회원삭제
			int result = memberService.deleteMember(id);
			if(result == 0)
				throw new IllegalArgumentException("존재하지 않는 사용자 정보 : " + id);
			
			//2. 사용자 피드백 + 리다이렉트 (로그아웃)
			redirectAttr.addFlashAttribute("msg", "회원 탈퇴 성공!");
		}catch(Exception e) {
			log.debug("회원탈퇴 오류!", e);
			throw e;
		}
		
		return "redirect:/member/memberLogout.do";
	}
	
	/**
	 * Spring Ajax(json) 3가지 방법
	 * 1. gson - 응답메세지에 json문자열을 직접 출력 (이미 해본 방법)
	 * 2. jsonView 빈을 통해 처리하기 - model에 담긴 데이터를 json으로 변환, 응답에 출력
	 * 3. @ResponseBody - 리턴된 자바객체를 json으로 변환, 응답에 출력
	 * 4. ResponseEntity<T>
	 */
	@GetMapping("/checkIdDuplicate1.do")
	public String checkIdDuplicate1(@RequestParam String id, Model model) {
		//1. 업무로직
		Member member = memberService.selectOneMember(id);
		boolean available = member == null; //null일 경우 true
		
		//2. Model에 속성 저장
		model.addAttribute("available", available); //이거만 담아도 됨
		model.addAttribute("id", id);
		
		return "jsonView";
	}
	
	@GetMapping("/checkIdDuplicate2.do")
	@ResponseBody
	public Map<String, Object> checkIdDuplicate2(@RequestParam String id) {
		//1. 업무로직
		Member member = memberService.selectOneMember(id);
		boolean available = member == null; //null일 경우 true
		
		//2. map에 요소 저장 후 리턴
		Map<String, Object> map = new HashMap<>();
		map.put("available", available);
		map.put("id", id);
		
		return map;
	}
	
	@GetMapping("/checkIdDuplicate3.do")
	public ResponseEntity<Map<String, Object>> checkIdDuplicate3(@RequestParam String id) {
		//1. 업무로직
		Member member = memberService.selectOneMember(id);
		boolean available = member == null; //null일 경우 true
		
		//2. map에 요소 저장 후 리턴
		Map<String, Object> map = new HashMap<>();
		map.put("available", available);
		map.put("id", id);
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(map);
	}

}
