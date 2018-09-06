package com.other;


import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;

public class SqlTestDao {
	Logger logger = Logger.getLogger(SqlTestDao.class);
	//setter메소드에 활용하는 spring의 변수이름은 마음대로 설정하면 안된다.
	//ref에 해당
	private SqlSessionTemplate sqlSessionTemplate;
	//name에 해당
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public String test() throws DataAccessException{
		String day = sqlSessionTemplate.selectOne("currentDay");
		logger.info("day : "+day);
		return day;
	}
	
}
