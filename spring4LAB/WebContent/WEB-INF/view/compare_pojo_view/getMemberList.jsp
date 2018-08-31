<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%
	List<Map<String,Object>> memberList
			= (List<Map<String,Object>>)request.getAttribute("memberList");
	int size = 0;
	if(memberList != null){
		size = memberList.size();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록조회</title>
</head>
<body>
<h1>여기까지 오느라 수고가 많았네요.</h1>
<%
	if(size>0){
		out.print("조회결과가 있어요!!<br>");
		out.print(size);
	} else {
		out.print("조회결과가 없네요..<br>");
		out.print(size);
	}
%>
</body>
</html>