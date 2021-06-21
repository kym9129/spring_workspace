﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="stomp" name="title"/>
</jsp:include>

<c:if test="${loginMember.id eq 'admin'}">
	    <div class="input-group mb-3">
    	<select id="stomp-url" class="form-select mr-1">
    		<option value="">전송url</option>
    		<option value="/admin/notice">/admin/notice</option>
    		<option value="/admin/notice/honggd">/admin/notice/honggd</option>
    	</select>
	    <input type="text" id="message" class="form-control" placeholder="Message">
	    <div class="input-group-append" style="padding: 0px;">
	        <button id="sendBtn" class="btn btn-outline-secondary" type="button">Send</button>
	    </div>
    </div>
      <button id="ajaxBtn" class="btn btn-outline-primary" type="button">비동기요청</button>
</c:if>
	

    <div>
        <ul class="list-group list-group-flush" id="data"></ul>
    </div>
 
 
<!-- sockjs-client js 추가 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js" integrity="sha512-3/5zbNJKTwZiPFIUPL9Q6woFGvOluvYq2/rJ+C4sZUTXKhVoY3e6mSTf5RJG01lYX3atqeslmWTsxCXb147x2w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<!-- stomp.js 추가 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js" integrity="sha512-tL4PIUsPy+Rks1go4kQG8M8/ItpRMvKnbBjQm4d2DQnFwgcBYRRN00QdyQnWSCwNMsoY/MfJY8nHp2CzlNdtZA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<!-- 구형브라우져에서 ES6 최신문법 사용하기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.js" integrity="sha512-pBSlhNUvB+td6sjW1zmR6L7c7kVWR4octUPl4tfHmzO63424nxta8aLmficEcAAswQmRqTiToi63AazDurj/Sg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-polyfill/7.12.1/polyfill.js" integrity="sha512-wixq/u8vbwoVM6yCmTHUNszWudaPpwf8pKxfG1NPUOBXTh1ntBx8sr/dJSbGTlZUqpcoPjaUmU1hlBB3oJlzFQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script type="text/babel">
$("#ajaxBtn").click(() => {

	$.ajax({
		url : "${pageContext.request.contextPath}/ws/someRequest.do",
		success(data){
			console.log(data);
		},
		error: console.log
	});
});

/**
 * sock.js
 * html5api websocket을 지원하지 않는 브라우져에서도 양방향 통신을 사용
 * 
 * - http로 최초연결시도후, websock이 사용가능한 경우 ws protocol로 upgrade.
 * - 구버젼 브라우져의 경우, xhr-stream/xhr-polling 중 적합한 방식으로 양방향 통신 사용.
 */
const ws = new SockJS(`http://\${location.host}${pageContext.request.contextPath}/stommmp`);
const stompClient = Stomp.over(ws);

const $data = $("#data");

//최초 연결
stompClient.connect({}, frame => {
	console.log("stomp connected : ", frame);

	//구독
	// /notice : 전체메세지
	// 메세지를 frame이라는 객체에 담아서 보낸다 (정해져있음)
	stompClient.subscribe("/notice", frame => {
		console.log("message from /notice : ", frame);
		displayMessage(frame);
	});
	// /notice/id : 특정 사용자에게만 메세지 보내기
	stompClient.subscribe("/notice/${loginMember.id}", message => {
		console.log("message from /notice/${loginMember.id} : ", message);
	});
});

//인자로 받은 frame객체의 body속성을 사용
const displayMessage = ({body}) => {
	
	//1. json -> js object
	let obj = JSON.parse(body);
	console.log(obj);

	// 2. 내용만 구조분해할당
	const {message} = obj; //obj객체에서 message만 꺼냈음
	let html = `<div class="alert alert-warning alert-dismissible fade show" role="alert">
	  <strong>\${message}</strong>
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	</div>`;

	// 3. #content prepend(자식요소로 맨앞에 추가하기)
	const $content = $("#content"); //header.jsp에 있는 section시작태그
	$content.prepend(html);

};



const sendMessage = () => {
	const url = $("#stomp-url").val();
	if(url === ""){
		alert("전송 url을 선택하세요.");
		return;
	}

	const $message = $("#message");

	//메세지의 정보를 객체로 담음. JSON으로 변경
	const msg = {
		from : "${loginMember.id}",
		to : url === "admin/notice" ? "all" : "honggd",
		message : $message.val(),
		type : "NOTICE",
		time : Date.now()
	};

	if($message.val()){
		// ws.send($message.val());
		stompClient.send(url, {}, JSON.stringify(msg)); //(url, option객체, 메세지객체);
		$message.val("").focus();
	}
};

$("#sendBtn").click(sendMessage);
$("#message").keyup(e => e.keyCode == 13 && sendMessage());



</script>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
