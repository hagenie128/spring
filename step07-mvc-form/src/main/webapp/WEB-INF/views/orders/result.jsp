<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 결과</title>
</head>
<body>
    <h1>주문 결과</h1>
    <p>고객명: ${orderForm.customerName}</p>
    <p>메뉴명: ${menu.name}</p>
    <p>카테고리: ${menu.category}</p>
    <p>수량: ${orderForm.quantity}</p>
    <p>요청사항: ${orderForm.requestMessage}</p>
    <p>합계: <fmt:formatNumber value="${totalPrice}" pattern="#,###" />원</p>
    <p><a href="${pageContext.request.contextPath}/menus">메뉴 목록으로</a></p>
</body>
</html>

