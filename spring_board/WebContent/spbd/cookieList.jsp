<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
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
out.print("전체 레코드 수[쿠키로]:"+ctotal);
%>
</body>
</html>