package com.mvc.board;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MemberLogic {
	Logger logger = Logger.getLogger(MemberLogic.class);
	
	public List<Map<String,Object>> getMemberList(Map<String,Object> pMap){
		logger.info("MemberLogic - getMemberList");
		//만일 게시판관리 업무에서 회원관리업무 메소드를 사용하고 싶다 어떡하지?
		
		return null;
	}
	
}
