package com.kosmo.mvc;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

public class SqlTestDao {
	private static final Logger logger = Logger.getLogger(SqlTestDao.class);
	
	//MyBatis와 Spring이 만나는 부분이므로 import가 필요함
	//Auowired이 아니므로 SetterMethod를 설정해주어야함.
	private SqlSessionTemplate sqlSessionTemplate = null;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public String crudTest() {
		logger.info("	[알림] crudTest() 호출성공");
		String ctime = sqlSessionTemplate.selectOne("currentTime");
		logger.info("	[알림] ctime = "+ctime);
		return ctime;
	}
	
}
