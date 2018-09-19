package com.kosmo.board;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.vo.BoardMasterVO;
import com.vo.BoardSubVO;


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
	
	
	
	
	/*
	 * SELECT한 결과를 sys_refcursor를 활용하여 외부로 꺼내고,
	 * 결과값은 List<VO>로 담도록 MyBatis와 프로시저를 연계하기
	 * 
	 * 1. 리턴타입이 n건 일 수 있으므로 List로 받고 제네릭타입은 VO로 정함.
	 * 2. 파라미터로 넘길 값은 Map을 사용함.
	 * 3. MyBatis에서 
	 */
	public List<BoardMasterVO> proc_board_test2() throws SQLException{
		logger.info("proc_board_test 호출 성공");
		String msg=null;
/*		BoardMasterVO bVO = new BoardMasterVO();
		bVO.setB_no(84);*/
		List<BoardMasterVO> bList = null;
		//List<Map<String,Object>> bList2 = null;
	    //bList = sqlSessionTemplate.selectList("proc_board_test",bList);
		Map<String,Object> map = new HashMap<String,Object>();
	    sqlSessionTemplate.selectOne("proc_board_test2",map);
	    bList = (List<BoardMasterVO>)map.get("key");
	    //bList2 = (List<Map<String,Object>>)map.get("key");
		//msg = bVO.getMsg();
	    //logger.info("bList.size():"+bList);
	    logger.info("bList.size():"+bList.get(0).getB_no());
/*	    for(int i=0;i<bList.size();i++) {
	    	BoardMasterVO bmVO = bList.get(i);
	    	logger.info("이름:"+bmVO.getB_name()+", 번호:"+bmVO.getB_no());
	    }*/
	    for(int i=0;i<bList.size();i++) {
	    	BoardMasterVO rBMVO = bList.get(i);
	    	logger.info("이름:"+rBMVO.getB_name()+", 번호:"+rBMVO.getB_no());
	    }
	    return bList;
	    /*
	    for(int i=0;i<bList2.size();i++) {
	    	Map<String,Object> rMap = bList2.get(i);
	    	logger.info("이름:"+rMap.get("B_NAME")+", 번호:"+rMap.get("B_NO"));
	    }
		return bList2;
		*/
	}
	
	
	
	
	
	
	
	
	//전체조회, 조건검색, 상세조회, 수정화면처리 의 업무를 수행할 수 있는 동적쿼리를 연계하는 메소드
	public List<Map<String, Object>> getBoardList(Map<String, Object> pMap) {
		logger.info("DAO-getBoardList 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlSessionTemplate.selectList("getBoardList", pMap);
		return boardList;//NullPointerException 주의
	}
	
	//조회수를 카운트하는 쿼리문을 호출할 메소드
	public int hitUpdate(Map<String,Object> pMap) {
		int result = 0;
		result = sqlSessionTemplate.update("hitUpdate",pMap);
		return result;
	}
	
	
	public int stepUpdate(BoardMasterVO bmVO) {
		logger.info("stepUpdate 호출 성공");
		int result = 0;
		result = sqlSessionTemplate.update("stepUpdate",bmVO);
		return result;
	}
	
	//글쓰기 구현을 위한 Dao 메소드
		//댓글의 경우는 read.jsp(상세보기 페이지)에서 값을 담으면 되지만
		//새글의 경우는 채번하여 값을 저장함
	public int getGroup() {
		int b_group = 0;//전체 레코드 수를 담을 변수
		b_group = sqlSessionTemplate.selectOne("getGroup");
		return b_group;
	}
	
	//첨부파일이 없을때 일반 정보 등록하기
	public int boardMInsert(BoardMasterVO bmVO)
				throws DataAccessException	//트랜잭션 처리를위해 데이터접속예외 던지기
	{
		int result = 0;
		result = sqlSessionTemplate.insert("boardMInsert",bmVO);
		return result;
	}
	
	//첨부파일이 있을 때 파일 첨부하기
	public int boardSInsert(BoardSubVO bsVO)
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
	
	//글 수정처리하기(저장)
	public int boardMUpdate(Map<String, Object> pMap) {
		logger.info("boardMUpdate 호출 성공");
		int result = 0;
		result = sqlSessionTemplate.update("boardMUpdate",pMap);
		return result;
	}

	//글 수정처리하기(저장시 첨부파일처리)
	public int boardSUpdate(Map<String, Object> pMap) {
		logger.info("boardSUpdate 호출 성공");
		int result = 0;
		result = sqlSessionTemplate.update("boardSUpdate",pMap);
		return result;
	}
	
	//삭제처리하기
	public int boardMDelete(Map<String, Object> pMap) {
		logger.info("boardMDelete 호출 성공");
		int result = 0;
		result = sqlSessionTemplate.delete("boardMDelete",pMap);
		return result;
	}
	
	//삭제처리할때 첨부파일이 있는 경우 첨부파일도 삭제하기
	public int boardSDelete(Map<String, Object> pMap) {
		int result = 0;
		result = sqlSessionTemplate.delete("boardSDelete",pMap);
		return result;
	}
	
	
	
	
	//테스트용 프로시저를 출력하는 테스트구문
	public String proc_board_test() {
		String msg = null;
		BoardMasterVO bmVO = new BoardMasterVO();
		bmVO.setB_no(60);
		sqlSessionTemplate.selectOne("proc_board_test", bmVO);
		msg = bmVO.getMsg();
		logger.info("msg = "+msg);
		return msg;
	}





	
}
