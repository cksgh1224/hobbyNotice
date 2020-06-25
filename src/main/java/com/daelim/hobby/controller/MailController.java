package com.daelim.hobby.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daelim.hobby.Service.MailService;

@Controller
@EnableAsync
@ResponseBody
public class MailController {
    @Autowired
    private MailService mailService;
 
    @RequestMapping(value = "/sendMail.do", method = RequestMethod.GET)
    public String sendSimpleMail(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");

        String memEmail = request.getParameter("memEmail");
        System.out.println("�Է��� �̸��� : " + memEmail);
        
        int randomNum = (int)(Math.random()*10000);
        System.out.println("������ȣ :" + randomNum);
        mailService.sendMail(memEmail,"Email ����","������ȣ :" + randomNum);
        
        return randomNum+"";
    }
}


