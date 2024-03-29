<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.spring.demo.model.vo.Dev"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String[] lang = ((Dev)request.getAttribute("dev")).getLang();
	//무조건 체크 아닐 경우 null처리도 
	List<String> langList = Arrays.asList(lang);
	pageContext.setAttribute("langList", langList);
%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Demo" name="title"/>
</jsp:include>
<style>
div#demo-container{
	width:550px;
	padding:15px;
	border:1px solid lightgray;
	border-radius: 10px;
}
</style>
<div id="demo-container" class="mx-auto">
	<!-- https://getbootstrap.com/docs/4.1/components/forms/#readonly-plain-text -->
	<form name="devFrm"
		  method="post" 
		  action="${pageContext.request.contextPath}/demo/updateDev.do">
		<div class="form-group row">
		  <label for="name" class="col-sm-2 col-form-label">이름</label>
		  <div class="col-sm-10">
		    <input type="text" class="form-control" id="name" name="name" value="${dev.name}" required>
		  </div>
		</div>
		<div class="form-group row">
		  <label for="career" class="col-sm-2 col-form-label">개발경력</label>
		  <div class="col-sm-10">
		    <input type="number" class="form-control" id="career" name="career" value="${dev.career}" required>
		  </div>
		</div>
		<div class="form-group row">
		  <label for="email" class="col-sm-2 col-form-label">이메일</label>
		  <div class="col-sm-10">
		    <input type="email" class="form-control" id="email" name="email" value="${dev.email}" required>
		  </div>
		</div>
	  	<!-- https://getbootstrap.com/docs/4.1/components/forms/#inline -->
	    <div class="form-group row">
	    	<label class="col-sm-2 col-form-label">성별</label>
	    	<div class="col-sm-10">
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="gender"
				  		 id="gender0" value="M" ${dev.gender eq 'M'? 'checked' : ''}>
				  <label class="form-check-label" for="gender0">남</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="gender" 				  		 
				  id="gender1" value="F" ${dev.gender eq 'F'? 'checked' : ''}>
				  <label class="form-check-label" for="gender1">여</label>
				</div>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 col-form-label">개발언어</label>
			<div class="col-sm-10">
				<div class="form-check form-check-inline"> <!-- checked추가 -->
				  <input class="form-check-input" type="checkbox" name="lang" id="Java" value="Java" ${langList.contains("Java") ? 'checked' : '' }>
				  <label class="form-check-label" for="Java">Java</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="checkbox" name="lang" id="C" value="C" ${langList.contains("C") ? 'checked' : '' }>
				  <label class="form-check-label" for="C">C</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="checkbox" name="lang" id="Javascript" value="Javascript" ${langList.contains("Javascript") ? 'checked' : '' }>
				  <label class="form-check-label" for="Javascript">Javascript</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="checkbox" name="lang" id="Python" value="Python" ${langList.contains("Python") ? 'checked' : '' }>
				  <label class="form-check-label" for="Python">Python</label>
				</div>
			</div>
		</div>
		<!-- 중요 - 수정시 반드시 고유번호도 함께 넘겨주어야 함 -->
	  	<input type="hidden" name="no" value="${dev.no}" />
	  	<button type="submit" class="list-group-item list-group-item-action">dev 수정</button>
	</form>
</div>

<script>
/**
 * 수정폼 유효성검사
 * 
 */
$(document.devFrm).submit((e) => {
	//1. 이름은 한글 2글자 이상이어야 한다.
	var $name = $("#name");
	if(!/^[가-힣]{2,}$/.test($name.val())){
		alert("올바른 한글 이름을 입력해주세요.");
		e.preventDefault();
	}
	
	//2. 개발언어는 하나이상 선택해야 한다.
	var $lang = $("[name=lang]:checked");
	if($lang.length == 0){
		alert("하나 이상의 개발 언어를 선택하세요.");
		e.preventDefault();
	}
	
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
