package com.kosmo.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 차트, 데이터 등을 처리를 전담하는 RestController Layer
 * 활용분야 : 챠트처리 , OpenAPI활용하서 Naver, Google에서 제공하는 데이터를 받아서 처리할 때 유용
 * 		 ,통계자료(국세청, 노동청, 건강보험, 산재보험, 기상청..... 등 데이터처리를 목적으로 만드는 통계정보)
 */
@RestController
@RequestMapping("/mv_board") //패턴을 맞추고나면 폴더 생성할 것.(Maven : src/main/webapp 하위  | 생성스프링 : Webcontent하위)
public class RestBoardContoller {
	//Maven에서 제공하는 Logger를 사용할 경우, (org.slf4j.LoggerFactory)
	//  src/main/resources/log4j.xml 에 logger Application 등록할 것
	private static final Logger logger = LoggerFactory.getLogger(RestBoardContoller.class);
	
	@Autowired
	private BoardLogic boardLogic = null;
	
	//■■■■■■■■■■■■■■[JSON타입 리턴/RestBoardController에서 적용]■■■■■■■■■■■■■■
	@RequestMapping("getBoardList")
	public List<Map<String,Object>> getBoardList(
			@RequestParam	 Map<String,Object> pMap
							,Model mod
							,HttpServletResponse res
			){
		System.out.println("왔긴했나?");
		logger.info("RestCONTROLLER-getBoardList 호출성공");
		logger.info("pMap = "+pMap);
		List<Map<String,Object>> boardList = null;
		boardList = boardLogic.getBoardList(pMap,res);
		mod.addAttribute("getBoardList", boardList);//화면에 넘길 모델에 속성을 저장함
		logger.info("─────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("이제가니?");
		return boardList;
	}
	
}
