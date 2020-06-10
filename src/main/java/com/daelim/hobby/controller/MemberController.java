package com.daelim.hobby.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daelim.hobby.service.MemberService;
import com.daelim.hobby.vo.MemberVO;


@Controller
public class MemberController {
	
	@Autowired
	public MemberService mService;
	MemberVO mVo;
	
	// 메인 페이지
	@RequestMapping("/")
	public String home(Model model) {
		return "main"; 
	}
	
	@RequestMapping("/search")
	public String search(Model model) {
		return "main_search_results.part"; 
	}
	
	
	
	
	// 회원가입
	@RequestMapping("/create_account_view")
	public String create_account_view(Model model) {
		return "/member/create_account_view"; 
	}
	@RequestMapping("/create_account")
	public String create_account(MemberVO mVo) {
		System.out.println("create_account()");
		
		mService.mCreateAccount(mVo);
		return "/member/login_page"; 
	}
	
	
	// 아이디 중복검사
	@RequestMapping("/idCheck")
	public String idCheck(HttpServletRequest request, Model model) {
		mVo = mService.mIdCheck(request);
		
		if(mVo == null) { 
			model.addAttribute("msg", request.getParameter("memId") + " 사용 가능한 아이디 입니다."); // 검색 결과를 view에서 사용하기 위해 model에 추가
			model.addAttribute("result","true");
		}else {
			model.addAttribute("msg", request.getParameter("memId") + " 사용할수 없는 아이디 입니다.");
			model.addAttribute("result","false");
		}
		
		return "member/create_account_view";
	}
	
	
	// 로그인
	@RequestMapping("/login_page")
	public String login_view() {
		return "/member/login_page";
	}
	@RequestMapping("/login")
	public String login(MemberVO mVo, HttpServletRequest request, Model model) {
		mVo = mService.mLogin(mVo, request);
		
		if(mVo == null) { // 검색 실패시
			model.addAttribute("msg", "아이디, 비밀번호가 틀립니다."); // view에 뿌려주기 위해 model에 추가
			model.addAttribute("url", "member/login_page.jsp");
			return "/member/login_page";
		}
		
		return "/member/myInfo_page";
	}
	
	
	// 아이디 찾기
	@RequestMapping("/idSearch_page")
	public String idSearch_page() {
		return "/member/idSearch_page";
	}
	@RequestMapping("/idSearch")
	public String idSearch(MemberVO mVo, Model model) {
		mVo = mService.mIdSearch(mVo);
		if(mVo != null) {
			System.out.println("아이디 검색 성공 : " + mVo.getMemId());
			model.addAttribute("mVo", mVo); // view에 결과를 뿌려주기 위해 model에 추가
		}
		return "/member/idSearch_page";
	}
	
	
	// 비밀번호 찾기
	@RequestMapping("/pwSearch_page")
	public String pwSearch_page() {
		return "/member/pwSearch_page";
	}
	@RequestMapping("/pwSearch")
	public String pwSearch(MemberVO mVo, Model model) {
		mVo = mService.mPwSearch(mVo);
		
		if(mVo != null) {
			System.out.println("비밀번호 검색 성공 : " + mVo.getMemPw());
			model.addAttribute("mVo", mVo); // view에 결과를 뿌려주기 위해 model에 추가
		}
		
		return "/member/pwSearch_page";
	}
	
	
	// 내 정보
	@RequestMapping("/myInfo_page")
	public String Login_MyInfo() {
		return "/member/myInfo_page";
	}
	
	
	// 회원 정보 수정
	@RequestMapping("/myInfo_modify_page")
	public String member_modify_page() {	
		return "/member/myInfo_modify_page";
	}
	@RequestMapping("/member_modify")
	public String member_modify(MemberVO mVo, HttpSession session) {
		mService.mMemberModify(mVo, session);
		return "redirect:myInfo_page";
	}
	
	// 비밀번호 변경
	@RequestMapping("myPw_modify_page")
	public String myPw_modify_page() {
		return "/member/myPw_modify_page";
	}
	@RequestMapping("/memberPw_modify")
	public String memberPw_modify(MemberVO mVo, HttpSession session) {
		mService.mMemberPwModify(mVo, session);
		return "redirect:myInfo_page";
	}
	
	
	// 로그아웃 (세션 삭제)
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession(); // 세션을 가져옴. (없으면 새로 만든다)
		if(session.isNew()) { // 처음 만들어진 세션이면 (로그인된 상태가 아닌거임)
			System.out.println("처음 만들어진 세션입니다.");
		}else {
			System.out.println("로그인된 아이디: " + session.getAttribute("mId"));
			session.invalidate(); // 세션 삭제
			System.out.println("세션을 삭제합니다");
		}
		return "redirect:/"; // 홈 페이지로 이동
	}
	
	
	// 회원 탈퇴
	@RequestMapping("/mDelete")
	public String mDelete(HttpSession session) {
		mService.mMemberDelete(session); 
		session.invalidate(); // 세션을 삭제한다
		return "/member/login_page";
	}
	
	
	
	// 멤버 리스트 (가입한 회원 목록)
//	@RequestMapping("/mList")
//	public String list(Model model) {
//		mService.mList(model);
//		return "/member/list";
//	}
	
	
} // end of MController
