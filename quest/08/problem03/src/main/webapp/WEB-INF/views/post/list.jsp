<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>게시글 목록</title></head>
<body>
  <h2>게시글 목록</h2>
  <a href="write">글쓰기</a>
  <table border="1">
    <tr><th>번호</th><th>제목</th><th>작성자</th></tr>
    <c:forEach var="p" items="${posts}">
      <tr>
        <td>${p.id}</td>
        <td><a href="detail/${p.id}">${p.title}</a></td>
        <td>${p.author}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
