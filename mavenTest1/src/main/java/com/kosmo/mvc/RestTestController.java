package com.kosmo.mvc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vo.ResVO;
/****************************************************************************************
 * @Controller & @RestController
 *	기존 컨트롤러가 응답으로 화면을 처리한다면,
 *	RestController는 응답으로 화면이 아닌 JSoN, 문자열, XML을 반환합니다.
 *	이것을 사용하면 Gson과 같은 별도의 API로 jsonXXX.jsp문서를 만들지 않더라도 자동으로 처리받을 수 있습니다.
 ****************************************************************************************/
@RestController//화면이 아닌 데이터타입을 리턴하는 컨트롤러 (4.0이후 신규로 추가된 어노테이션!!)
@RequestMapping(value="/foodMap")
			   //,produces="text/plain;charset=UTF-8")//텍스트방식에 대한 임시 한글처리
public class RestTestController {
	private static final Logger logger = Logger.getLogger(RestTestController.class);
	
	
	//3.0버전 이하에 대해, 혹은 기존 컨트롤러에 대응할때 @ResponseBody
	//■■■■■■■■■■■■■[텍스트타입 리턴/ResponseBody적용]■■■■■■■■■■■■■
	@RequestMapping("resName2")
	public @ResponseBody String resName2() {//음식점 이름을 리턴하는 메소드
		logger.info("	[알림] resName2() 호출 성공");
		String rname = "골목식당";
		return rname;
		//화면이 아닌, 음식점 이름 하나를 리턴
		//필터클래스 이용해서 한글처리합시다.
	}
	
	
	//■■■■■■■■■■■■■[텍스트타입 리턴]■■■■■■■■■■■■■
	@RequestMapping("resName")
	public String resName() {//음식점 이름을 리턴하는 메소드
		logger.info("	[알림] resName() 호출 성공");
		String rname = "골목식당";
		return rname;
		//화면이 아닌, 음식점 이름 하나를 리턴
		//필터클래스 이용해서 한글처리합시다.
	}
	
	
	//■■■■■■■■■■■■■■[VO타입 리턴]■■■■■■■■■■■■■■
	@RequestMapping("resVO")
	public ResVO resVO() {
		logger.info("	[알림] resVO() 호출 성공");
		ResVO rVO = new ResVO();
		rVO.setRname("골목식당");
		rVO.setRaddr("가산 디지털단지...");
		rVO.setRtel("02-0000-1111");
		return rVO;
	}
	
	
	//■■■■■■■■■■■■■■[JSON타입 리턴/ResponseBody적용]■■■■■■■■■■■■■■
	@RequestMapping("resList2")
	public @ResponseBody List<ResVO> resList2() {
		logger.info("	[알림] resList2() 호출 성공");
		List<ResVO> resList = new ArrayList<>();
		ResVO rVO = new ResVO();
		rVO.setRname("골목식당");
		rVO.setRaddr("가산 디지털단지...");
		rVO.setRtel("02-0000-1111");
		resList.add(rVO);
		rVO = new ResVO();
		rVO.setRname("골목식당2");
		rVO.setRaddr("가산 디지털단지...2");
		rVO.setRtel("02-1111-2222");
		resList.add(rVO);
		rVO = new ResVO();
		rVO.setRname("골목식당3");
		rVO.setRaddr("가산 디지털단지...3");
		rVO.setRtel("02-3333-4444");
		resList.add(rVO);
		return resList;
	}
	
	
	//■■■■■■■■■■■■■■[JSON타입 리턴]■■■■■■■■■■■■■■
	@RequestMapping("resList")
	public List<ResVO> resList() {
		logger.info("	[알림] resList() 호출 성공");
		List<ResVO> resList = new ArrayList<>();
		ResVO rVO = new ResVO();
		rVO.setRname("골목식당");
		rVO.setRaddr("가산 디지털단지...");
		rVO.setRtel("02-0000-1111");
			resList.add(rVO);
		rVO = new ResVO();
		rVO.setRname("골목식당2");
		rVO.setRaddr("가산 디지털단지...2");
		rVO.setRtel("02-1111-2222");
			resList.add(rVO);
		rVO = new ResVO();
		rVO.setRname("골목식당3");
		rVO.setRaddr("가산 디지털단지...3");
		rVO.setRtel("02-3333-4444");
			resList.add(rVO);
		return resList;
	}
	
}
