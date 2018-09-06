package com.other;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * @RequestMapping 의 값은 메소드가 처리할 요청 경로를 지정함.
 * 이 어노테이션이 적용된 메소드는 클라이언트의 요청 결과를 보여줄 뷰 이름을 리턴한다.
 * 뷰 이름을 리턴하기 전에 두 가지 작업을 수행한다.
 * 	1) 클라이언트의 요청을 처리한다.
 * 	2) 처리결과데이터를 뷰에 전달한다.
 * 
 */
@Controller
@RequestMapping(value="/other")//other폴더를 바라봐줄래?
public class AController {
	Logger logger = Logger.getLogger(AController.class);
	private A a = null;
	public void setA(A a) {
		this.a = a;
	}
	
	//Spring Annotation덕분에 doGet혹()은 doPost()가 아닌 메소드를 써도 사용할 수 있다.
	/*
	 * 요청 URL이름을 작성해보세요.
	 * 응답페이지를 추가해보세요.
	 */
	@RequestMapping(value="test.sp")
	public String test(HttpServletRequest req, HttpServletResponse res) {
		logger.info("test 호출성공");
		logger.info("클래스 A : "+a);
		//메소드 이름을 내가 정했는데, req나 res를 주입해주나??
		req.setAttribute("name", "김춘추");
		//배포위치는?? 어디로? : 안맞으면 404
		return "forward:aresult.jsp";
		//return "redirect:aresult.jsp";
		//return "aresult.jsp";
	}
}
