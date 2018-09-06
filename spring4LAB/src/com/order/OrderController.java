package com.order;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vo.OrderVO;

@Controller
//@SessionAttributes(value="mem_id") //한개의 세션을 기억할때
@SessionAttributes(value={"mem_id","ord_no","total","s_orderList"})//모델과 세션을 연동함(p.343)
public class OrderController {
	Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	private OrderLogic orderLogic = null;
	
	//패턴작업이나 세션처리 등은 스프링에서 지원할테니, 개발자는 기능구현(logic)만 집중하라는 의도
	
	/*
	 * Model과 ModelAndView는 scope가 같다.
	 * (ModelAndView는 스프링 2.5에서 개발했던 프로젝트에서 주로 사용했으므로 알아야함)
	 */
	@RequestMapping(value="/order/getOrderList.sp",method=RequestMethod.GET) 
	public String getOrderList(Model mod	//화면에 대한 것을 리턴
							  ,ModelMap mMap
				,@RequestParam Map<String,Object> pMap//Request객체에 자동으로 담아주는 어노테이션 (p.293)
				 			  ,SessionStatus sStatus
				,@ModelAttribute("ovo") OrderVO ovo//객체저장값을 저장
							  )
	{
		logger.info("getOrderList 호출성공");
		List<OrderVO> orderList = null;
		orderList = orderLogic.getOrderList(pMap);
		
		//Model에 저장시켜 화면에 넘김 ( ModelAndView일때는 addObject(name,value) )
		mod.addAttribute("orderList",orderList);//request scope
		mod.addAttribute("s_orderList",orderList);//session scope (세션생성)
		mMap.addAttribute("orderListMap",orderList);
		
		//WebContent 하위폴더임
		return "forward:getOrderList.jsp";
		//return "redirect:getOrderList.jsp";
		
		//ViewResolver를 사용해서 WEB-INF 하위의 jsp를 연결해줌 (forward메소드 인스턴스화나 호출이 필요없어짐)
		//return "order/getOrderList";
	}
	
	@RequestMapping(value="/order/sessionAllClear.sp",method=RequestMethod.GET)
	public String sesstionAllClear(SessionStatus sStatus) {
		//화면에서 사용한 session.invalidate();와 역할이 같음
		//단, 스프링에서는 아래와같이 처리하자
		sStatus.setComplete();//세션에 담긴 속성을 삭제함
		return "redirect:sessionTest.jsp";
	}
	
	@RequestMapping(value="/order/orderInsert.sp",method=RequestMethod.POST)
	public String orderInsert(HttpServletRequest req
			   ,@RequestParam Map<String,Object> pMap) {
		logger.info("orderInsert 호출 성공");
		logger.info("주문번호 : "+req.getParameter("ord_no"));
		logger.info("주문번호 : "+pMap.get("ord_no"));
		int result = 0;
		result = orderLogic.orderInsert(pMap);
		return "redirect:orderInsertOk.jsp";
		//sendRedirect하는데, 이 페이지는 Webcontents 하위폴더에 위치함
	}
}
