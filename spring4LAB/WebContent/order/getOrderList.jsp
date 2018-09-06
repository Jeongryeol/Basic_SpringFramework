<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.vo.OrderVO, java.util.Map ,java.util.HashMap" %>
<%
	List<OrderVO> orderList = (List<OrderVO>)request.getAttribute("orderList");
	List<OrderVO> orderListMap = (List<OrderVO>)request.getAttribute("orderListMap");
	
	out.print("orderList.size() : "+orderList.size()+"<br>");
	if(orderList!=null){
		out.print(orderList.get(0).getOname()+"<br>");
	}
	out.print("orderList.size() : "+orderList.size()+"<br>");
	if(orderListMap!=null){
		out.print(orderListMap.get(0).getOname()+"<br>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
나오셨습니까요???

여긴 WebContent 하위입니다요.
</body>
</html>