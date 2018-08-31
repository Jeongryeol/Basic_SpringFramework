package com.di;

import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;

public class SqlBoardDao {
	Logger logger = Logger.getLogger(SqlBoardDao.class);

	//스프링으로부터 객체주입받아서 MyBatis Layer에 걸쳐서 넘어오는 내용
	public SqlSessionTemplate sqlSessionTemplate = null;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int boardMInsert(Map<String, Object> pMap)
			throws DataAccessException {//try-catch문에서 걸려서 스프링이 인터셉트하도록 유도해야함 (메소드 이름에 따른 커밋과 자동롤백)
		logger.info("boardMInsert 호출성공");
		int result = 0;
		result = sqlSessionTemplate.insert("boardMInsert", pMap);
		return result;
	}
	
	public int boardSInsert(Map<String, Object> pMap)
			throws DataAccessException {//try-catch문에서 걸려서 스프링이 인터셉트하도록 유도해야함 (메소드 이름에 따른 커밋과 자동롤백)
		logger.info("boardSInsert 호출성공");
		int result = 0;
		result = sqlSessionTemplate.insert("boardSInsert", pMap);
		return result;
	}
	
	
}
