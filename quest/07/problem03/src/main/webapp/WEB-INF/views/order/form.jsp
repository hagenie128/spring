<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DTO 주문 입력</title>
</head>
<body>
    <h1>DTO 주문 입력</h1>
    <form action="${pageContext.request.contextPath}/orders" method="post">
        <p>
            <label>고객명</label>
            <input type="text" name="customerName" required>
        </p>
        <p>
            <label>메뉴</label>
            <select name="menuName">
                <c:forEach var="menu" items="${menus}">
                    <option value="${menu}">${menu}</option>
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

