<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 상세</title>
</head>
<body>
    <h1>상품 상세</h1>
    <p>상품명: ${productName}</p>
    <p>가격: ${price}원</p>
    <p><a href="${pageContext.request.contextPath }/list">목록으로 돌아가기</a></p>
</body>
</html>
