package com.other;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//스프링입장에서 아래 컨트롤러정보를 찾아야한다. 패키지로 찾게 될 것.
//web.xml에서 초기화하는 xml문서중 applicationContext.xml과 같이 최초로 주입관계를 나타내는 문서에서 베이스 패키지를 설정할 수 있다.
//<context:component-scan base-package="com.other"/>
@Controller
public class TestController {
	Logger logger = Logger.getLogger(TestController.class);
	
	//p105 : 의존관계를 자동으로 설정하는 방법 3가지 중 1가지
	@Autowired(required=false)//false : 스프링은 일치하는 스프링빈이 존재하지 않더라도 익셉션을 발생시키지 않음 ( 만약 일치하면 해당 빈 객체를 사용함) / true : 스프링빈이 반드시 존재해야함
	public TestLogic testLogic = null;
	//위 코드를 xml bean태그로 나타내보면 다음과 같다
	/* <bean>
	 * 		<property name="testLogic" ref="test-logic"/>
	 * </bean>
	 */
	
	@RequestMapping(value="/other/testIndex.sp",method=RequestMethod.GET)//또는 POST (전송방식표시가능)
	public String Test() {
		logger.info("Test호출 성공");
		testLogic.test();
		return "redirect:testIndex.jsp";
		
		//RequestMapping에 의해 배포되는 위치 3가지를 반드시 기억할 것...
		//return "redirect:testIndex.jsp";	//Webcontent
		//return "forward:testIndex.jsp";	//Webcontent
		//return "textIndex"	//  WEB-IND/view/testIndex.jsp

	}
}
