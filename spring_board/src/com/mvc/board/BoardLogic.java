package com.mvc.board;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Controller계층에서 Autowired가 아닌 setter객체주입법으로 xml에서 주입받을때 의 예제
@Service
public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);
	
	@Autowired
	private MemberLogic memberLogic = null;	
	
	@Autowired
	private SqlMapBoardDao sqlMapBoardDao = null;
	
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap) {
		logger.info("logic-getBoardList");
		//업무상 다른 클래스를 재사용할때의 힌트
		List<Map<String,Object>> boardList = null;
		boardList = memberLogic.getMemberList(pMap);
		
		boardList = sqlMapBoardDao.getBoardList(pMap);
		
		return boardList;
	}

	
}
