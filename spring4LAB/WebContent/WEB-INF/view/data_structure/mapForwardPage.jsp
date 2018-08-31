<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%
	Map<String,String> mapTest = (Map<String,String>)request.getAttribute("mapBean");
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<% out.print("[[ for문 ]] Entry<K,V>를 이용해서 Map 출력하기"); %> 
<table border="1">
<%
	for(Map.Entry<String,String> fmap:mapTest.entrySet()) {
%>
	<tr>
		<td><%=String.format("키 : %s 	| 값 : %s", fmap.getKey(), fmap.getValue()) %></td>
	</tr>
<%
	}
%>
</table>


<br>
<br>


<% out.print("[[ while문 ]] map.keySet().iterator()를 이용해서 hasNext()로 Map 출력하기"); %> 
<table border="1">
<%
	Iterator<String> keys = mapTest.keySet().iterator();
	while(keys.hasNext()) {
		String key = keys.next();
%>
	<tr>
		<td><%=String.format("키 : %s 	| 값 : %s", key, mapTest.get(key)) %></td>
	</tr>
<%
	}
%>
</table>


<br>
<br>


<% out.print("[[ for문 ]] map.keySet()을 이용해서 출력하기"); %> 
<table border="1">
<%
	for(String key:mapTest.keySet()) {
%>
	<tr>
		<td><%=String.format("키 : %s 	| 값 : %s", key, mapTest.get(key)) %></td>
	</tr>
<%
	}
%>
</table>

</body>
</html>