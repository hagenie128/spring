<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>업로드 결과</title></head>
<body>
  <h2>${message}</h2>
  <p>파일명: ${fileName}</p>
  <p>크기: ${fileSize} bytes</p>
  <p>타입: ${contentType}</p>
  <a href="/upload">다시 업로드</a>
</body>
</html>
