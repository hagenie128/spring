<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>게시판</title></head>
<body>
  <h2>게시판 목록</h2>
  <a href="write">글쓰기</a> | <a href="${pageContext.request.contextPath }/logout">로그아웃</a>
  <table border="1">
    <tr><th>번호</th><th>제목</th><th>작성자</th><th>날짜</th></tr>
    <c:forEach var="b" items="${boards}">
      <tr>
        <td>${b.id}</td>
        <td><a href="detail?id=${b.id}">${b.title}</a></td>
        <td>${b.author}</td>
        <td>${b.createdAt}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
