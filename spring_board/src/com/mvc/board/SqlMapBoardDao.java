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
import org.springframework.dao.DataAccessException;

public class SqlMapBoardDao {
	Logger logger = Logger.getLogger(MvcBoardLogic.class);
	
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
	
	public int stepUpdate(MvcBoardMasterVO bmVO) {
		logger.info("stepUpdate 호출 성공");
		int result = 0;
		result = sqlSessionTemplate.update("stepUpdate",bmVO);
		return result;
	}
	
	//댓글의 경우는 read.jsp(상세보기 페이지)에서 값을 담으면 되지만
	//새글의 경우는 채번하여 값을 저장함
	public int getGroup() {
		int b_group = 0;//전체 레코드 수를 담을 변수
		b_group = sqlSessionTemplate.selectOne("getGroup");
		return b_group;
	}
	
	
	
	/*
	 * 게시판 구현을 위해서 테이블 정규화를 통해 2개로 분리한 상태입니다.
	 * 따라서 반드시 트랜잭션처리가 필요하므로 Logic계층에서 처리합니다.
	 * 
	 * 1. SQLException 대신, Spring에서 지원하는 DataAccessException을 발생시킵니다.
	 * 2. 에러가 발생하면 try블록에서 걸리게 됩니다.
	 */
	
	//첨부파일이 없을때 일반 정보 등록하기
	public int boardMInsert(MvcBoardMasterVO bmVO)
				throws DataAccessException	//트랜잭션 처리를위해 데이터접속예외 던지기
	{
		int result = 0;
		result = sqlSessionTemplate.insert("boardMInsert",bmVO);
		return result;
	}
	
	//첨부파일이 있을 때 파일 첨부하기
	public int boardSInsert(MvcBoardSubVO bsVO)
				throws DataAccessException	//트랜잭션 처리를위해 데이터접속예외 던지기
	{
		int result = 0;
		result = sqlSessionTemplate.insert("boardSInsert",bsVO);
		return result;
	}
	
	//글번호 채번하기
	public int getBno() {
		int result = 0;
		result = sqlSessionTemplate.selectOne("getBno");
		return result;
	}

}
