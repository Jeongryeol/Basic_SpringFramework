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
	String rb_file = "";
	
 	if(getBoardList!=null && getBoardList.size()>0){
		rb_title = getBoardList.get(0).get("B_TITLE").toString();
		rb_name = getBoardList.get(0).get("B_NAME").toString();
		rb_content = getBoardList.get(0).get("B_CONTENT").toString();
		rb_pwd = getBoardList.get(0).get("B_PWD").toString();
		rb_no = getBoardList.get(0).get("B_NO").toString();
		rb_group = getBoardList.get(0).get("B_GROUP").toString();
		rb_pos = getBoardList.get(0).get("B_POS").toString();
		rb_step = getBoardList.get(0).get("B_STEP").toString();
		rb_file = getBoardList.get(0).get("B_FILE").toString();
	}
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Insert title here</title>
<!-- 공통 코드 include처리 -->
<%@ include file="../include/commonUI.jsp" %>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function(){
	<%-- 		$("#b_title").textbox('setValue','<%=rb_title%>');
			$("#b_name").textbox('setValue','<%=rb_name%>');
			$("#b_content").textbox('setValue','<%=rb_content%>');
			$("#b_pwd").textbox('setValue','<%=rb_pwd%>'); --%>
		});
	</script>
	<table align="center">
		<tr>
			<td>제목</td>
			<td>
				<input id="b_title" value="<%=rb_title%>" name="b_title" data-options="width:'250px'" class="easyui-textbox" readonly>
			</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>
				<input id="b_name" value="<%=rb_name%>" name="b_name" class="easyui-textbox" readonly>
			</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<input id="b_content" value="<%=rb_content%>" name="b_content" data-options="multiline:'true', width:'350px', height:'90px'" class="easyui-textbox" readonly>
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input id="b_pwd" value="<%=rb_pwd%>" name="b_pwd" class="easyui-passwordbox" readonly>
			</td>
		</tr>
	</table>
	</form>
	<table align="center">
		<tr>
			<td>
				<a href="javascript:repleForm('<%=rb_no%>','<%=rb_group%>','<%=rb_pos%>','<%=rb_step%>')" class="easyui-linkbutton">댓글</a>
				<a href="javascript:updateForm('<%=rb_no %>')" class="easyui-linkbutton">수정</a>
				<a href="javascript:deleteForm('<%=rb_no %>','<%=rb_pwd %>','<%=rb_file %>')" class="easyui-linkbutton">삭제</a>
				<a href="javascript:dg_listReload()" class="easyui-linkbutton">목록</a>
			</td>
		</tr>
	</table>
</body>
</html>