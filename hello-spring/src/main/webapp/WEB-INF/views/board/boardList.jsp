<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
tr[data-no]{
	cursor: pointer;
}
</style>
<script>
function goBoardForm(){
	location.href = "${pageContext.request.contextPath}/board/boardForm.do";
}

$(()=>{
	$("tr[data-no]").click(e => {
		//화살표 암수 안에서 this는 e.target이 아니다.
		// console.log(e.target); //td태그클릭 -> 부모tr로 이벤트 전파 (버블링)
		var $tr = $(e.target).parent();
		var no = $tr.data("no");
		location.href = "${pageContext.request.contextPath}/board/boardDetail.do?no=" + no;
	});

    // jQuery Autocomplete API docs : https://api.jqueryui.com/autocomplete/
    $( "#searchTitle" ).autocomplete({
        
    	source: function(request, response){
    		   $.ajax({
    			   url: "${pageContext.request.contextPath}/board/searchBoard.do",
    			   data: {
    				   keyword: request.term
    			   },
    			   success: function(data){
    				 // console.log(data.searchBoardList);
    				/* var arr = data.searchBoardList;
    				
    				  //jQuery버전 map()함수
    				  arr = $.map(arr, function(board){ // 함수의 인자값 : arr[i]의 값
       					 return {
   						  label : board.title, //화면에 노출할 텍스트
   						  value : board.no //내부적으로 처리할 값
   					 	 };
    					 
    				  }); */

    				  // ES6문법 사용 버전. 
    				  const {searchBoardList} = data;

    				  //배열, JS버전 map()함수
    				  const arr = 
    				  searchBoardList.map((board) => {
						console.log(board);
						return {
							//jQuery autocomplate 에서 필요한 속성. label, value 
								label : board.title,
								value : board.no
							}
	        			});
    				  
    				  //콜백함수 response()호출
    				  response(arr); 
    			   },
    			   error: function(xhr, status, err){
    				   console.log(xhr, status, err);
    			   }
    		   });
    	   }, 
    	   select: function(event, selected){
    		   //선택한 값 가져오기 
    		   console.log(event);
    		   console.log(selected); //selected함수에 item객체가 있고 그 안에 label속성이 있음

    		 location.href="${pageContext.request.contextPath}/board/boardDetail.do?no=" + selected.item.value;
    		   
    		  // $("h1.selected").html(selected.item.label);
    	   },
    	  minLength: 2 //2글자 이상 쳐야 검색됨
    });
})
</script>
<section id="board-container" class="container">
	<input type="search" placeholder="제목 검색..." id="searchTitle" class="form-control col-sm-3 d-inline" autofocus />
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success" onclick="goBoardForm();"/>
	<table id="tbl-board" class="table table-striped table-hover">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th> <!-- 첨부파일 있을 경우, /resources/images/file.png 표시 width: 16px-->
			<th>조회수</th>
		</tr>
<c:if test="${not empty boardList }">
	<c:forEach items="${boardList}" var="board">
		<tr data-no="${board.no}">
			<td>${board.no}</td>
			<td>${board.title}</td>
			<td>${board.memberId}</td>
			<td><fmt:formatDate value="${board.regDate}" pattern="yy/MM/dd HH:mm"/></td>
			<td>
			<c:if test="${board.hasAttachment}">
				<img src="${pageContext.request.contextPath }/resources/images/file.png" style="width: 20px; height: 20px;" alt="" />
			</c:if>
			</td>
			<td>${board.readCount}</td>
		</tr>
	</c:forEach>
</c:if>
	</table>
	
	<nav aria-label="Page navigation example">
  <ul class="pagination">
  <c:if test="${param.cPage > 5}">
    <li class="page-item"><a class="page-link" href="${pageBar.url}?cPage=${param.cPage-1}">Previous</a></li>
  </c:if>
  <c:if test="${not empty pageBar.pageNoList }">
   <c:forEach items="${pageBar.pageNoList}" var="pageNo">
  		<li class="page-item"><a class="page-link" href="${pageBar.url}?cPage=${pageNo}">${pageNo}</a></li>
  </c:forEach>
  </c:if>
 <c:if test="${pageBar.lastPageNo < pageBar.totalPage }">
 	<li class="page-item"><a class="page-link" href="${pageBar.url}?cPage=${pageBar.lastPageNo+1}">Next</a></li>
 </c:if>
  </ul>
</nav>
</section> 

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
