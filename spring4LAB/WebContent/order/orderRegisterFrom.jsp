<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/0_src/_includeList/commonUI_E.jsp" %>

<script type="text/javascript">
	function orderAdd(){
		$("#f_order").attr("method","post");
		$("#f_order").attr("action","/order/orderInsert.sp");
		$("#f_order").submit();
	}
</script>
</head>
<body>
<h3>주문등록</h3>
<form id="f_order">
	주문번호 : <input id="ord_no" name="ord_no" class="easyui-textbox"/><br>
	주문자 : <input id="oname" name="oname" class="easyui-textbox"/><br>
</form>
<a value="전송" href="javascript:orderAdd()" class="easyui-linkbutton">전송</a>
</body>
</html>