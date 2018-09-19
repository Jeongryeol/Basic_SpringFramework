package com.kosmo.board;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vo.BoardMasterVO;
import com.vo.BoardSubVO;

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
	//상세보기가 되었을때, 조회수가 카운트되어야함.
	@RequestMapping("getBoardDetail")
	public String getBoardDetail(@RequestParam Map<String, Object> pMap, Model mod) {
		logger.info("getBoardDetail 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = boardLogic.getBoardDetail(pMap);

		mod.addAttribute("boardList",boardList);
		return "forward:read.jsp";//화면에 리턴하기때문에 Controller에 있어야함.
	}
	
	//새글쓰기
	@RequestMapping("boardInsert")
	public String boardInsert(@RequestParam("bfile") MultipartFile bfile
			                , @ModelAttribute BoardMasterVO bmVO
			                , @ModelAttribute BoardSubVO bsVO)
	{
		String fileName = bfile.getOriginalFilename();
		String path = "E://JLGit//Basic_SpringFramework//mavenTest1//src//main//webapp//pbs//";
		double dsize = 0.0;
		long size = 0;
		if(bfile!=null) {
			File file = new File(path+fileName);
			try {
				byte[] bytes = bfile.getBytes();
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
		//DB연동 처리
		int result = 0;
		bsVO.setB_file(fileName);
		bsVO.setB_size(dsize);
		bmVO.setB_name(bmVO.getB_name());
		bmVO.setB_title(bmVO.getB_title());
		bmVO.setB_content(bmVO.getB_content());
		result = boardLogic.cudBoard(bmVO, bsVO);
		return "redirect:list.jsp";//꼭 확인
	}
	
	//수정하기
	@RequestMapping("boardUpdateForm")
	public String boardUpdateForm(@RequestParam Map<String, Object> pMap, Model mod) {
		logger.info("boardUpdateForm 호출 성공");
		List<Map<String, Object>> boardList = null;
		boardList = boardLogic.getBoardDetail(pMap);
		
		mod.addAttribute("boardList",boardList);
		return "forward:updateForm.jsp";//화면에 리턴하기때문에 Controller에 있어야함.
	}
	
	//수정 처리하기(저장)
	@RequestMapping("boardMUpdate")
	public String boardMUpdate(@RequestParam Map<String, Object> pMap
							  ,@RequestParam("b_file") MultipartFile b_file) {
		logger.info("boardMUpdate 호출 성공");
		int result = 0;
		result = boardLogic.boardMUpdate(pMap, b_file);
		return "redirect:list.jsp";//수정이 완료된 후 목록화면으로 돌아가면 끝
	}
	
	//삭제처리하기
	@RequestMapping("boardDelete")
	public String boardDelete(@RequestParam Map<String, Object> pMap) {
		logger.info("boardDelete 호출 성공");
		int result = 0;
		//삭제해야하는 파일과 경로 준비
		String path = "E://JLGit//Basic_SpringFramework//mavenTest1//src//main//webapp//pbs//";
		String fileName = pMap.get("b_file").toString();
		String fullPath = path + fileName;
		File file = new File(fullPath);//파일명으로 객체 생성
		//삭제할 파일이 존재하면 삭제실행
		if(file.exists()) {
			boolean isDelete = file.delete();
			logger.info("파일삭제여부 : "+isDelete);//true:삭제성공 | false:삭제실패
		}
		//글삭제
		result = boardLogic.boardDelete(pMap);
		
		return "redirect:list.jsp";//수정이 완료된 후 목록화면으로 돌아가면 끝
	}
}
