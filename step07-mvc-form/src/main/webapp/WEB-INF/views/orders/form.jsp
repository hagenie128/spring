<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 입력 폼</title>
</head>
<body>
	<h1>주문 입력</h1>
	
	<form action="${pageContext.request.contextPath }/orders" method="post">
		<p>
			<label for="customerName">고객명</label>
			<input type="text" id="customerName" name="customerName" required>
		</p>
		<p>
			<label>메뉴</label> <select name="menuId" id="menu">
				<!-- 메뉴 항목 출력
					메뉴명(3000원)
					폼으로 전달할 값은 menuId값	
					seletedMenuId 와 menuId가 같으면 해당 태그에 selected
  			    -->
				<c:forEach var="item" items="${menus }">
					<option value="${item.id }"
						${item.id == selectedMenuId ? 'selected' : ''}>
						${item.name }(${item.price }원)</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label>수량</label> <input type="number" name="quantity" value="1" min="1" required>
		</p>
		<p>
			<label>요청사항</label> <input type="text" name="requestMessage" placeholder="예: 양파 빼주세요">
		</p>
		<button type="submit">주문하기</button>
	</form>
	
</body>
</html>