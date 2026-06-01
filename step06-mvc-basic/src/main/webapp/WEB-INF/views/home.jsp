<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${message }</h1>
	<p>서버시간 : ${serverTime }</p>
	<ul>
		<li><a href="${pageContext.request.contextPath }/hello">인사페이지</a></li>
		<li>
			<a href="${pageContext.request.contextPath }/hello/greet?name=홍길동">
				홍길동으로 인사
			</a>
		</li>
	</ul>
</body>
</html>




