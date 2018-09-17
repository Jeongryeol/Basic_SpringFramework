package com.kosmo.mvc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestLogic {
	private static final Logger logger = Logger.getLogger(TestLogic.class);
	
	@Autowired//Dao계층 클래스를 주입
	public SqlTestDao sqlTestDao = null;
	
	public String crudTest() {
		logger.info("	[알림] crudTest() 호출성공");
		String ctime = sqlTestDao.crudTest();
		return ctime;
	}
	
}
