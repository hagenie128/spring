<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/kiosk/register" method="post">
		<input type="text" name="name"><br>
		<input type="text" name="price"><br>
		<button>추가</button>		
	</form>
</body>
</html>