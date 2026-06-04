<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>로그인</title></head>
<body>
  <h2>로그인</h2>
  <form method="post" action="${pageContext.request.contextPath }/login">
    <input type="text" name="username" placeholder="아이디"/>
    <button type="submit">로그인</button>
  </form>
</body>
</html>
