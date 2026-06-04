<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메뉴 상세</title>
</head>
<body>
    <h1>메뉴 상세</h1>
    <p>번호: ${menu.id}</p>
    <p>메뉴명: ${menu.name}</p>
    <p>카테고리: ${menu.category}</p>
    <p>가격: ${menu.price}원</p>
    <p>
        <a href="${pageContext.request.contextPath}/orders/new?menuId=${menu.id}">이 메뉴 주문하기</a>
        <a href="${pageContext.request.contextPath}/menus">목록으로</a>
    </p>
</body>
</html>

