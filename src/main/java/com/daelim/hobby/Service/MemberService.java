package com.daelim.hobby.Service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.daelim.hobby.vo.MemberVO;
import com.dalim.hobby.dao.MemberDAO;

@Service
public class MemberService {
	
	@Autowired
	public MemberDAO mDao;
	public MemberVO mVo;
	
	
	// ȸ������
	public void mCreateAccount(MemberVO dto) {
		mDao.createAccount(mVo.getMemId(), mVo.getMemPw(), mVo.getMemName(), mVo.getMemEmail(), mVo.getMemPhone(), 
				mVo.getMemCity(), mVo.getMemRegion(), mVo.getMemPwHint(), mVo.getMemPwAns());
	}
	
	
	
	
	
	
	
	
/*	
	
	// ���̵� �ߺ��˻�
	public void mIdCheckCommand(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String mId = request.getParameter("mId");
		
		dto = dao.checkId(mId);
		
		if(dto == null) {
			model.addAttribute("dto", "ok");
		}
	}
	
	
	// �α���
	public void mLoginCommand(MemberVO dto, HttpServletRequest request, Model model) {
		dto = dao.login(dto.getmId(), dto.getmPwd());
		
		if(dto != null) {
			model.addAttribute("dto", dto);
			
			HttpSession session = request.getSession(); // �α��� -> ���� ����
			session.setAttribute("dto2", dto); 
			
			session.setAttribute("mId", dto.getmId());
			session.setAttribute("mPwd", dto.getmPwd());
			session.setAttribute("mName", dto.getmName());
			
			String myid = (String) session.getAttribute("mId");
			String mypwd = (String) session.getAttribute("mPwd");
			System.out.println("���� ���̵�: " + myid);
			System.out.println("���� ��й�ȣ: " + mypwd);
		}
	}
	
	
	// ��� ����Ʈ
	public void mListCommand(Model model) {
		ArrayList<MemberVO> dtos = dao.list();
		model.addAttribute("list", dtos);
	}
	
	
	// ���̵� ã��
	public void MIdSearchCommand(MemberVO dto, Model model) {
		String mName = dto.getmName();
		String mEmail = dto.getmEmail();
		String mBirth = dto.getmBirth();
		
		dto = dao.searchId(mName, mEmail, mBirth);
		
		if(dto != null) {
			System.out.println("���̵� �˻� ����.");
			model.addAttribute("dto", dto);
		}
	}

	
	// ȸ�� ���� ����
	public void mMemberModifyCommand(MemberVO dto, HttpSession session) {
		
		String mName = dto.getmName();
		String mMobile = dto.getmMobile();
		String mEmail = dto.getmEmail();
		String mRegion = dto.getmRegion();
		String mBirth = dto.getmBirth();
		
		// �α����Ҷ�  ���ǿ� mId�� ��������. getAttribute�� �ؼ� mId�� �����´ٸ� -> �α��� �� ����
		String mId = (String) session.getAttribute("mId");
		System.out.println("session.getAttribute mId : " + mId);
		
		dao.memberModify(mId, mName, mMobile, mEmail, mRegion, mBirth);
	}
	
	
	// ȸ�� Ż��
	public void mMemberDelete(HttpSession session) {
		String mId = (String)session.getAttribute("mId");
		System.out.println("���� ���̵�(������ ���̵�): " + mId);
		dao.memberDelete(mId);
	}
	
	
	

*/

	
}

