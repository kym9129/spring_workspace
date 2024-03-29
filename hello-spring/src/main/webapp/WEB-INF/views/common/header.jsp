<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.title }</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- bootstrap js: jquery load 이후에 작성할것.-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<!-- bootstrap css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

<!-- 사용자작성 css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />
</head>
<body>
<div id="container">
	<header>
		<div id="header-container">
			<h2>${param.title }</h2>
		</div>

		<!-- https://getbootstrap.com/docs/4.0/components/navbar/ -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">
				<img src="${pageContext.request.contextPath}/resources/images/logo-spring.png" alt="스프링로고" width="50px" />
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
		  	</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav mr-auto">
			    	<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/board/boardList.do?cPage=1">게시판</a></li>
                    <!-- 데모메뉴 DropDown -->
                    <!--https://getbootstrap.com/docs/4.1/components/navbar/#supported-content-->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Demo
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/demo/devForm.do">Dev 등록</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/demo/devList.do">Dev 목록</a>
                        </div>
				    </li>
				    
				   <!-- Websocket -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Websocket
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/ws/websocket.do">Websocket</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/ws/sockjs.do">Sock.js</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/ws/stomp.do">Stomp</a>
                        </div>
				    </li>
				    
				     <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/memo/memo.do">Memo(AOP)</a></li>
				     <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/menu/menu.do">Menu(REST)</a></li>
			    </ul>
			    <c:if test="${loginMember == null }">
			    <%-- 로그인 이전 --%>
			     <button 
			    	class="btn btn-outline-success my-2 my-sm-0" 
			    	type="button" 
			    	onclick="location.href='${pageContext.request.contextPath}/member/memberLogin.do'">로그인</button>
                &nbsp;
                <button 
                	class="btn btn-outline-success my-2 my-sm-0" 
                	type="button"
                	onclick="location.href='${pageContext.request.contextPath}/member/memberEnroll.do'">회원가입</button>
			
			    </c:if>
			    <c:if test="${loginMember != null }">
			    <%-- 로그인 이후 --%>
			    <span><a href="${pageContext.request.contextPath}/member/memberDetail.do">${loginMember.name}</a>님, 안녕하세요.</span>
			    &nbsp;
			     <button 
			    	class="btn btn-outline-success my-2 my-sm-0" 
			    	type="button" 
			    	onclick="location.href='${pageContext.request.contextPath}/member/memberLogout.do'">로그아웃</button>
			    </c:if>
			  </div>
		</nav>
	</header>
	<c:if test="${not empty msg}">
	<div class="alert alert-warning alert-dismissible fade show" role="alert">
	  <strong>${msg}</strong>
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	</div>
	</c:if>
	<section id="content">