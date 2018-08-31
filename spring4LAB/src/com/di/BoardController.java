package com.di;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import util.web.HashMapBinder;


public class BoardController extends MultiActionController {
	Logger logger = Logger.getLogger(BoardController.class);
	//setter객체 주입법 코드
	//선언
	public BoardLogic boardLogic = null;
	public void setBoardLogic(BoardLogic boardLogic) {
		this.boardLogic = boardLogic;
	}
	public void boardInsert(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		logger.info("boardInsert 호출 성공");
		Map<String, Object> pMap = new HashMap<String,Object>();
		HashMapBinder hmb =  new HashMapBinder(req);
		hmb.bind(pMap);
		//boardLogic.boardInsert(pMap);//매 건마다 commit
		
		//트랜잭션처리된 내용
		boardLogic.cudBoard(pMap);//묶어서 마지막에 commit
		//boardLogic.doBoard(pMap);//묶어서 마지막에 commit
		
		//오타로 인식되어 건건마다 처리되는 내용
		//boardLogic.eudBoard(pMap);//매 건마다 commit
		
		
	}
}
