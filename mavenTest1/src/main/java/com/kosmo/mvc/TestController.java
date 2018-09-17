package com.kosmo.mvc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
	//다음,구글,네이버 등 OpenAPI(OpenSource)활용할 때 사용하는 특수 컨트롤러
	//화면이 아닌 JSON,XML,문자열로 반응하는 컨트롤러
@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired//Logicr계층 클래스 주입
	public TestLogic testLogic = null;
	
	//web.xml에서 *.spbd를 지정하지 않았으므로(\),/를 붙일 필요가 없다. 
	@RequestMapping("crudTest")
	public String crudTest(Model mod//UI,화면단에 보내줄때 사용해야하는 모델객체
			) {
		logger.info("	[알림] crudTest() 호출성공");
		String ctime = testLogic.crudTest();
		mod.addAttribute("ctime",ctime);
		
		return "forward:testIndex.jsp";
	}
}
