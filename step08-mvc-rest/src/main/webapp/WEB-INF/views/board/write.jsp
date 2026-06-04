<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>글쓰기</title></head>
<body>
  <h2>글쓰기</h2>
  <form method="post" action="write">
    제목: <input type="text" name="title"/><br/>
    내용: <textarea name="content"></textarea><br/>
    작성자: <input type="text" name="author"/><br/>
    <button type="submit">등록</button>
  </form>
  <a href="list">목록으로</a>
</body>
</html>
