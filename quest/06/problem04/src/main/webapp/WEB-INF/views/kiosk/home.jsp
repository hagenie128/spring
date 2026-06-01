<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>키오스크</title>
</head>
<body>
    <h1>그린 키오스크</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath }/kiosk/menu">메뉴 보기</a></li>
        <li><a href="${pageContext.request.contextPath }/kiosk/order?menu=불고기버거&qty=1">주문하기</a></li>
        <li><a href="${pageContext.request.contextPath }/kiosk/register/view">메뉴 추가</a></li>
    </ul>
</body>
</html>