<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!--                                �̰��� ����Ʈ�� alert â�� ���� ���� jsp ���Դϴ�.                                -->
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<script>
		var msg="${message}";
		alert(msg);
		document.location.href="${url}";
	</script>
</body>
</html>