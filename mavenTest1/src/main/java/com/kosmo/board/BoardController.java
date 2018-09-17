package com.kosmo.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//화면처리를 목적으로 하는 Controller Layer
@Controller
@RequestMapping("/mv_board") //패턴을 맞추고나면 폴더 생성할 것.(Maven : src/main/webapp 하위  | 생성스프링 : Webcontent하위)
public class BoardController {
	//Maven에서 제공하는 Logger를 사용할 경우, (org.slf4j.LoggerFactory)
	//  src/main/resources/log4j.xml 에 logger Application 등록할 것
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardLogic boardLogic = null;
	
	//■■■■■■■■■■■■■■[JSON타입 리턴/ResponseBody적용]■■■■■■■■■■■■■■
	@RequestMapping("getBoardList2")
	public @ResponseBody List<Map<String,Object>> getBoardList2(
				@RequestParam	 Map<String,Object> pMap
								,Model mod
								,HttpServletResponse res
				) {
			System.out.println("왔긴했나?");
			logger.info("CONTROLLER-getBoardList2 호출성공");
			logger.info("pMap = "+pMap);
			List<Map<String,Object>> boardList = null;
			boardList = boardLogic.getBoardList(pMap,res);
			mod.addAttribute("boardList", boardList);//화면에 넘길 모델에 속성을 저장함
			logger.info("─────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("이제가니?");
			return boardList;
	}
	
	//상세보기 구현
	@RequestMapping("getBoardDetail")
	public String getBoardDetail(@RequestParam Map<String, Object> pMap
								//pMap을 한줄만 읽었으므로 dao클래스의 재사용이 가능함.
			,Model mod
			,HttpServletResponse res) {
		logger.info("getBoardDetail 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = boardLogic.getBoardList(pMap,res);

		mod.addAttribute("boardList",boardList);
		return "forward:read.jsp";//화면에 리턴하기때문에 Controller에 있어야함.
	}
}
