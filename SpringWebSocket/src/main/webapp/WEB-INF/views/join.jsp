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
<script type="text/javascript">

$(function() {
	$("#join").submit(function() {
		if ($("#pw").val() != $("#pw2").val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$("#pw, #pw2").val("");
			$("#pw").focus();
			return false;
		}
	});
});

</script>
<style>
body {
	background: #f8f8f8;
	padding: 60px 0;
}

#join-form>div {
	margin: 15px 0;
}
#join-form button {
	width: 100%; /* 버튼의 너비를 100%로 지정 */
}
</style>
<title>JoinForm</title>
</head>
<div class="container">
	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-success">
			<div class="panel-heading">
				<div class="panel-title">환영합니다!</div>
			</div>
			<div class="panel-body">
				<form id="join-form" method="post" action="/join">
					<div>
					<input type="text" class="form-control" name="id" placeholder="아이디 입력" autofocus>
					<input type="text" class="form-control" name="name" placeholder="이름 입력" autofocus>
					<input type="text" class="form-control" name="email" placeholder="이메일 입력" autofocus>
					<input type="password" class="form-control" name="pw" placeholder="비밀번호 입력" autofocus>
					<input type="password" class="form-control" name="pw2" placeholder="비밀번호 확인" autofocus>
					</div>
					<div>
						<button type="submit" class="form-control btn btn-primary">회원가입</button>

					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</html>