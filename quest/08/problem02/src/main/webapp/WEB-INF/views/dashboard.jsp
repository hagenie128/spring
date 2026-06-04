<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>대시보드</title></head>
<body>
  <h2>관리자 대시보드</h2>
  <p>로그인 사용자: ${sessionScope.adminUser}</p>
  <a href="/logout">로그아웃</a>
</body>
</html>
