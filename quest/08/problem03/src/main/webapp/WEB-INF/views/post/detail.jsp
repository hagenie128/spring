<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>상세</title></head>
<body>
  <h2>${post.title}</h2>
  <p>작성자: ${post.author}</p>
  <p>${post.content}</p>
  <form method="post" action="${pageContext.request.contextPath }/post/delete">
    <input type="hidden" name="id" value="${post.id}"/>
    <button type="submit" onclick="return confirm('삭제하시겠습니까?')">삭제</button>
  </form>
  <a href="list">목록으로</a>
</body>
</html>
