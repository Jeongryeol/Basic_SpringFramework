package com.kosmo.board;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SqlBoardDao {
	private static final Logger logger = LoggerFactory.getLogger(SqlBoardDao.class);
/*		//어노테이션을 이용한 주입방법도 적용가능?
		@Autowired
		private SqlSessionTemplate sqlSessionTemplate = null; */
	//이종간의 주입을 위해 setter객체 주입법을 적용
	private SqlSessionTemplate sqlSessionTemplate = null;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap) {
		logger.info("DAO-getBoardList 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlSessionTemplate.selectList("getBoardList", pMap);
		return boardList;//NullPointerException 주의
	}
}
