<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>메뉴 목록</h1>
	<!-- 매뉴명 검색 폼 -->
	<form action="${pageContext.request.contextPath }/menus/search" method="get">
		<input type="text" name="keyword" value="${keyword }" placeholder="메뉴명 또는 카테고리를 입력하세요">
		<button>검색</button>
		<a href="${pageContext.request.contextPath }/menus">전체보기</a>
	</form>
	<!-- 메뉴 출력 표 -->
	<c:choose>
		<c:when test="${empty menus }">
			<p>검색 결과가 없습니다.
			<p>
		</c:when>
	<c:otherwise>
		<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>메뉴명</th>
				<th>카테고리</th>
				<th>가격</th>
				<th>주문</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${menus }" var="menu">
				<tr>
					<td>${menu.id }</td>
					<td>${menu.name }</td>
					<td>${menu.category }</td>
					<td>${menu.price }</td>
					<td><a href="${pageContext.request.contextPath }/menus/${menu.id}">주문</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
	</c:choose>
</body>
</html>