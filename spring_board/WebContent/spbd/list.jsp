<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Integer total = null;
	total = (Integer) session.getAttribute("total");
	int ctotal = 0;
	//out.print("전체 레코드 수:"+total);
	//쿠키에 저장된 값을 불러오기
	Cookie[] cs = request.getCookies();
	for (int i = 0; i < cs.length; i++) {
		String cName = cs[i].getName();
		//out.print("쿠키이름:"+cName);
		if ("ctotal".equals(cName)) {
			ctotal = Integer.parseInt(cs[i].getValue());
		}
	}
	out.print("전체 레코드 수[쿠키로]:" + ctotal);
%>
<!DOCTYPE html>
<html>
<!-- 
	easyUI를 쓰는데, 게시판을 조회하는데 list.jsp를 요청하였다.
	getBoardList.spbd를 하는 대신... 왜?
	
	이유는, easyUI의 datagrid의 url속성으로 스프링을 경유하게 하였다.
	(url:getBoardList.spbd)
	
	UISolution 중 UI를 지원하는 컴포넌트 중, 아래 내용을 관심있게 보길 바란다.
	datagrid ( 테이블과 json을 지원하는 UI )
	dialog ( 팝업 )
	panel ( 패널 )
	combo ( DB연동을 지원하는 UI ) : select은 DB연동지원하지 않음
	
	하나더,
	List나 Map을 json포맷으로, 혹은 xml포맷으로 처리할 수 있어야 한다.
	
	list.jsp -> datagrid(url:getBoardList.spbd) -> 스프링경유 -> jsonBoardList.jsp(json포맷변환파일)
 -->
<head>
<meta charset="UTF-8">
<title>글목록 [ Webcontent/springboard/list.jsp : redirect|forward ]</title>
<%@ include file="../include/commonUI.jsp"%>
<script type="text/javascript">
	var g_total = 0;
	var g_no = 0;//클릭할때 매번 반영될 셀 위치정보를 담을 전역변수...
	//댓글쓰기 할 때
	function repleForm(pb_no, pb_group, pb_pos, pb_step) {
		alert("repleForm호출 성공" + pb_no + "," + pb_group + "," + pb_pos + "," + pb_step);
		$("#b_no").val(pb_no);
		$("#b_group").val(pb_group);
		$("#b_pos").val(pb_pos);
		$("#b_step").val(pb_step);
		$("#dl_read").dialog('close');
		$("#dl_boardInsert").dialog('open');
	}
	//글 수정할 때 - DB를 태워야 함.
	function updateForm(pb_no) {
		//alert("updateForm호출 성공");
		$("#dl_read").dialog('close');
		$("#dl_update").dialog({
			href : './mvcBoard.spbd?gubun=getBoardList&one=update&b_no=' + pb_no,
		});
		$("#dl_update").dialog('open');
	}
	//글수정  처리하기
	function updateAction() {
		$("#f_update").attr("method", "post");
		$("#f_update").attr("action", "./mvcBoard.spbd");
		$("#f_update").submit(); //이 때 서버로 전송이 일어남
	}
	//글삭제할 때
	function deleteForm() {
		alert("deleteForm호출 성공");
	}
	//글상세보기에서 글목록 돌아갈때
	function dg_listReload() {
		//alert("dg_listReload호출 성공");
		$("#dl_read").dialog('close');
		$("#dg_list").datagrid({
			url : './mvcBoard.spbd?gubun=getBoardList'
		});
	}
	//조건검색
	function bSearch() {
		//지금의 경우 이미 DOM구성이 완료되었을 때 액션을 이미 탄 상태가 되고 그 결과는 jsonBoardList.jsp로 응답이 나온다.
		/* 		$("#f_search").attr("method","get");
				$("#f_search").attr("action","./mvcBoard.bd?gubun=getBoardList");
				$("#f_search").submit(); */
		$("#dg_list").datagrid({
			url : './getBoardList.spbd?cb_type=' + $("#cb_type").val()
				+ '&sb_keyword=' + $("#sb_keyword").val()
		});
	}
	//글쓰기 화면 띄우기
	function boardInsForm() {
		$("#dl_boardInsert").dialog('open');
	}
	//글쓰기 등록 처리하기
	function boardInsert() {
		alert("gubun:" + $("#gubun").val());
		$("#f_insert").attr("method", "post");
		$("#f_insert").attr("action", "./boardInsert.spbd");
		$("#f_insert").submit(); //이 때 서버로 전송이 일어남
		alert("글쓰기 등록 요청했습니다.");
		console.log("글쓰기 등록 요청했습니다.");
	}
	//페이지 네이션에서 페이지 이동 버튼을 클릭했을 때 실제 페이지 이동처리 구현
	function pageMove(pageNumber, pageSize) {
		$("#dg_list").datagrid({
			url : './getBoardList.spbd?page=' + pageNumber + '&pageSize=' + pageSize
		});
	}
