package com.daelim.hobby.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daelim.hobby.Service.MemberService;
import com.daelim.hobby.vo.MemberVO;



@Controller
public class MemberController {
		
	@Autowired
	public MemberService mService;

	
	// ȸ������
	@RequestMapping("/Login_SignUp") // create_account_view 
	public String create_account_view(Model model) {
		return "/member/Login_SignUp";
	}
	@RequestMapping("/create_account")
	public String create_account(MemberVO mVo) {
		mService.mCreateAccount(mVo);
		return "/member/Login"; // /member/login_page
	}
	
	
	
	
	
	
	
	
	
/*	
	
	
	// ���̵� �ߺ��˻�
	@RequestMapping("/idCheck")
	public String idCheck(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		mService.mIdCheckCommand(model);
		
		if(model.containsAttribute("dto")) {
			System.out.println("��� ������ ���̵� �Դϴ�.");
			model.addAttribute("msg", "��� ������ ���̵� �Դϴ�.");
		}
		else {
			System.out.println("����Ҽ� ���� ���̵� �Դϴ�.");
			model.addAttribute("msg", "����Ҽ� ���� ���̵� �Դϴ�.");
		}
		
		return "member/create_account_view";
	}
	
	
	// �α���
	@RequestMapping("/login_page")
	public String login_view() {
		return "/member/login_page";
	}
	@RequestMapping("/login")
	public String login(MemberVO dto, HttpServletRequest request, Model model) {
		mService.mLoginCommand(dto, request, model);
		
		if(!model.containsAttribute("dto")) {
			model.addAttribute("msg", "���̵�, ��й�ȣ�� Ʋ���ϴ�.");
			model.addAttribute("url", "member/login_page.jsp");
			return "/member/login_page";
		}
		return "/member/login_view";
	}
	
	
	// ��� ����Ʈ
	@RequestMapping("/mList")
	public String list(Model model) {
		mService.mListCommand(model);
		return "/member/list";
	}
	
	
	// ���̵� ã��
	@RequestMapping("/idSearch_page")
	public String idSearch_page() {
		return "/member/idSearch_page";
	}
	@RequestMapping("/idSearch")
	public String idSearch(MemberVO dto, Model model) {	
		mService.MIdSearchCommand(dto, model);
		return "/member/idSearch_page";
	}
	
	
	// ȸ�� ���� ����  (�������� �ϰ� Home���� �����̷�Ʈ ��Ű�µ� ������ ������ �ٲ��� ����)
	@RequestMapping("/member_modify_page")
	public String member_modify_page() {	
		return "/member/member_modify_page";
	}
	@RequestMapping("/member_modify")
	public String member_modify(MemberVO dto, HttpSession session) {
		mService.mMemberModifyCommand(dto, session);
		return "redirect:Home";
	}
	
	
	// Ȩ������
	@RequestMapping("/Home")
	public String Home(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		if(session.getAttribute("mId") == null) { // ó�� ������� �����̸� -> �α��� ���°� �ƴϸ� (���ǿ� ���ε��� id�� ����) -> �α��� �������� ������
			System.out.println("�α��κ��� �ϼ���!");
			return "member/login_page";
		}
		
		System.out.println("���� ���̵�: " + session.getAttribute("mId"));
		return "member/login_view"; // �α����� �Ǿ��ִ� �����̸� �α��κ並 �����ش�
	}
	
	
	// �α׾ƿ�
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		System.out.println("logout()");
		session = request.getSession();
		if(session.isNew()) {
			System.out.println("ó�� ������� �����Դϴ�.");
		}else {
			System.out.println("�α��ε� ���̵�: " + session.getAttribute("mId"));
			session.invalidate();
			System.out.println("������ �����մϴ�");
		}
		
		return "/member/login_page";
	}
	
	
	// ȸ�� Ż��
	@RequestMapping("/mDelete")
	public String mDelete(HttpSession session) {
		mService.mMemberDelete(session);
		return "/member/login_page";
	}
	

*/

	
} // end of MController
