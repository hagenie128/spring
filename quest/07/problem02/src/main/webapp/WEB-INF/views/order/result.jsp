<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 결과</title>
</head>
<body>
    <h1>주문 결과</h1>
    <p>고객명: ${customerName}</p>
    <p>메뉴명: ${menuName}</p>
    <p>수량: ${quantity}</p>
    <p>합계: ${totalPrice}원</p>
    <p><a href="${pageContext.request.contextPath}/orders/new">다시 주문하기</a></p>
</body>
</html>

