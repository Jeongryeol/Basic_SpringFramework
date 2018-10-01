<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String b_no   = request.getParameter("b_no");  //글번호 받기
	String b_pwd  = request.getParameter("b_pwd"); //해당글의 글비밀번호 받기
	String b_file = request.getParameter("b_file");//해당글의 첨부파일명 받기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호확인(pwVerifyForm.jsp)</title>
<!-- 공통 코드 include처리 -->
<%@ include file="../include/commonUI.jsp" %>

	<script type="text/javascript">
		//글 삭제 처리하기	
		function deleteAction(){
			//글을 삭제하는데 필요한것은 비밀번호와 글번호 (상단 스크립틀릿에서 받아둠)
			
			var result = 0;
			
			//비밀번호가 틀린 경우
			if($("#b_pwd").val() != <%=b_pwd %>){
				alert("비밀번호가 일치하지 않습니다.");
				$("#b_pwd").val('');//비우기
				$("#b_pwd").focus();//커서옮기기
			}
				
			//비밀번호가 일치하는 경우
			else{//폼전송 실행
				alert("비밀번호가 일치합니다. 글을 삭제합니다.");
				$("#f_verify").attr("method", "post");
				$("#f_verify").attr("action", "./boardDelete");
				$("#f_verify").submit(); //이 때 서버로 전송이 일어남
				result = 1;
			}
			
			if(result==1) selfClose();
		}
		function selfClose(){
				//read.jsp에서 띄워진 창(commons.js 기능)으로부터 켜진 창을 닫음 
				self.close();//전송하고나면 자기자신의 창은 닫음
		}
	</script>

</head>
<body>

	<form id="f_verify">
		<table border="1" align="center">
			<input type="hidden" id="b_no" name="b_no" value="<%=b_no %>"/>
			<input type="hidden" id="b_pwd" name="b_pwd" value="<%=b_pwd %>"/>
			<input type="hidden" id="b_file" name="b_file" value="<%=b_file %>"/>
			<input type="password" id="b_pwd" name="b_pwd" size="15" class="easyui-passwordbox"/>
			<input type="button" value="확인" onClick="javascript:deleteAction()" />
		</table>
	</form>

</body>
</html>