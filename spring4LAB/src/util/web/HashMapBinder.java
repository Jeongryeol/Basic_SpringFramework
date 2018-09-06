package util.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
/*************************************************************************************
 * [[ Request → Map변환 클래스 ]] 
 * @author	Jeongryeol
 * @comment	 요청객체에 담긴 내용을 Name:Value방식으로 Map에 담아주는 바인더 클래스
 *************************************************************************************/
public class HashMapBinder {
	HttpServletRequest req = null;//요청객체 준비
	//첨부파일 처리에 필요한 변수
	MultipartRequest multi = null;
	String realFolder = "";
	String encType = "UTF-8";//첨부파일의 한글처리
	int maxSize = 5*1024*1024;//5MB
	//생성자
	public HashMapBinder(HttpServletRequest req) {
		this.req = req;//요청객체(request)의 원본주소를 받아 저장해둠
		realFolder="";//각자서버의 위치경로 입력
	}
	//Web에서 보내진 요청객체의 자료를 토대로 (생성자 초기화)
	//DB에 보낼 Map계열 자료형에 옮겨담아주는 클래스  
	public void bind(Map<String,Object> pMap){
		pMap.clear();//자료형 초기화
		//Enumeration : 자바 초기버전에 개발된 인터페이스
		//객체들의 집합(Vector)의 객체들을 하나씩 순서대로 담아 처리할 수 있는 메소드를 제공하는 켈렉션
		Enumeration<String> en = req.getParameterNames();//name속성으로 된 모든 내용을 순서대로 담음
		while(en.hasMoreElements()) {//자료형 일괄저장
			String key = en.nextElement();		//에뉴멀레이션에 담긴 속성값(name)을 키(key)로 담음
			pMap.put(key,req.getParameter(key));//키(key)와 키에 따라 담고있는 값(value)을 Map에 담음
		}
	}
}
