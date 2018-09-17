package com.mvc.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.util.HangulConversion;

@Service
public class MvcBoardLogic {
	Logger logger = Logger.getLogger(MvcBoardLogic.class);
	
	@Autowired
	private MvcMemberLogic memberLogic = null;	
	
	@Autowired//setter를 쓰지않았을때는 클래스이름 그대로 다 써야합니다.
	private SqlMapBoardDao sqlMapBoardDao = null;
	
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap, HttpServletResponse res) {
		logger.info("logic-getBoardList");
		//업무상 다른 클래스를 재사용할때의 힌트 ( ex. 게시판업무에서 회원업무의 메소드를 사용하고 싶을때 )
		List<Map<String,Object>> boardList = null;
		//boardList = memberLogic.getMemberList(pMap);
		
		int ctotal = 0;
		ctotal = sqlMapBoardDao.totalCount();
		//Controller계층에서는 5개씩 처리하니까 총레코드수가 실제와 다른값
		Cookie c = new Cookie("ctotal",String.valueOf(ctotal));
		c.setPath("/spring_board/spbd");
		c.setMaxAge(60*60*2);
		res.addCookie(c);
		
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
		//boardList.get(0).put("total", total);
		return boardList;
	}
	
	//게시글 입력 DB연동
	//Transaction처리가 되도록 정규화된 내용으로 cud를 적용함.
	public int cudBoard(MvcBoardMasterVO bmVO, MvcBoardSubVO bsVO) {
		logger.info("일단 여기까지 도착함 ㅇㅇ");

		//글쓰기 등록처리에서 Exception이 발생하지 않으면 변수1을 저장함.
		int result = 0;
		
		//트랜잭션 처리를 위해서 발생시킨 DataAccessException을 잡아줄 try-catch block
		try {
		
			//답글이니? - read.jsp(한건이 조회된 상태)-글번호를 가지고 있다.
			//답글쓰기에는 나보다 뒤에 있는 글이 있을 경우 update처리 해야 됨.(step)
			//b_pos와 b_step에 1씩 증가된 값을 설정한다.
			if(0!=bmVO.getB_no()) {
				logger.info("댓글쓰기");
				sqlMapBoardDao.stepUpdate(bmVO);
				//insert처리 한 뒤  성공하면 update처리를 뒤에 하고 커밋처리
				
				//──────[ Map과 VO을 비교할 수 있는 코드 ]──────
				//pMap.put("b_pos", String.valueOf(Integer.parseInt(pMap.get("b_pos").toString())+1));
				bmVO.setB_pos(bmVO.getB_pos()+1);
				//pMap.put("b_step", String.valueOf(Integer.parseInt(pMap.get("b_step").toString())+1));
				bmVO.setB_step(bmVO.getB_step()+1);
				//────────────────────────────────────
			}
			//새글이니? - list.jsp(완전 처음입력하는 상태)-글번호를 새로 채번해야 한다.
			//새글일땐 b_pos와 b_step 0 0
			else{
				logger.info("새글쓰기");
				//댓글일 경우에는 그룹번호를 그대로 사용하지만 새글일땐 새로 채번한 값으로 등록시켜야 한다.
				int b_group = sqlMapBoardDao.getGroup();
					//반드시 Map에 추가 해 줄것.
					//pMap.put("b_group", b_group);
				bmVO.setB_group(b_group);
					//pMap.put("b_pos", 0);
				bmVO.setB_pos(0);
					//pMap.put("b_step", 0);
				bmVO.setB_step(0);
			}
		/*
		 * [코드작성방식]
		 * 상단에서 설정을 미리 해두고
		 * 하단에서 일괄처리하도록 함. ( 일원화 | 재사용성 증대 )
		 */
			int b_no = sqlMapBoardDao.getBno();//시퀀스로 글 번호 채번
			bmVO.setB_no(b_no);
			sqlMapBoardDao.boardMInsert(bmVO);
			//if(pMap.get("b_file")!=null) {//첨부파일이 있다
			if(bsVO.getB_file()!=null && bsVO.getB_file().length()>1) {//첨부파일이 있다.
				bsVO.setB_no(b_no);
				sqlMapBoardDao.boardSInsert(bsVO);
			}
			result = 1;
			
		} catch (DataAccessException dae) {
			//트랜잭션처리를 위해 반드시 있어야하는 코드
			//spring에서 개입하기위해 예외를 발생시킴
			throw dae;
		}
		return result;
	}
	
	//상세보기 DB연동
	public List<Map<String, Object>> getBoardDetail(Map<String, Object> pMap, HttpServletResponse res) {
		logger.info("getBoardDetail 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlMapBoardDao.getBoardList(pMap);
		return boardList;
	}
}
