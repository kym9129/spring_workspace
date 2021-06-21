<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Menu RestAPI" name="title"/>
</jsp:include>

<style>
div.menu-test{width:50%; margin:0 auto; text-align:center;}
div.result{width:70%; margin:0 auto;}
</style>
<script>
const menuRestOrigin = "http://localhost:10000";
const menuRestContextPath = "/springboot";
const url = menuRestOrigin + menuRestContextPath;

</script>

<div id="menu-container" class="text-center">
  <!-- 1.GET /menus-->
        <div class="menu-test">
            <h4>전체메뉴조회(GET)</h4>
            <input type="button" class="btn btn-block btn-outline-success btn-send" id="btn-menus" value="전송" />
        </div>
        <div class="result" id="menus-result"></div>
        <script>
		/*
		SOP (Same Origin Policy 동일근원정책)
		(origin이 동일해야 한다는 뜻. 글자수 하나라도 틀리면 동일이 아님)
		- origin : protocol + host + port (ex. http://localhost:10000)
		- 비동기 요청 시 현재페이지의 origin과 동일 origin으로만 요청할 수 있게 제한함.

		http://localhost:9090 ---X---> http://localhost:10000

		CORS Policy (Cross Origin Resource Sharing)
		- 조건: 응답header에 [Access-Control-Allow-Origin : 나의 origin] 값을 담아야 한다.
		
		
		Access to XMLHttpRequest at 'http://localhost:10000/springboot/menus' 
		from origin 'http://localhost:9090' 
		has been blocked by CORS policy: 
			No 'Access-Control-Allow-Origin' header is present on the requested resource.

		Access-Control-Allow-Origin값이 요청 헤더에 없다
			
		XMLHttpRequest : 비동기 처리하는 객체

		
		*/


        
		$("#btn-menus").click(() => {
			$.ajax({
				//url: `\${url}/menus`, //타 REST server로 직접 요청
				url: "${pageContext.request.contextPath}/menu/selectMenuList.do", //우리 서버로 요청
				method: "GET",
				success(data){ //객체 안의 메소스만 이렇게 줄여서 작성 가능 : 원래는 success: function(data){}
					console.log(data);
					displayResultTable("menus-result", data);
				},
				error: console.log //error: (x, y) => console.log(x, y) 서버가 주는 인자를 전부 다 찍어줌
			})
		});
        </script>
        
         
	<div class="menu-test">
		<h4>추천메뉴(GET)</h4>
		<form id="menuRecommendationFrm">
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="type" id="get-no-type" value="all" checked>
				<label for="get-no-type" class="form-check-label">모두</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="get-kr" value="kr">
				<label for="get-kr" class="form-check-label">한식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="get-ch" value="ch">
				<label for="get-ch" class="form-check-label">중식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="get-jp" value="jp">
				<label for="get-jp" class="form-check-label">일식</label>&nbsp;
			</div>
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="taste" id="get-no-taste" value="all" checked>
				<label for="get-no-taste" class="form-check-label">모두</label>&nbsp;
				<input type="radio" class="form-check-input" name="taste" id="get-hot" value="hot">
				<label for="get-hot" class="form-check-label">매운맛</label>&nbsp;
				<input type="radio" class="form-check-input" name="taste" id="get-mild" value="mild">
				<label for="get-mild" class="form-check-label">순한맛</label>
			</div>
			<br />
			<input type="submit" class="btn btn-block btn-outline-success btn-send" value="전송" >
		</form>
	</div>
	<div class="result" id="menuRecommendation-result">
	<script>
		$("#menuRecommendationFrm").submit(e => {
			// 2. GET : url를 /menus/kr  /menus/ch /menus/jp 로 처리하기
			
			// 폼제출 방지
			e.preventDefault();

			// 현재 폼
			const $frm = $(e.target);
			const type = $frm.find("[name=type]:checked").val();
			const taste = $frm.find("[name=taste]:checked").val();
			console.log(type, taste);

			$.ajax({
				url: `\${url}/menus/\${type}/\${taste}`,
				success(data){
					console.log(data);
					displayResultTable("menuRecommendation-result", data);
				},
				error: console.log
			});
		});
	</script>
	
	
	    <!-- 2.POST /menu -->
	<div class="menu-test">
		<h4>메뉴 등록하기(POST)</h4>
		<form id="menuEnrollFrm">
			<input type="text" name="restaurant" placeholder="음식점" class="form-control" />
			<br />
			<input type="text" name="name" placeholder="메뉴" class="form-control" />
			<br />
			<input type="number" name="price" placeholder="가격" class="form-control" />
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="type" id="post-kr" value="kr" checked>
				<label for="post-kr" class="form-check-label">한식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="post-ch" value="ch">
				<label for="post-ch" class="form-check-label">중식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="post-jp" value="jp">
				<label for="post-jp" class="form-check-label">일식</label>&nbsp;
			</div>
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="taste" id="post-hot" value="hot" checked>
				<label for="post-hot" class="form-check-label">매운맛</label>&nbsp;
				<input type="radio" class="form-check-input" name="taste" id="post-mild" value="mild">
				<label for="post-mild" class="form-check-label">순한맛</label>
			</div>
			<br />
			<input type="submit" class="btn btn-block btn-outline-success btn-send" value="등록" >
		</form>
	</div>
	<script>
	/**
	 * POST  /menu
	 */
	 $("#menuEnrollFrm").submit(e => {
		e.preventDefault();

		const $frm = $(e.target);
		const restaurant = $frm.find("[name=restaurant]").val();
		const name = $frm.find("[name=name]").val();
		const price = Number($frm.find("[name=price]").val()); //number로 형변환
		const type = $frm.find("[name=type]:checked").val();
		const taste = $frm.find("[name=taste]:checked").val();

		const menu = {
				restaurant, //속성명과 값이 같으면 값 생략 가능
				name,
				price,
				type,
				taste
			};

		console.log(restaurant, name, price, type, taste);

		$.ajax({
			url: `\${url}/menu`,
			//data: `restaurant=\${restaurant}&name=\${name}&price=\${price}&type=\${type}&taste=\${taste}`,
			data: JSON.stringify(menu), //객체를 JSON으로 바꿔서 서버에 전달
			contentType: "application/json; charset=utf-8", //json으로 보낼땐 contentType 꼭 적기
			method: "POST",
			success(data){
				console.log(data);
			},
			error: console.log,
			complete(){
				e.target.reset(); //폼 초기화 
			}
		});
		
	});
	</script>	
	
	<!-- #3.PUT /menu/123 -->
	<div class="menu-test">
		<h4>메뉴 수정하기(PUT)</h4>
		<p>메뉴번호를 사용해 해당메뉴정보를 수정함.</p>
		<form id="menuSearchFrm">
			<input type="text" name="id" placeholder="메뉴번호" class="form-control" /><br />
			<input type="submit" class="btn btn-block btn-outline-primary btn-send" value="검색" >
		</form>
		<hr />
		<form id="menuUpdateFrm">
			<input type="hidden" name="id" value="" />
			<input type="text" name="restaurant" placeholder="음식점" class="form-control" />
			<br />
			<input type="text" name="name" placeholder="메뉴" class="form-control" />
			<br />
			<input type="number" name="price" placeholder="가격" step="1000" class="form-control" />
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="type" id="put-kr" value="kr" checked>
				<label for="put-kr" class="form-check-label">한식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="put-ch" value="ch">
				<label for="put-ch" class="form-check-label">중식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="put-jp" value="jp">
				<label for="put-jp" class="form-check-label">일식</label>&nbsp;
			</div>
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="taste" id="put-hot" value="hot" checked>
				<label for="put-hot" class="form-check-label">매운맛</label>&nbsp;
				<input type="radio" class="form-check-input" name="taste" id="put-mild" value="mild">
				<label for="put-mild" class="form-check-label">순한맛</label>
			</div>
			<br />
			<input type="submit" class="btn btn-block btn-outline-success btn-send" value="수정" >
		</form>
	</div>
	<script>
		$("#menuSearchFrm").submit(e => {
			e.preventDefault();

			const id = Number($("[name=id]").val());
			/*
			e.target하위의 선택자를 조회 (컨텍스트)
			$("[name=id]", e.target).val()
			*/
			if(!id) return;
			console.log(id);

			$.ajax({
				url: `\${url}/menu/\${id}`,
				success(data){
					console.log(data);

					if(data){
						const $frm = $("#menuUpdateFrm");
						const {id, restaurant, name, price, type, taste} = data; //구조분해할당. ES6문법
						
						$frm.find("[name=id]").val(id);
						$frm.find("[name=restaurant]").val(restaurant);
						$frm.find("[name=name]").val(name);
						$frm.find("[name=price]").val(price);

						//name과 value가 같으면 checked속성 true
						$frm.find(`[name=type][value=\${type}]`).prop("checked", true);
						$frm.find(`[name=taste][value=\${taste}]`).prop("checked", true);
						
					}
					/* else{
						alrt("해당 메뉴가 존재하지 않습니다.");
						$("[name=id]", e.target).select(); // 사용자가 다시 입력할 수 있도록
					} */
				},
				error(xhr, statusText, err){
				//status code를 이용해서 에러처리
					console.log(xhr, statusText, err);
					const {status} = xhr;
					status == 404 && alrt("해당 메뉴가 존재하지 않습니다.");
					$("[name=id]", e.target).select(); // 사용자가 다시 입력할 수 있도록
				}
			});
				
		});

		$("#menuUpdateFrm").submit(e => {
			e.preventDefault();

			/* const id = Number($("[name=id]").val());
			const $frm = $(e.target);
			const restaurant = $frm.find("[name=restaurant]").val();
			const name = $frm.find("[name=name]").val();
			const price = Number($frm.find("[name=price]").val());
			const type = $frm.find("[name=type]:checked").val();
			const taste = $frm.find("[name=taste]:checked").val();

			const menu = {
						id,
						restaurant,
						name,
						price,
						type,
						taste
					}; */

			//formData를 활용해서 객체 만들기
			const frmData = new FormData(e.target);
			const menu = {};
			frmData.forEach((value, key) =>{
			//frmData에 key value가 있지만 숨어있어서 JSON처리가 안됨. 그래서 menu 객체에 옮겨담는다.
				menu[key] = value;
			})

			console.log(menu);

			$.ajax({
				//id를 menu객체에서 꺼내욤
				url: `\${url}/menu/\${menu.id}`,
				data: JSON.stringify(menu),
				contentType: "application/json; charset=utf-8",
				method: "PUT",
				success(data){
					console.log(data);
					const {msg} = data; //data객체에서 msg속성 꺼내기
				},
				error: console.log,
				complete(){
					$("#menuSearchFrm")[0].reset();
					$("#menuUpdateFrm")[0].reset();
					/*
					reset()메소드는 Vanila JS메소드임
					JS메소드를 사용하기 위해 jQuery객체의 [0]번지인 js체에 대해 reset을 사용
					
					$("#menuSearchFrm") : jQuery객체
					$("#menuSearchFrm"[0]) : JS객체
					*/
					//
				}
			
			});
		});
	</script>
	
	<!-- 4. 삭제 DELETE /menu/123 -->    
	<div class="menu-test">
    	<h4>메뉴 삭제하기(DELETE)</h4>
    	<p>메뉴번호를 사용해 해당메뉴정보를 삭제함.</p>
    	<form id="menuDeleteFrm">
    		<input type="text" name="id" placeholder="메뉴번호" class="form-control" /><br />
    		<input type="submit" class="btn btn-block btn-outline-danger btn-send" value="삭제" >
    	</form>
    </div>
    <script>
	$("#menuDeleteFrm").submit(e => {
		e.preventDefault();

		const id = $("[name=id]", e.target).val();

		if(!id) return; //id값이 없다면 조기리턴

		$.ajax({
			url: `\${url}/menu/\${id}`,
			method: "DELETE",
			success(data){
				console.log(data);
			},
			error(xhr, statusText, err){
				//status code로 에러처리
				console.log(xhr, statusText, err);
				const {status} = xhr;
				switch(status){
				case 404: alert("존재하지 않는 메뉴번호입니다");
				case 200: alert("")
				}

				
				$("[name=id]", e.target).select();
			},
			complete(){
				$(e.target)[0].reset();
			}
		});
	})
    </script>
	
	
	</div>
</div>

<script>
function displayResultTable(id, data){
	const $container = $("#" + id);
	let html = "<table class='table'>";
	html += "<tr><th>번호</th><th>음식점</th><th>메뉴</th><th>가격</th><th>타입</th><th>맛</th></tr>";

	//mybatis session.selectList()는 데이터가 없는 경우, 비어있는 list를 리턴.
	if(data.length > 0){
		$(data).each((i, menu) =>{
			const {id, restaurant, name, price, type, taste} = menu;
			html += `
				<tr>
					<td>\${id}</td>
					<td>\${restaurant}</td>
					<td>\${name}</td>
					<td>\${price}</td>
					<td>\${type}</td>
					<td><span class="badge badge-\${taste == 'hot' ? 'danger' : 'warning'}">\${taste}</span></td>
				</tr>`;
		});
	}
	else{
		html +="<tr><td conspan='6'>검색된 결과가 없습니다.</td></tr>";
	}
	html += "</table>";
	$container.html(html);
	
}
</script>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
