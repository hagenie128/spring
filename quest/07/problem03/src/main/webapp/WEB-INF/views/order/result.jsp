<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DTO 주문 결과</title>
</head>
<body>
    <h1>DTO 주문 결과</h1>
    <p>고객명: ${orderForm.customerName}</p>
    <p>메뉴명: ${orderForm.menuName}</p>
    <p>수량: ${orderForm.quantity}</p>
    <p>요청사항: ${orderForm.requestMessage}</p>
    <p>단가: ${unitPrice}원</p>
    <p>합계: ${totalPrice}원</p>
    <p><a href="${pageContext.request.contextPath}/orders/new">다시 주문하기</a></p>
</body>
</html>

