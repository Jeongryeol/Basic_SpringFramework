<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map"%>    
<%
	List<Map<String,Object>> getBoardList = 
		(List<Map<String,Object>>)request.getAttribute("boardList");
	out.print("read.jsp");
	String rb_title = "";
	String rb_name = "";
	String rb_content = "";
	String rb_pwd = "";
	String rb_no = "";
	String rb_group = "";
	String rb_pos = "";
	String rb_step = "";
 	if(getBoardList!=null && getBoardList.size()>0){
		rb_title = getBoardList.get(0).get("B_TITLE").toString();
		rb_name = getBoardList.get(0).get("B_NAME").toString();
		rb_content = getBoardList.get(0).get("B_CONTENT").toString();
		rb_pwd = getBoardList.get(0).get("B_PWD").toString();
		rb_no = getBoardList.get(0).get("B_NO").toString();
		rb_group = getBoardList.get(0).get("B_GROUP").toString();
		rb_pos = getBoardList.get(0).get("B_POS").toString();
		rb_step = getBoardList.get(0).get("B_STEP").toString();
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정화면</title>
<!-- 공통 코드 include처리 -->
<%@ include file="../include/commonUI.jsp" %>
</head>
<body>

	<form id="f_update" method="post" enctype="multipart/form-data">
	
		<!-- JQuery는 form태그를 전송할때 action의 ?로 파라미터로써 넘길 수 없다. 따라서 히든으로 넘겨야함. -->
		<input type="hidden" name="b_no" value="<%=rb_no %>"/>
	
	<table align="center">
		<tr>
			<td>제목</td>
			<td>
				<input id="b_title" value="<%=rb_title%>" name="b_title" data-options="width:'250px'" class="easyui-textbox">
			</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>
				<input id="b_name" value="<%=rb_name%>" name="b_name" class="easyui-textbox">
			</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<input id="b_content" value="<%=rb_content%>" name="b_content" data-options="multiline:'true', width:'350px', height:'90px'" class="easyui-textbox">
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input id="b_pwd" value="<%=rb_pwd%>" name="b_pwd" class="easyui-passwordbox">
			</td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td>
				<input id="bfile" name="b_file" class="easyui-filebox" style="width: 350px">
			</td>
		</tr>
		</table>
	</form>
	<table align="center">
		<tr>
			<td>
				<a href="javascript:updateAction()" class="easyui-linkbutton">저장</a>
				<a href="javascript:$('#dl_update').dialog('close');" class="easyui-linkbutton">닫기</a>
			</td>
		</tr>
	</table>
	
	</form>
	
</body>
</html>