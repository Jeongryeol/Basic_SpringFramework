<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.vo.OrderVO"%>
<%
	List<OrderVO> orderList
		= (List<OrderVO>)session.getAttribute("s_orderList");

//@SessionAttributes(value={"mem_id","ord_no","total","s_orderList"})
	String s_memid = (String)session.getAttribute("mem_id");
	String s_ordno = (String)session.getAttribute("ord_no");
	String s_total = (String)session.getAttribute("total");

	if(orderList != null) {
		out.print("세션에 정보가 있습니다. <br>");
		out.print(orderList.toString());
	}
	else {
		out.print("세션에 정보가 없네요.<br>");
	}
	out.print("<br>-----------------------------------------------<br>");
	//똑같이 Model에 담은 정보가 Session속성에 선언된 경우와, 그렇지 않은 경우의 차이는 무엇일까요?
	List<OrderVO> nonOrderList
		= (List<OrderVO>)session.getAttribute("orderList");
	if(nonOrderList != null) {
		out.print("세션에 정보가 있습니다. <br>");
		out.print(nonOrderList.toString());
	}
	else {
		out.print("세션에 정보가 없네요.<br>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session scope를 갖는 객체 이름은?</title>
</head>
<body>
<div>
<div>
	<h3>테스트시나리오1</h3>
	세션을 생성하는 요청처리 없이 해당 페이지를 불러 봅니다.<br>
	<b>
	결과 : 세션에 정보가 없네요.
	</b>
	<h3>결론2</h3>
	세션 속성에 대한 처리과정 없이는<br>
	아무리 세션에 담긴 정보라 하더라도 확인할 수 없습니다.	
<div>--------------------------------------------------------------------------------</div>
	<h3>테스트시나리오2</h3>
	/order/getOrderList.sp 요청을 하여<br>
	어노테이션을 통한 세션이름선언과<br>
	Model을 통해서 세션스코프로 담긴 정보를<br>
	이 페이지에서 다시 요청해서 내용을 확인해봅니다.<br>
	<b>
	결과 : 세션에 정보가 있습니다.
	</b>
	<h3>결론2</h3>
	Model에 담은 정보를 다른 페이지에서 호출해보면<br>
	그 값이 유지(공유)되고 있다는 것을 확인할 수 있습니다.
<div>--------------------------------------------------------------------------------</div>
	<h3>테스트시나리오3</h3>
	세션선언에서 사용된 이름에 아무런 값도 매핑되지 않았을 경우에<br>
	어떤 정보가 default로 매핑되는지 확인해봅니다.<br>
	세션 아이디 : <%=s_memid %><br>
	세션 주문번호 : <%=s_ordno %><br>
	세션 전체 레코드 수 : <%=s_total %><br>
	<h3>결론3</h3>
	세션 속성에 선언만 되어 있고, Model객체를 통해 담지 않았을 경우 모두 null상태로 됨.
</div>
</div>
</body>
</html>