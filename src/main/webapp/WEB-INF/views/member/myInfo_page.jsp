<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="myInfo_page.css" rel="stylesheet" />

<title>내정보</title>
</head>
<body>

<div class="mem_myInfo">
	<h1 id="mem_myInfo_title">${mVo.memName } 님의 정보관리</h1>

	<form name="myInfo" class="mem_myInfo_section">

		<a href="myInfo_modify_page"> <input type="button" value="회원정보 수정" class="mem_myInfo_Button"></a><br>
	
		<a href="myPw_modify_page"> <input type="button" value="비밀번호 변경" class="mem_myInfo_Button"></a><br>
	
		<a href="mDelete"> <input type="button" value="회원탈퇴" class="mem_myInfo_Button"></a><br>
	
		<a href="logout"> <input type="button" value="로그아웃" class="mem_myInfo_Button"></a>

	</form>		
</div>
</body>
</html>
