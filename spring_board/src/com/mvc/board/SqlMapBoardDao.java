package com.mvc.board;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

public class SqlMapBoardDao {
	Logger logger = Logger.getLogger(BoardLogic.class);
	
	//setter객체주입법으로 이종간에 주입받아야함
	private SqlSessionTemplate sqlSessionTemplate = null;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap) {
		List<Map<String,Object>> boardList = null;
		//메소드이름과 SELECT문의 id를 일치시킨다.
		boardList = sqlSessionTemplate.selectList("getBoardList",pMap);
		return boardList;
	}
	
	public int totalCount() {
		logger.info("totalCount 호출 성공");
		int total = 0;//전체 레코드 수를 담을 변수
		total = sqlSessionTemplate.selectOne("totalCount",0);
		logger.info("total:"+total);
		return total;
	}
	
}
