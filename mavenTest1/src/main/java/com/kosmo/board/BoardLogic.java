package com.kosmo.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardLogic {
	private static final Logger logger = LoggerFactory.getLogger(BoardLogic.class);

	@Autowired//어노테이션 객체주입
	private SqlBoardDao sqlBoardDao = null;
	
	
	//게시판 목록얻기 DB연동
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap, HttpServletResponse res) {
		logger.info("LOGIC-getBoardList 호출성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlBoardDao.getBoardList(pMap);
		logger.info("LOGIC-boardList : "+boardList);
		return boardList;
	}
	
	
	//상세보기 DB연동
	public List<Map<String, Object>> getBoardDetail(Map<String, Object> pMap, HttpServletResponse res) {
		logger.info("getBoardDetail 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlBoardDao.getBoardList(pMap);
		return boardList;
	}
}
