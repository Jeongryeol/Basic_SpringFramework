package com.compare_pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import util.web.HashMapBinder;

public class MemberController extends MultiActionController {//더이상 지원하지 않겠다는 데플리케이션(어노테이션에 밀림)
	Logger logger = Logger.getLogger(MemberController.class);
	
	//Logic부분 : setter객체 주입법 코드로 작성
	//선언
	private MemberLogic memberLogic = null;
	//setter메소드
	public void setMemberLogic(MemberLogic memberLogic) {
		this.memberLogic = memberLogic;
	}
	
	//DispatcherServlet : ModelAndView객체를 참조함
	
	/*
	 * 생각1	: 스프링프레임워크에서는 메소드를 자유롭게 선언할 수 있다.
	 * 생각2	: 스프링프레임워크에서는 페이지 배치 위치를 2가지로 선택할 수 있다.
	 * 			하나.WebContent 하위
	 * 			둘.	WEB-INF 하위 
	 */
	//회원목록조회 테스트
	//ModelAndView는 forward이므로 WEB-INF 하위에 둔다
	public ModelAndView getMemberList(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		logger.info("getMemberList 호출성공");
		//Logic단으로 넘길 파라미터를 준비함(request객체로부터 Map을 생성)
		Map<String,Object> pMap = new HashMap<>();
		new HashMapBinder(req).bind(pMap);
		
		List<Map<String,Object>> memberList = memberLogic.getMemberList(pMap);
		ModelAndView mav = new ModelAndView();
		mav.addObject("memberList", memberList);
		mav.setViewName("compare_pojo_view/getMemberList");// WEB_INF/view/member/getMemberList.jsp
		return mav;
	}
	//sendRedirect는 Webcontent밑에다가 둬야한다.
	public void memberInsert(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		logger.info("memberInsert 호출성공");
		res.sendRedirect("./memberSuccess.jsp");//분기하지않고 성공했다고 가정해봅시다.
	}
	public void memberUpdate(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		logger.info("memberUpdate 호출성공");
		res.sendRedirect("./memberSuccess.jsp");//분기하지않고 성공했다고 가정해봅시다.
	}
	public void memberDelete(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		logger.info("memberDelete 호출성공");
		res.sendRedirect("./memberSuccess.jsp");//분기하지않고 성공했다고 가정해봅시다.
	}

	
}
