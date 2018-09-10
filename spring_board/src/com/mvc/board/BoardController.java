package com.mvc.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/spbd")//모든 매핑에 선행매핑 추가하기
public class BoardController {
	Logger logger = Logger.getLogger(BoardController.class);
	
	//setter객체주입법을 사용하는 경우라면 개발자가 그 이름을 xml 문서에 등록할 수 있지만,
	//Autowired를 사용할 때는 xml문서에 추가 등록자체가 필요 없기 때문에 클래스이름을 반드시 따라서 낙타표기법으로 선언함
	@Autowired//클래스이름을 반드시 따라서 낙타표기법으로 선언해야 합니다.
	private BoardLogic boardLogic = null;
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
		boardList = boardLogic.getBoardList(pMap);
		
		//쿠키세팅하기
		int ctotal = (int)boardList.get(0).get("total");
		Cookie c = new Cookie("ctotal", String.valueOf(ctotal));
		c.setMaxAge(60*60*24);//시간
		res.addCookie(c);
		
		//boardList = bLogic.getBoardList(pMap);//setter메소드방식일때의 코드
		mod.addAttribute("getBoardList", boardList);//화면에 넘길 모델에 속성을 저장함
		//return "board/list2";
		return "forward:jsonBoardList.jsp";
	}
	
	//jsp에서 UI Solution을 통해서 스프링을 경유하도록 한다.
	@RequestMapping("writeForm.spbd")
	public String writeForm(){
		return "redirect:writeForm.jsp";
	}
}









