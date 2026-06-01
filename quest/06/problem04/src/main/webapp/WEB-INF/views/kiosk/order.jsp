<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문</title>
</head>
<body>
    <h1>주문 완료</h1>
    <p>메뉴: ${orderMenu}</p>
    <p>수량: ${orderQty}개</p>
    <p>합계: ${totalPrice}원</p>
    <p><a href="${pageContext.request.contextPath }/kiosk/home">홈으로</a></p>
</body>
</html>
