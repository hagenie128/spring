<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>메뉴 관리</title>
</head>
<body>
  <h2>메뉴 관리 화면</h2>
  <p>API 엔드포인트 목록:</p>
  <ul>
    <li><a href="/api/menus">GET /api/menus — 전체 메뉴</a></li>
    <li><a href="/api/menus/available">GET /api/menus/available — 판매 가능 메뉴</a></li>
  </ul>
  <p>POST /api/menus 로 새 메뉴를 JSON으로 전송하여 추가할 수 있습니다.</p>
</body>
</html>
