package com.kosmo.board;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vo.BoardMasterVO;
import com.vo.BoardSubVO;

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
	
	//글쓰기 
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor={DataAccessException.class})
	@Pointcut(value="execution(* com.mvc.board.*Logic.*(..)")
	public int cudBoard(BoardMasterVO bmVO, BoardSubVO bsVO) {
		logger.info("cudBoard 호출 성공");
		int result = 0;
		//예외처리 - 디버깅, 시스템의 안정화, 트랜잭션처리...
		try {
		//등록 처리에서 Exception이 발생하지 않으면 변수 result에 1을 저장함.
		if(0!=bmVO.getB_no()) {
			logger.info("댓글쓰기");
			sqlBoardDao.stepUpdate(bmVO);
			//insert처리 한 뒤  성공하면 update처리를 뒤에 하고 커밋처리
			bmVO.setB_pos(bmVO.getB_pos()+1);
			bmVO.setB_step(bmVO.getB_step()+1);
		}
		//새글이니? - list.jsp(완전 처음입력하는 상태)-글번호를 새로 채번해야 한다.
		//새글일땐 b_pos와 b_step 0 0
		else{
			logger.info("새글쓰기");
			//댓글일 경우에는 그룹번호를 그대로 사용하지만 새글일땐 새로 채번한 값으로 등록시켜야 한다.
			int b_group = sqlBoardDao.getGroup();
			//반드시 Map에 추가 해 줄것.
			bmVO.setB_group(b_group);
			bmVO.setB_pos(0);
			bmVO.setB_step(0);
		}

		int b_no = sqlBoardDao.getBno();
		bmVO.setB_no(b_no);
		sqlBoardDao.boardMInsert(bmVO);
		if(bsVO.getB_file()!=null && bsVO.getB_file().length()>1) {//첨부파일이 있다
			bsVO.setB_noS(b_no);
			sqlBoardDao.boardSInsert(bsVO);
		}
		result = 1;
		} catch (DataAccessException de) {
			throw de;//이 코드가 반드시 있어야 함. 왜냐하면 Exception발생시 개입해야 하니까
		}
		return result;
	}
	
	
	//상세보기 DB연동
	public List<Map<String, Object>> getBoardDetail(Map<String, Object> pMap) {
		logger.info("getBoardDetail 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlBoardDao.getBoardList(pMap);//조건에 따른 세부내용 조회함. ( 같은 쿼리문을 다른 조건으로 사용함 )
		
		//조회성공했고 그 조건이 하나일때, 조회수 1을 카운트하도록 하자.
		if(boardList != null && boardList.size()==1) {
			int result = sqlBoardDao.hitUpdate(pMap);
			logger.info("hitUpdate result = "+result);
		}
		
		return boardList;
	}

	//글 수정하기
	public List<Map<String, Object>> boardUpdateForm(Map<String, Object> pMap) {
		logger.info("boardUpdateForm 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = sqlBoardDao.getBoardList(pMap);//조건에 따른 세부내용 조회함. ( 같은 쿼리문을 다른 조건으로 사용함 )
		return boardList;
	}
	
	//글 수정처리하기 (저장)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor={DataAccessException.class})
	@Pointcut(value="execution(* com.mvc.board.*Logic.*(..)")
	public int boardMUpdate(Map<String, Object> pMap, MultipartFile b_file) {
		logger.info("boardMUpdate 호출 성공");
		int result = 0;
		try {
		result = sqlBoardDao.boardMUpdate(pMap);
		//첨부파일이 있는경우, 로직에서 boardSub업데이트 처리해야함.
		if(pMap.get("b_file")!=null && b_file!=null) {
			//파일이름과 경로 설정
			String fileName = b_file.getOriginalFilename();
			String path = "E://JLGit//Basic_SpringFramework//mavenTest1//src//main//webapp//pbs//";
			double dsize = 0.0;
			long size = 0;
			//파일생성처리
			if(fileName.length()>0) {
				File file = new File(path+fileName);
				try {
					byte[] bytes = b_file.getBytes();
					BufferedOutputStream bos = 
							new BufferedOutputStream(
									new FileOutputStream(file));
					bos.write(bytes);//쓰기 처리
					bos.close();
					//파일 크기
					size = file.length();
					logger.info("size:"+size);
					dsize = size/(double)(1024);
				} catch (Exception e) {
					logger.info("Exception : "+e.toString());
				}
			}
			//기존파일 삭제처리 (보완필요)
			
			//DB연동 처리
			pMap.put("b_file",fileName);
			pMap.put("b_size",dsize);
			result = sqlBoardDao.boardSUpdate(pMap);
		}
		} catch (DataAccessException dae) {
			throw dae;//이 코드가 반드시 있어야 함. 왜냐하면 Exception발생시 개입해야 하니까
		}
		return result;
	}
	
	//삭제처리하기
	public int boardDelete(Map<String, Object> pMap) {
		logger.info("boardDelete 호출 성공");
		int result = 0;
		//글삭제
		result = sqlBoardDao.boardMDelete(pMap);//조건에 따른 세부내용 조회함. ( 같은 쿼리문을 다른 조건으로 사용함 )
		//첨부파일이 있는경우 테이블의 첨부파일기록도 삭제
		if(!"없음".equals(pMap.get("b_file"))) {
			int sResult = sqlBoardDao.boardSDelete(pMap);
		}
		return result;
	}
	
	
	
	
	//테스트용 프로시저를 출력하늩 테스트 구문
	public String proc_board_test() {
		String msg = null;
		msg = sqlBoardDao.proc_board_test();
		return msg;
	}

	
	
}
