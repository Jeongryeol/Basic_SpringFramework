package com.compare_pojo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);
	
	public SqlBoardDao sqlBoardDao = null;
	public void setSqlBoardDao(SqlBoardDao sqlBoardDao) {
		this.sqlBoardDao = sqlBoardDao;
	}

	public void cudBoard(Map<String, Object> pMap) {
		try {
			sqlBoardDao.boardMInsert(pMap);//마스터 인서트
			sqlBoardDao.boardSInsert(pMap);//서브 인서트
			
		} catch (DataAccessException de) {//스프링에서 제공하는 DB접속에러
			throw de;//강제로 Exception을 던져서 스프링이 Injection함
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
