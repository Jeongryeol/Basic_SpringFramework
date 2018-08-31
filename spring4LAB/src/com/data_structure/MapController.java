package com.data_structure;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class MapController extends AbstractController{

	Map<String,String> mapBean = null;
	
	public void setMapBean(Map<String,String> mapBean) {
		this.mapBean = mapBean;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mapBean",mapBean);
		mav.setViewName("data_structure/mapForwardPage");
		return mav;
	}

}
