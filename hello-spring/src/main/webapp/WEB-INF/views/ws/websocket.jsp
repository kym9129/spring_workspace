<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Websocket" name="title"/>
</jsp:include>

    <div class="input-group mb-3">
    <input type="text" id="message" class="form-control" placeholder="Message">
    <div class="input-group-append" style="padding: 0px;">
        <button id="sendBtn" class="btn btn-outline-secondary" type="button">Send</button>
    </div>
    </div>
    <div>
        <ul class="list-group list-group-flush" id="data"></ul>
    </div>
    
<script>
//페이지 접속 시 서버로 websocket 연결을 시도
//WebSocket()객체는 표준 라이브러리라 바로 사용 가능
const ws = new WebSocket(`ws:\${location.host}/spring/websoooocket`);	
const $data = $("#data");
ws.onopen = e => {
	console.log("onopen : ", e);
	
};

ws.onmessage = e => {
	console.log("onmessage : ", e);
	const {data} = e; //객체 e의 data속성값을 data라는 변수에 담기
	$data.append("<li class='list-group-item'>" + data + "</li>"); //메세지 표
};

ws.onerror = e => {
	console.log("onerror : ", e);
};

ws.onclose = e => {
	console.log("onclose : ", e);
};



const sendMessage = () => {
	const $message = $("#message");	
	
	if($message.val()){ // 메세지 입력했을 때에만 전송 가능
		ws.send($message.val());
		$message.val(""); // 전송 후엔 입력창 초기화
	}
};

$("#sendBtn").click(sendMessage);
$("#message").keyup(e => e.keyCode == 13 && sendMessage()); //메세지 쓰다 엔터치면 전송

</script>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
