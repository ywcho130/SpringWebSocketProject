<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<title>JoinKakaoForm</title>
</head>
<div class="container">
	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-success">
			<div class="panel-heading">
				<div class="panel-title">환영합니다!</div>
			</div>
			<div class="panel-body">
				<form id="join-form" method="post" action="/joinKakao">
					<div>
					<input name="id" type="hidden" value="${vo.id}">
                                        <input name="email" type="hidden" value="${vo.email}">
                                        <input name="name" type="hidden" value="${vo.name}">
                                        <label for="changePw">비밀번호(계정관리에 사용)</label>
                                            <input type = "password"class="form-control form-control-user"
                                                id="pw" name="pw"  
                                                placeholder="계정관리에 사용됩니다.">
					</div>
					<div>
						<button type="submit" class="form-control btn btn-primary">회원가입</button>

					</div>
				</form>
			</div>
		</div>
	</div>
</div>
    

</body>
</html>