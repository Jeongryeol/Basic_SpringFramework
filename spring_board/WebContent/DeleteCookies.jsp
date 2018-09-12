<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
 
    // 전체 쿠키 삭제하기
    Cookie[] cookies = request.getCookies() ;
     
    if(cookies != null){
        for(int i=0; i < cookies.length; i++){
             
            // 쿠키의 유효시간을 0으로 설정하여 만료시킨다
            cookies[i].setMaxAge(0) ;
             
            // 응답 헤더에 추가한다
            response.addCookie(cookies[i]) ;
        }
    }
     
     
    // 특정 쿠키만 삭제하기
/*     Cookie kc = new Cookie("memberNo", null) ;
    kc.setMaxAge(0) ;
    response.addCookie(kc) ; */
 
 
%>

</body>
</html>