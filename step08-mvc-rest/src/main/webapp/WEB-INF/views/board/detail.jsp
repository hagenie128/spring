<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>상세</title></head>
<body>
  <h2>${board.title}</h2>
  <p>작성자: ${board.author} | 날짜: ${board.createdAt}</p>
  <p>${board.content}</p>
  <form method="post" action="delete">
    <input type="hidden" name="id" value="${board.id}"/>
    <button type="submit" onclick="return confirm('삭제하시겠습니까?')">삭제</button>
  </form>
  <a href="list">목록으로</a>
</body>
</html>
