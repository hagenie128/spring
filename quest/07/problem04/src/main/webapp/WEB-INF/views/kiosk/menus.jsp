<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>키오스크 메뉴</title>
</head>
<body>
    <h1>키오스크 메뉴</h1>
    <form action="${pageContext.request.contextPath}/kiosk/menus/search" method="get">
        <input type="text" name="keyword" value="${keyword}" placeholder="메뉴명 또는 카테고리">
        <button type="submit">검색</button>
        <a href="${pageContext.request.contextPath}/kiosk/menus">전체 보기</a>
    </form>
    <table border="1">
        <tr>
            <th>번호</th>
            <th>메뉴명</th>
            <th>카테고리</th>
            <th>가격</th>
            <th>주문</th>
        </tr>
        <c:forEach var="menu" items="${menus}">
            <tr>
                <td>${menu.id}</td>
                <td><a href="${pageContext.request.contextPath}/kiosk/menus/${menu.id}">${menu.name}</a></td>
                <td>${menu.category}</td>
                <td>${menu.price}원</td>
                <td><a href="${pageContext.request.contextPath}/kiosk/orders/new?menuId=${menu.id}">주문하기</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

