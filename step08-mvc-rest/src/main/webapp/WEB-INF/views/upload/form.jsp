<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>파일 업로드</title></head>
<body>
  <h2>파일 업로드</h2>
  <form method="post" action="${pageContext.request.contextPath}/upload/process" enctype="multipart/form-data">
    <input type="file" name="file" multiple/><br>
    <input type="file" name="file" multiple/><br>
    <input type="file" name="file" multiple/><br>
    <button type="submit">업로드</button>
  </form>
</body>
</html>
