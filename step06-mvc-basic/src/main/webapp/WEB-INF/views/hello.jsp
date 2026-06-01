<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${greeting }</h1>
	<!-- <a href="/step06-mvc-basic/">홈으로 돌아가기</a> -->
	<a href="${pageContext.request.contextPath }/">홈으로 돌아가기</a>
</body>
</html>