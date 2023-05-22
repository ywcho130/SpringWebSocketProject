<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css"
	rel="stylesheet">
<!-- 카카오 로그인 -->
<script type="text/javascript"
	src="https://developers.kakao.com/sdk/js/kakao.min.js" charset="utf-8"></script>
<style>
.container {
    max-width: 450px; /* 원하는 최대 너비로 설정하세요 */
    margin: 0 auto; /* 가운데 정렬을 위해 자동으로 여백을 설정합니다 */
}
body {
	background: #f8f8f8;
	padding: 60px 0;
}

#login-form>div {
	margin: 15px 0;
}
.button-container {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: center;
    margin-top: 15px;
}

.button-container button,
.button-container img {
    width: 100%;
}
</style>
<title>LoginForm</title>
</head>
<div class="container">
	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-success">
			<div class="panel-heading">
				<div class="panel-title">환영합니다!</div>
			</div>
			<div class="panel-body">
				<form id="login-form" method="post" action="/login">
					<div>
						<input name="id" class="form-control form-control-user" id="id"
							placeholder="Userid" > 
							<input name="pw" class="form-control form-control-user" id="pw"
							placeholder="Userpassword" >
					</div>
					<div class="button-container">
						<button type="submit" class="form-control btn btn-primary">로그인</button>

						<a href="https://kauth.kakao.com/oauth/authorize?client_id=c332edbfee259eb19e2123ccf452a4ca&redirect_uri=http://localhost/kakaoLogin&response_type=code"><img height=40px src="/resources/images/kakao_login_medium_narrow.png"/></a>
						<button type="button" style ="color : #f8f8f8" class="from-control btn btn-warning"onclick="location.href='/joinform'">회원가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</html>