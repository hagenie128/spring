<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메뉴 정보</title>
</head>
<body>
    <h1>메뉴 상세</h1>
    <p>메뉴명: ${menuName}</p>
    <p>가격: ${price}원</p>
    <p>카테고리: ${category}</p>
    <p><a href="./menu/order?item=${menuName}&qty=1">주문하기</a></p>
</body>
</html>
