<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>키오스크 주문</title>
</head>
<body>
    <h1>키오스크 주문</h1>
    <form action="${pageContext.request.contextPath}/kiosk/orders" method="post">
        <p>
            <label>고객명</label>
            <input type="text" name="customerName" required>
        </p>
        <p>
            <label>메뉴</label>
            <select name="menuId">
                <c:forEach var="menu" items="${menus}">
                    <option value="${menu.id}" ${menu.id == selectedMenuId ? 'selected' : ''}>
                        ${menu.name} (${menu.price}원)
                    </option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>수량</label>
            <input type="number" name="quantity" value="1" min="1" required>
        </p>
        <p>
            <label>요청사항</label>
            <input type="text" name="requestMessage">
        </p>
        <button type="submit">주문하기</button>
    </form>
</body>
</html>