</script>
</head>
<body>

	<script type="text/javascript">
	$(document).ready(function(){
		$("#dg_list").datagrid({
		    url:'./getBoardList.spbd?page=1&pageSize=5',
		    
/* 		    columns:[[//VO로 받을때
		        {field:'b_no',title:'글번호',width:100, align:'center'},
		        {field:'b_title',title:'제목',width:400, align:'center'},
		        {field:'b_name',title:'이름',width:100, align:'center'},
		        {field:'b_file',title:'첨부파일',width:150, align:'center'},
		        {field:'b_hit',title:'조회수',width:100, align:'center'}
		    ]], */
 		    columns:[[//Map으로 받았을때
		        {field:'B_NO',title:'글번호',width:50, align:'center'},
		        {field:'B_TITLE',title:'제목',width:300, align:'center'},
		        {field:'B_NAME',title:'이름',width:70, align:'center'},
		        {field:'B_FILE',title:'첨부파일',width:350, align:'center'},
		        {field:'B_SIZE',title:'크기',width:80, align:'center'},
		        {field:'B_HIT',title:'조회수',width:50, align:'center'}
		    ]],
		  	//하나만 클릭가능
		    singleSelect:true,
		    
		    
		/* 데이터 조회가 완료되었을 때 */
			//상세보기 처리
			
			//클릭했을때 셀 값 읽어오기
		    onSelect: function(index,row){
		    	var row = $("#dg_list").datagrid("getSelected");
				g_no = row.B_NO;
			},

			//더블클릭에 대한 액션 정의하기
			onDblClickCell: function(index,field,value){
				//타이틀을 클릭했을 때
				if("B_TITLE"==field){
					$("#dl_read").dialog({
			    		href:'/getBoardDetail.spbd?b_no='+g_no,
			    	    onLoad:function(){
			    	    	//alert("success!!!");
			    	    }
			        });
					$("#dl_read").dialog('open');
					g_no = 0;//성공하고서 다이얼로그창을 띄우고 난 뒤 다시 전역변수를 0으로 초기화
				}
				
				//파일을 클릭했을 때
				if("B_FILE"==field){
					location.href='./downLoad.jsp?b_file='+value;
				}
			}
		});
		
		
		$('#pg_board').pagination({
		    total:<%=ctotal%>
		   ,pageSize:5
		   ,pageList: [5,10,15,20]
		   ,onSelectPage:function(pageNumber, pageSize){//pageNumber:현재 내가 바라보는 페이지, pageSize:한페이지에 뿌릴 로우수
			   //alert("pageNumber:"+pageNumber+"\n pageSize:"+ pageSize);
			   pageMove(pageNumber, pageSize);
		   }
		});
	});
</script>
	<table align="center" width="900px">
		<!-- 검색 조건 화면 추가하기 -->
		<form id="f_search">
			<tr>
				<td><select id="cb_type" class="easyui-combobox" name="cb_type" style="width: 200px;">
						<option value="b_title">제목</option>
						<option value="b_content">내용</option>
						<option value="b_name">작성자</option>
				</select> <input id="sb_keyword" name="sb_keyword" class="easyui-searchbox" style="width: 300px" data-options="searcher:bSearch,prompt:'Please Input Value'"></input></td>
			</tr>
		</form>
		<!-- 글쓰기 버튼 추가 -->
		<tr>
			<td align="left"><a id="btn_write" href="javascript:boardInsForm()" class="easyui-linkbutton">글쓰기</a></td>
		</tr>
		<!-- 목록처리 화면 추가하기(datagrid) -->
		<tr>
			<td>
				<table id="dg_list"></table>
			</td>
		</tr>
		<!-- 페이지 네이션 처리(pagination) -->
		<tr>
			<td>
				<div id="pg_board" style="background: #efefef; border: 1px solid #ccc;"></div>
			</td>
		</tr>
	</table>
	<!-- 글상세 보기 다이얼로그 처리 시작[read.jsp] -->
	<div id="dl_read" class="easyui-dialog" title="글상세보기" style="width: 600px; height: 350px;" data-options="resizable:true,modal:true,closed:true"></div>
	<!-- 글상세 보기 다이얼로그 처리  끝   -->
	<!-- 
글쓰기 다이얼 로그 처리(writeForm.jsp화면 처리)
처음엔 닫힌 상태로 있다가 글쓰기 버튼을 클릭하면 창을 띄움.
 -->
	<div id="dl_boardInsert" class="easyui-dialog" title="글쓰기" style="width: 600px; height: 350px;" data-options="resizable:true,modal:true,closed:true">
		<form id="f_insert" method="post" enctype="multipart/form-data">
 
			<input type="hidden" id="gubun" name="gubun" value="boardInsert">
			<input type="hidden" id="b_no" name="b_no" value="0">
			<input type="hidden" id="b_group" name="b_group" value="0">
			<input type="hidden" id="b_pos" name="b_pos" value="0">
			<input type="hidden" id="b_step" name="b_step" value="0">
			
			<table align="center">
				<tr>
					<td>제목</td>
					<td><input id="b_title" name="b_title" data-options="width:'250px'" class="easyui-textbox"></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input id="b_name" name="b_name" class="easyui-textbox"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><input id="b_content" name="b_content" data-options="multiline:'true', width:'350px', height:'90px'" class="easyui-textbox"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input id="b_pwd" name="b_pwd" class="easyui-passwordbox"></td>
				</tr>
				<tr>
					<td>첨부파일</td><!-- 파일을 첨부할때 변수명에 특수문자는 안됨. -->
					<td><input id="bFile" name="bFile" class="easyui-filebox" style="width: 350px"></td>
				</tr>

			</table>
		</form>
		<table align="center">
			<tr>
				<td><a href="javascript:boardInsert()" class="easyui-linkbutton">저장</a> <a href="javascript:$('#dl_boardInsert').dialog('close');" class="easyui-linkbutton">닫기</a></td>
			</tr>
		</table>
	</div>
	<!-- 
글쓰기 다이얼 로그 처리(writeForm.jsp화면 처리)
처음엔 닫힌 상태로 있다가 글쓰기 버튼을 클릭하면 창을 띄움.
 -->
	<!--=================================== 글수정하기 화면 시작[updateForm.jsp] =================================-->
	<div id="dl_update" class="easyui-dialog" title="글수정" style="width: 600px; height: 350px;" data-options="resizable:true,modal:true,closed:true"></div>
	<!--=================================== 글수정하기 화면  끝   =================================-->


</body>
</html>