package com.other;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//비지니스 업무 수행하는 어노테이션
public class TestLogic {
	Logger logger = Logger.getLogger(TestLogic.class);
	
	@Autowired
	private SqlTestDao sqlTestDao = null;
	//xml문서에 따로 name속성을 추가하지 않으므로 클래스 이름에 낙타표기법을 준수해야 한다.
	//(sql-test-dao 같이.. 지난번에 임의로 작성했던것과는 다르게 Annotation을 준수해야 주입받을 수 있음)
	
	public void test() {
		logger.info("TestLogic.test() 호출성공");
		sqlTestDao.test();
	}

}
; 