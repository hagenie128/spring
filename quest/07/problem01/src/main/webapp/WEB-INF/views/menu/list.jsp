<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메뉴 목록</title>
</head>
<body>
    <h1>메뉴 목록</h1>
    <form action="${pageContext.request.contextPath}/menus/search" method="get">
        <input type="text" name="keyword" value="${keyword}" placeholder="검색어">
        <button type="submit">검색</button>
    </form>
    <table border="1">
        <tr>
            <th>번호</th>
            <th>메뉴명</th>
            <th>카테고리</th>
            <th>가격</th>
        </tr>
        <c:forEach var="menu" items="${menus}">
            <tr>
                <td>${menu.id}</td>
                <td><a href="${pageContext.request.contextPath}/menus/${menu.id}">${menu.name}</a></td>
                <td>${menu.category}</td>
                <td>${menu.price}원</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

