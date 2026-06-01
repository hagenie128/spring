<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- [완성된 파일 — 수정 불필요] -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 목록</title>
</head>
<body>
    <h1>상품 목록</h1>
    <ul>
        <c:forEach var="product" items="${products}">
            <li>
                ${product}
                (<a href="${pageContext.request.contextPath }/product/detail?name=${product}">상세보기</a>)
            </li>
        </c:forEach>
    </ul>
</body>
</html>
