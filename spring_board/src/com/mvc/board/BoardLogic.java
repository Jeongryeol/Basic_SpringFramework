package com.mvc.board;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.HangulConversion;

//Controller계층에서 Autowired가 아닌 setter객체주입법으로 xml에서 주입받을때 의 예제
@Service
public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);
	
	@Autowired
	private MemberLogic memberLogic = null;	
	
	@Autowired//setter를 쓰지않았을때는 클래스이름 그대로 다 써야합니다.
	private SqlMapBoardDao sqlMapBoardDao = null;
	
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap) {
		logger.info("logic-getBoardList");
		//업무상 다른 클래스를 재사용할때의 힌트 ( ex. 게시판업무에서 회원업무의 메소드를 사용하고 싶을때 )
		List<Map<String,Object>> boardList = null;
		//boardList = memberLogic.getMemberList(pMap);
		
	//───[ pagenation : START ]─────────────────────────────────────────────────────────────
		int total = 0;
		total = sqlMapBoardDao.totalCount();
		int page = 0;//현재 사용자가 바라보는 페이지 번호 - pageNumber
		int pageSize = 0;//한 페이지에 몇개씩 보여줄 거니? 5|10|15|20
		int start = 0;//페이지의 시작 번호
		int end = 0;//페이지의 끝 번호
		if(pMap.containsKey("page")) {
			page = Integer.parseInt(pMap.get("page").toString());
			logger.info("page : "+page);
		}
		if(pMap.containsKey("pageSize")) {
			pageSize = Integer.parseInt(pMap.get("pageSize").toString());
			logger.info("pageSize : "+pageSize);
		}
		if(page>0) {
			start = ((page-1)*pageSize)+1;
			end = page*pageSize;
			pMap.put("start", start);
			if(end > total) {
				pMap.put("end", total);
			}else {
				pMap.put("end", end);
			}
		}
	//───[ pagenation : END ]─────────────────────────────────────────────────────────────
		
		boardList = sqlMapBoardDao.getBoardList(pMap);
		boardList.get(0).put("total", total);
		return boardList;
	}
	
	//Transaction처리가 되도록 정규화된 내용으로 cud를 적용함.
	public List<Map<String,Object>> cudBoard(BoardMasterVO bmVO
											,BoardSubVO bsVO){
		return null;
	}
}
