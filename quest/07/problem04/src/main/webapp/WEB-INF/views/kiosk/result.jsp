<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 결과</title>
</head>
<body>
    <h1>주문 결과</h1>
    <!-- TODO 14: 고객명, 메뉴명, 카테고리, 수량, 요청사항, 총 금액을 EL로 출력 -->
    <p>고객명: ${orderForm.customerName }</p>
    <p>메뉴명: ${menu.name }</p>
    <p>카테고리: ${menu.category }</p>
    <p>수량: ${orderForm.quantity }</p>
    <p>요청사항: ${orderForm.requestMessage}</p>
    <p>총 금액: ${totalPrice }원</p>
    <p><a href="${pageContext.request.contextPath}/kiosk/menus">메뉴 목록</a></p>
</body>
</html>

