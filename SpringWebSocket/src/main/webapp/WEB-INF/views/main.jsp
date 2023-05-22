<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="hidden" name="id" id="id" value="${login.id }">
<c:if test="${login.id == null }">
<button type="button" style ="color : #f8f8f8" class="from-control btn btn-warning"onclick="location.href='/loginform'">로그인 하러가기</button>
</c:if>
<c:if test="${login.id != null }">
<button type="button" style ="color : #f8f8f8" class="from-control btn btn-warning"onclick="location.href='/logout'">로그아웃</button>
</c:if>
<c:if test="${login.id != null }">
<button type="button" style ="color : #f8f8f8" class="from-control btn btn-warning"onclick="location.href='/chat'">채팅 서버 입장</button>
</c:if>

</body>
</html>