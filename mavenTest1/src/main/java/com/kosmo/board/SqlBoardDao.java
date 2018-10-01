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
		BoardMasterVO rBMVO = null;
		
		Map<String,Object> map = new HashMap<String,Object>();
	    sqlSessionTemplate.selectOne("proc_board_test2",map);
	    //OUT모드가 지정되어있다고해서 리턴타입을 받도록 처리하면 안된다.
	    //왜냐하면 Map에 담겨나오므로 리턴타입을 받을 필요가 없기 때문이다. (아래 원리 참조)
	    
	    //원리
	    //파라미터로 넘긴 map주소번지로 마이바티스 레이어의 #{key}이름으로 꺼내면 List에 접근가능함.
	    //즉, parameterType의 map에는 key에 대한 정보만 가지고 있고
	    //refcursor에서 꺼낸 정보를 갖게되므로 아래 코드로 꺼냄.
	    bList = (List<BoardMasterVO>)map.get("key");
	    //map에는 키값만 담겨있고, 실제 반환된 정보는 resultMap에 있다.
	    
	    logger.info("bList.size():"+bList.get(0).getB_no());
	    for(int i=0;i<bList.size();i++) {
	    	//Map rMap = bList.get(i);
	    	rBMVO = bList.get(i);
	    	//logger.info("이름:"+rMap.get("B_NO")+", 번호:"+bmVO.getB_no());
	    	logger.info("이름:"+rBMVO.getB_name()+", 번호:"+rBMVO.getB_no());
	    }
	    return bList;
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
	
	
	



	
}
