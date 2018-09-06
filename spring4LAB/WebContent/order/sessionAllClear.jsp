<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//세션에 담긴 모든 정보를 삭제합니다.
	session.invalidate();

	//하나씩 삭제할 떄
	//session.removeAttribute("세션이름");
%>