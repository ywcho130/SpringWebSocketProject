<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>웹소켓 채팅</title>

<!-- 라이브러리 등록  : CDN 방식 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div>
        <button type="button" class="btn btn-success" onclick="openSocket();">대화방 참여</button>
        <button type="button" class="btn btn-danger"onclick="closeSocket();">대화방 나가기</button>
    	<br/><br/><br/>
  		메세지 입력 : 
        <input type="text" id="sender" value="${login.id}" style="display: none;">
        <input type="text" id="messageInput">
        <button type="button" class="btn btn-info" onclick="send();">메세지 전송</button>
    </div>
    <!-- 메시지는 여기 담긴다. -->
    <div id="messages">
    </div>
    <!-- 웹소켓 자바스크립트 -->
    <script type="text/javascript">
        var ws;
        var messages = document.getElementById("messages");

        // 웹소켓 연결하는 함수, 이미 웹소켓이 연결되어있다면 메시지를 출력
        function openSocket(){
            if(ws !== undefined && ws.readyState !== WebSocket.CLOSED ){
                writeResponse("이미 웹소켓이 실행 중에 있습니다.");
                return;
            }
            //웹소켓 객체 만드는 코드
            ws = new WebSocket("ws://localhost:80/cho");

            // 서버로부터 받은 초기 데이터 처리
            ws.onopen = function(event){
                if(event.data === undefined){
              		return;
                }
                writeResponse(event.data);
            };
            // 수신한 메시지 출력
            ws.onmessage = function(event){
                console.log('writeResponse');
                console.log(event.data)
                writeResponse(event.data);
            };
            // 대화종료
            ws.onclose = function(event){
                writeResponse("대화 종료");
            }
            
        }
        // 입력한 메시지와 로그인 정보의 아이디를 서버로 전송하는 함수
        function send(){
            var text = document.getElementById("messageInput").value+","+document.getElementById("sender").value;
            ws.send(text);
            text = "";
        }
        // 웹소켓 연결을 닫는 함수
        function closeSocket(){
            ws.close();
        }
        // messages 요소에 메시지를 추가하는 함수
        function writeResponse(text){
            messages.innerHTML += "<br/>"+text;
        }
        
  </script>
</body>
</html>