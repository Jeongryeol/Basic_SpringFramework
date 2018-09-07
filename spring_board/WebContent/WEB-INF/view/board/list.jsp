<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	Integer total = null;
	total = (Integer)session.getAttribute("total");
	int ctotal = 0;
	//out.print("전체 레코드 수:"+total);
	//쿠키에 저장된 값을 불러오기
	Cookie[] cs = request.getCookies();
	for(int i=0;i<cs.length;i++){
		String cName = cs[i].getName();
		//out.print("쿠키이름:"+cName);
		if("ctotal".equals(cName)){
			ctotal = Integer.parseInt(cs[i].getValue());
		}
	}	
	//out.print("전체 레코드 수[쿠키로]:"+ctotal);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>글목록</title>
<!-- 공통 코드 include처리 -->
<%@ include file="../../../include/commonUI.jsp" %>
<script type="text/javascript">
	var g_total=0;
	//댓글쓰기 할 때
	function repleForm(pb_no, pb_group, pb_pos, pb_step){
		alert("repleForm호출 성공"+pb_no+","+pb_group+","+pb_pos+","+pb_step);
		$("#b_no").val(pb_no);
		$("#b_group").val(pb_group);
		$("#b_pos").val(pb_pos);
		$("#b_step").val(pb_step);
		$("#dl_read").dialog('close');
		$("#dl_boardInsert").dialog('open');
	}
	//글 수정할 때 - DB를 태워야 함.
	function updateForm(pb_no){
		//alert("updateForm호출 성공");
		$("#dl_read").dialog('close');
        $("#dl_update").dialog({
    		href:'./mvcBoard.bd?gubun=getBoardList&one=update&b_no='+pb_no,
        });
		$("#dl_update").dialog('open');
	}
	//글수정  처리하기
	function updateAction(){
		$("#f_update").attr("method","post");
		$("#f_update").attr("action","./mvcBoard.bd");
		$("#f_update").submit();//이 때 서버로 전송이 일어남
	}
	//글삭제할 때
	function deleteForm(){
		alert("deleteForm호출 성공");
	}
	//글상세보기에서 글목록 돌아갈때
	function dg_listReload(){
		//alert("dg_listReload호출 성공");
		$("#dl_read").dialog('close');
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList'	
		});
	}
	function bSearch(){
	//지금의 경우 이미 DOM구성이 완료되었을 때 액션을 이미 탄 상태가 되고 그 결과는 jsonBoardList.jsp로 응답이 나온다.
/* 		$("#f_search").attr("method","get");
		$("#f_search").attr("action","./mvcBoard.bd?gubun=getBoardList");
		$("#f_search").submit(); */
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&cb_type='+$("#cb_type").val()+'&sb_keyword='+$("#sb_keyword").val()		
		});
	}
	//글쓰기 화면 띄우기
	function boardInsForm(){
		$("#dl_boardInsert").dialog('open');
	}
	//글쓰기 등록 처리하기
	function boardInsert(){
		alert("gubun:"+$("#gubun").val());
		$("#f_insert").attr("method","post");
		$("#f_insert").attr("action","./mvcBoard.bd");
		$("#f_insert").submit();//이 때 서버로 전송이 일어남
	}
	//페이지 네이션에서 페이지 이동 버튼을 클릭했을 때 실제 페이지 이동처리 구현
	function pageMove(pageNumber, pageSize){
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&page='+pageNumber+'&pageSize='+pageSize
		});    		
	}
</script>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&page=1&pageSize=5',
		    columns:[[
		        {field:'B_NO',title:'글번호',width:100, align:'center'},
		        {field:'B_TITLE',title:'제목',width:400, align:'center'},
		        {field:'B_NAME',title:'이름',width:100, align:'center'},
		        {field:'B_FILE',title:'첨부파일',width:150, align:'center'},
		        {field:'B_HIT',title:'조회수',width:100, align:'center'},
		    ]],
		/* 데이터 조회가 완료되었을 때 */
		    onDblClickRow: function(index,row){
		    	//alert("글번호 : "+row.B_NO);
		    	//다이얼 로그 창을 띄워봐요 - 코드 추가 - 스크립트로 처리
		    	//list.jsp페이지 내에 다이얼로그 페이지 구현이 가능하니까 - 페이지가 분리되어 있지 않은 상태
		        $("#dl_read").dialog({
		    		href:'./mvcBoard.bd?gubun=getBoardList&one=one&b_no='+row.B_NO,
		    	    onLoad:function(){
		    	    	//alert("success!!!");
		    	    }
		        });
				$("#dl_read").dialog('open');
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
		<td>
		<select id="cb_type" class="easyui-combobox" name="cb_type" style="width:200px;">
		    <option value="b_title">제목</option>
		    <option value="b_content">내용</option>
		    <option value="b_name">작성자</option>
		</select>
		<input id="sb_keyword" name="sb_keyword" class="easyui-searchbox" style="width:300px"
        data-options="searcher:bSearch,prompt:'Please Input Value'"></input>
		</td>
	</tr>
</form>	
<!-- 글쓰기 버튼 추가 -->	
	<tr>
		<td align="left">
        <a id="btn_write" href="javascript:boardInsForm()" class="easyui-linkbutton">글쓰기</a>
		</td>
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
		<div id="pg_board" style="background:#efefef;border:1px solid #ccc;">
		</div>		
		</td>
	</tr>	
</table>
<!-- 글상세 보기 다이얼로그 처리 시작[read.jsp] -->
<div id="dl_read" class="easyui-dialog" title="글상세보기" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
</div> 
<!-- 글상세 보기 다이얼로그 처리  끝   -->
<!-- 
글쓰기 다이얼 로그 처리(writeForm.jsp화면 처리)
처음엔 닫힌 상태로 있다가 글쓰기 버튼을 클릭하면 창을 띄움.
 -->
<div id="dl_boardInsert" class="easyui-dialog" title="글쓰기" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
 	    <form id="f_insert" method="post" enctype="multipart/form-data">
<!-- 	    <form id="f_insert"> -->
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
	    	<td>첨부파일</td>
	    	<td><input id="b_file" name="b_file" class="easyui-filebox" style="width:350px"></td>
	    	</tr>
	    		    	
	   </table>
	    	</form>
	    <table align="center">
	    	<tr>
	    	<td>
				<a href="javascript:boardInsert()" class="easyui-linkbutton">저장</a>
				<a href="javascript:$('#dl_boardInsert').dialog('close');" class="easyui-linkbutton">닫기</a>
	    	</td>
	    	</tr>
	    </table>
</div> 
<!-- 
글쓰기 다이얼 로그 처리(writeForm.jsp화면 처리)
처음엔 닫힌 상태로 있다가 글쓰기 버튼을 클릭하면 창을 띄움.
 -->
 <!--=================================== 글수정하기 화면 시작[updateForm.jsp] =================================-->
 <div id="dl_update" class="easyui-dialog" title="글수정" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
</div> 
 <!--=================================== 글수정하기 화면  끝   =================================-->
</body>
</html>










