package com.util;

import java.io.File;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/*
 * 공통코드를 작성해 봅시다.
 * 1)파라미터가 필요한가? - yes(왜? )
 * 힌트:누가 언제 어떤 클래스에서 인스턴스화를 해야하는거지?
 *     어떤 시점에서 필요한 걸까?
 *     
 */
public class HashMapBinder {
	Logger logger = Logger.getLogger(HashMapBinder.class);
	HttpServletRequest req = null;
	//첨부파일 처리에 필요한 변수 선언
	MultipartRequest multi = null;
	String realFolder = "";
	//첨부파일의 한글처리
	String encType = "euc-kr";
	//첨부파일의 크기
	int maxSize = 5*1024*1024;//5MB
	
	public HashMapBinder(HttpServletRequest req) {
		this.req = req;
		//첨부파일이 실제로 배포될 물리적인 위치 - 톰캣서버내에 경로를 바라보니까 외부에서도 다운로드 가능함.
		realFolder = "E:\\dev_kosmo201804\\dev_jsp20180417\\WebContent\\pds";
	}
	//첨부파일 있는 경우 등록할 때 사용자가 입력한 값을 pMap에 담기
	public void multiBind(Map<String,Object> pMap){//외부에서 객체 생성이 되었다.
		pMap.clear();//NullPointerException
		try {
			multi = new MultipartRequest(req, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
		} catch (Exception e) {
			// TODO: handle exception
		}
		Enumeration<String> en = 
				multi.getParameterNames();
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			logger.info("key:"+key);
			pMap.put(key, HangulConversion.toKor(multi.getParameter(key)));
		}
		//첨부파일에 대한 정보를 받아오는 코드 추가
		Enumeration<String> files = multi.getFileNames();
		//이렇게 읽어온 파일이름을 객체로 만들어준다.
		File file = null;
		while(files.hasMoreElements()) {
			String fname = files.nextElement();
			String filename = multi.getFilesystemName(fname);
			pMap.put("b_file", filename);
			//file객체 생성하기 -> 왜냐하면 첨부파일의 크기를 알고 싶어요....
			file = multi.getFile(filename);
		}
		//첨부파일의 크기를 담을 수 있는 변수
		double size = 0;
		if(file != null) {
			size = file.length();//파일 크기가 저장 5.2MB
			size = size/(1024*1024);
			pMap.put("b_size", size);
		}
		
	}	
	public void bind(Map<String,Object> pMap){//외부에서 객체 생성이 되었다.
		pMap.clear();//NullPointerException
		Enumeration<String> en = 
				req.getParameterNames();
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			logger.info("key:"+key);
			pMap.put(key, HangulConversion.toKor(req.getParameter(key)));
		}
	}
}









