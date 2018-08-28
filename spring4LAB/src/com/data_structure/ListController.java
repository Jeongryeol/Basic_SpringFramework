package com.data_structure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ListController extends AbstractController {

	List<String> listBean = null;
	
	public void setListBean(List<String> listBean) {
		this.listBean = listBean;
	}
	
	//WEB-INF에 배포할 때 : URL로 접속불가 
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();//응답페이지를 연결시켜주는 객체
		mav.addObject("listBean", listBean);//request.setAttribute와 같은 역할
		mav.setViewName("data_structure/listForwardPage");//bean의 setter객체 주입법으로 만들어진 객체를 유지시켜서 포워딩하기위한 목적지 설정
		return mav;
	}

}
