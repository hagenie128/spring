<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메뉴</title>
</head>
<body>
    <h1>메뉴판</h1>
    <table border="1">
        <tr><th>메뉴</th><th>가격</th></tr>
        <c:forEach var="item" items="${menuList}">
            <tr>
                <td><a href="${pageContext.request.contextPath }/kiosk/order?menu=${item.name}&qty=1">${item.name}</a></td>
                <td>${item.price}원</td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="${pageContext.request.contextPath }/kiosk/home">홈으로</a></p>
</body>
</html>
