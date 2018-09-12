package com.mvc.board;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.util.HangulConversion;

@Controller
@RequestMapping("/spbd")//모든 매핑에 선행매핑 추가하기
public class MvcBoardController {
	Logger logger = Logger.getLogger(MvcBoardController.class);
	
	//setter객체주입법을 사용하는 경우라면 개발자가 그 이름을 xml 문서에 등록할 수 있지만,
	//Autowired를 사용할 때는 xml문서에 추가 등록자체가 필요 없기 때문에 클래스이름을 반드시 따라서 낙타표기법으로 선언함
	@Autowired//클래스이름을 반드시 따라서 낙타표기법으로 선언해야 합니다.
	private MvcBoardLogic boardLogic = null;
	//setter메소드로 연결을할떄는, servlet에서 name등록하고, service.xml에 bean을 등록함
/*	
	private BoardLogic bLogic = null;
 	public void setBLogic(BoardLogic bLogic) {
		this.bLogic = bLogic;
	}
*/

	@RequestMapping("getBoardList.spbd")
	public String getBoardList(
			@RequestParam	 Map<String,Object> pMap
							,Model mod
							,HttpServletResponse res
			){
		logger.info("controller-getBoardList");
		logger.info("pMap = "+pMap);
		List<Map<String,Object>> boardList = null;
		
		//검색 요청이 전체 조회인 경우 조건 검색인 경우 나누어진다.
/*		//POST방식일때 한글처리함
  		if(pMap.containsKey("cb_type")) {
			pMap.put("sb_keyword"
					,HangulConversion.toKor(pMap.get("sb_keyword").toString()));//한글처리
		}*/
		
		boardList = boardLogic.getBoardList(pMap,res);
		
		//전체개수를 담은 쿠키세팅하기
/*		int ctotal = (int)boardList.get(0).get("total");
		Cookie c = new Cookie("ctotal", String.valueOf(ctotal));
		c.setMaxAge(60*60*24);//시간
		res.addCookie(c);
*/
		
		//boardList = bLogic.getBoardList(pMap);//setter메소드방식일때의 코드
		mod.addAttribute("getBoardList", boardList);//화면에 넘길 모델에 속성을 저장함
		//return "board/list2";
		logger.info("─────────────────────────────────────────────────────────────────────────────────────────────────");
		return "forward:jsonBoardList.jsp";
	}
	
	//jsp에서 UI Solution을 통해서 스프링을 경유하도록 한다.
	@RequestMapping("writeForm.spbd")
	public String writeForm(){
		return "redirect:writeForm.jsp";
	}
	
	//스프링으로 첨부파일 구현하기
	@RequestMapping("boardInsert.spbd")
	public String boardInsert(//RequestParam의 이름과 변수명이 ModelAttribute의 변수명과 달라야 한다.
							  @RequestParam("bFile") MultipartFile bfile
							  ,HttpServletResponse res
							  //부모테이블의 컬럼명과 자식테이블의 key로 된 컬럼명이 같을땐 타입이 같아야한다.
							 ,@ModelAttribute MvcBoardMasterVO bmVO
							 ,@ModelAttribute MvcBoardSubVO bsVO
							  ) {
		logger.info("bfile.getOriginalFilename() : "+bfile.getOriginalFilename());
		
	//────────────────[ 파일첨부 시작 ]────────────────────────────────────────────────────────
		//파일형태로 넘어온 객체에서 이름을 읽고 문자열로 변환
		String fileName = bfile.getOriginalFilename();
		String path = "E:\\JLGit\\Basic_SpringFramework\\spring_board\\WebContent\\pbs\\";
		if(bfile!=null) {
			File file = new File(path+fileName);
			try {
				byte[] bytes = bfile.getBytes();
				BufferedOutputStream bos			//단독구현이 불가능한 필터클래스
					= new BufferedOutputStream(		//필터스트림
						new FileOutputStream(file));//안쪽에 있는게 실제생성스트림
				bos.write(bytes);//쓰기처리
				bos.close();
				long size = file.length();//파일크기 처리
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		bsVO.setB_file(fileName);//변환된 문자열(String)타입의 파일이름을 저장함 ( 이게 컬럼에 들어가야하는 내용 )
	//────────────────[ 파일첨부 끝 ]──────────────────────────────────────────────────────────
		logger.info("첨부파일 진행됨");
		
		
		//DB연동
		int result = 0;
		result = boardLogic.cudBoard(bmVO,bsVO);	
		
		return "redirect:list.jsp";
	}
}