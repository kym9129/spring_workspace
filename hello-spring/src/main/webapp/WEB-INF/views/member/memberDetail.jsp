<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.spring.member.model.vo.Member, java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String[] hobby = ((Member)session.getAttribute("loginMember")).getHobby();
	
	List<String> hobbyList = hobby != null ? Arrays.asList(hobby) : null;
	pageContext.setAttribute("hobbyList", hobbyList);

	
%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원정보" name="title"/>
</jsp:include>
<style>
div#update-container{width:400px; margin:0 auto; text-align:center;}
div#update-container input, div#update-container select {margin-bottom:10px;}
</style>
<div id="update-container">
	<form name="memberUpdateFrm" action="${pageContext.request.contextPath}/member/memberUpdate.do" method="post">
		<input type="text" class="form-control" 
			placeholder="아이디 (4글자이상)" value="${loginMember.id}" name="id" id="id" readonly required/>
		<input type="text" class="form-control" value="${loginMember.name}" placeholder="이름" name="name" id="name" required/>
		<input type="date" class="form-control" placeholder="생일" value="${loginMember.birthday }" name="birthday" id="birthday"/>
		<input type="email" class="form-control" placeholder="이메일" value="${loginMember.email}" name="email" id="email" required/>
		<input type="tel" class="form-control" placeholder="전화번호 (예:01012345678)" value="${loginMember.phone}" name="phone" id="phone" maxlength="11" required/>
		<input type="text" class="form-control" placeholder="주소" value="${loginMember.address }" name="address" id="address"/>
		<select class="form-control" name="gender" required>
		  <option value="" disabled selected>성별</option>
		  <option value="M" ${loginMember.gender eq 'M' ? 'selected' : ''} >남</option>
		  <option value="F" ${loginMember.gender eq 'F' ? 'selected' : ''} >여</option>
		</select>
		<div class="form-check-inline form-check">
			취미 : &nbsp; 
			<input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동" ${hobbyList.contains("운동") ? 'checked' : ''}>
			<label for="hobby0" class="form-check-label" >운동</label>&nbsp;
			<input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산" ${hobbyList.contains("등산") ? 'checked' : ''}>
			<label for="hobby1" class="form-check-label" >등산</label>&nbsp;
			<input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서" ${hobbyList.contains("독서") ? 'checked' : ''}>
			<label for="hobby2" class="form-check-label" >독서</label>&nbsp;
			<input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임" ${hobbyList.contains("게임") ? 'checked' : ''}>
			<label for="hobby3" class="form-check-label" >게임</label>&nbsp;
			<input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행" ${hobbyList.contains("여행") ? 'checked' : ''}>
			<label for="hobby4" class="form-check-label" >여행</label>&nbsp;
		</div>
		<br />
		<input type="submit" class="btn btn-outline-success" value="수정">&nbsp;
		<input type="reset" class="btn btn-outline-success" value="취소" onclick="location.href='${pageContext.request.contextPath}'">
		<br><br />
		<input type="button" class="btn btn-outline-danger" value="회원탈퇴" data-toggle="modal" data-target="#myModal">
	</form>
</div>
<form name="memberDelFrm"
	action="${pageContext.request.contextPath}/member/memberDelete.do"
	method="POST">
	<input type="hidden" name="id" value="${loginMember.id}" />
</form>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">회원 탈퇴</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        정말 탈퇴하시겠습니까?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
        <button type="button" class="btn btn-outline-danger" onclick="deleteMember();">회원탈퇴</button>
      </div>
    </div>
  </div>
</div>

<script>
function deleteMember(){
	$(document.memberDelFrm).submit();
}
</script>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
