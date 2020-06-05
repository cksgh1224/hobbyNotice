package com.daelim.hobby.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daelim.hobby.Service.MemberService;
import com.daelim.hobby.Vo.MemberVO;



@Controller
public class MemberController {
	
	@Autowired
	public MemberService mService;
	MemberVO mVo;
	
	@RequestMapping("/")
	public String home(Model model) {
		return "main.page"; // ȸ������ ������(jsp)
	}
	// ȸ������
	@RequestMapping("/create_account_view")
	public String create_account_view(Model model) {
		return "/member/create_account_view"; // ȸ������ ������(jsp)
	}
	@RequestMapping("/create_account")
	public String create_account(MemberVO mVo) {
		System.out.println("create_account()");
		
		mService.mCreateAccount(mVo);
		return "/member/login_page"; // �α��� ������(jsp)
	}
	
	
	// ���̵� �ߺ��˻�
	@RequestMapping("/idCheck")
	public String idCheck(HttpServletRequest request, Model model) {
		mVo = mService.mIdCheck(request);
		
		if(mVo == null) { 
			model.addAttribute("msg", "��� ������ ���̵� �Դϴ�."); // �˻� ����� view���� ����ϱ� ���� model�� �߰�
		}else {
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
	public String login(MemberVO mVo, HttpServletRequest request, Model model) {
		mVo = mService.mLogin(mVo, request);
		
		if(mVo == null) { // �˻� ���н�
			model.addAttribute("msg", "���̵�, ��й�ȣ�� Ʋ���ϴ�."); // view�� �ѷ��ֱ� ���� model�� �߰�
			model.addAttribute("url", "member/login_page.jsp");
			return "/member/login_page";
		}
		
		return "/member/myInfo_page";
	}
	
	
	// ���̵� ã��
	@RequestMapping("/idSearch_page")
	public String idSearch_page() {
		return "/member/idSearch_page";
	}
	@RequestMapping("/idSearch")
	public String idSearch(MemberVO mVo, Model model) {
		mVo = mService.mIdSearch(mVo);
		if(mVo != null) {
			System.out.println("���̵� �˻� ���� : " + mVo.getMemId());
			model.addAttribute("mVo", mVo); // view�� ����� �ѷ��ֱ� ���� model�� �߰�
		}
		return "/member/idSearch_page";
	}
	
	
	// ��й�ȣ ã��
	@RequestMapping("/pwSearch_page")
	public String pwSearch_page() {
		return "/member/pwSearch_page";
	}
	@RequestMapping("/pwSearch")
	public String pwSearch(MemberVO mVo, Model model) {
		mVo = mService.mPwSearch(mVo);
		
		if(mVo != null) {
			System.out.println("��й�ȣ �˻� ���� : " + mVo.getMemPw());
			model.addAttribute("mVo", mVo); // view�� ����� �ѷ��ֱ� ���� model�� �߰�
		}
		
		return "/member/pwSearch_page";
	}
	
	
	// �� ����
	@RequestMapping("/myInfo_page")
	public String Login_MyInfo() {
		return "/member/myInfo_page";
	}
	
	
	// ȸ�� ���� ����
	@RequestMapping("/myInfo_modify_page")
	public String member_modify_page() {	
		return "/member/myInfo_modify_page";
	}
	@RequestMapping("/member_modify")
	public String member_modify(MemberVO mVo, HttpSession session) {
		mService.mMemberModify(mVo, session);
		
		return "redirect:myInfo_page";
	}
	
	// ��й�ȣ ����
	@RequestMapping("myPw_modify_page")
	public String myPw_modify_page() {
		return "/member/myPw_modify_page";
	}
	@RequestMapping("/memberPw_modify")
	public String memberPw_modify(MemberVO mVo, HttpSession session) {
		mService.mMemberPwModify(mVo, session);
		return "redirect:myInfo_page";
	}
	
	
	// �α׾ƿ� (���� ����)
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession(); // ������ ������. (������ ���� �����)
		if(session.isNew()) { // ó�� ������� �����̸� (�α��ε� ���°� �ƴѰ���)
			System.out.println("ó�� ������� �����Դϴ�.");
		}else {
			System.out.println("�α��ε� ���̵�: " + session.getAttribute("mId"));
			session.invalidate(); // ���� ����
			System.out.println("������ �����մϴ�");
		}
		
		return "/member/login_page";
	}
	
	
	// ȸ�� Ż��
	@RequestMapping("/mDelete")
	public String mDelete(HttpSession session) {
		mService.mMemberDelete(session); 
		session.invalidate(); // ������ �����Ѵ�
		return "/member/login_page";
	}
	
	
	
	// ��� ����Ʈ (������ ȸ�� ���)
	@RequestMapping("/mList")
	public String list(Model model) {
		mService.mList(model);
		return "/member/list";
	}
	
	
	
/*	
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
*/
	
} // end of MController
