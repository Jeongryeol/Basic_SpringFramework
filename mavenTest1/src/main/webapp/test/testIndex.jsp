<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctime = (String)request.getAttribute("ctime");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testIndex.jsp</title>
</head>
<body>

<h1>testIndex.jsp 페이지 도착했습니다.</h1>

현재시간 : <%=ctime %>

</body>
</html>