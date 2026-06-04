<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>restAPI</title>
</head>
<body>
	<h1>Spring MVC</h1>
	<ul>
			<li><a href="${pageContext.request.contextPath}/api/products">상품API</a></li>
			<li><a href="${pageContext.request.contextPath}/api/products/1">1번 상품API</a></li>
			<li><a href="${pageContext.request.contextPath}/upload">파일 업로드</a></li>
			<li><a href="${pageContext.request.contextPath}/board/list">게시판</a></li>
	</ul>
</body>
</html>