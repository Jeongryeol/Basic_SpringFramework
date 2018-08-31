package com.compare_pojo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MemberLogic {
	Logger logger = Logger.getLogger(MemberLogic.class);

	//Dao계증에 대한 객체주입이 필요함
	public SqlMemberDao sqlMemberDao = null;
	public void setSqlMemberDao(SqlMemberDao sqlMemberDao) {
		this.sqlMemberDao = sqlMemberDao;
	}

	//Logic의 실제 업무(DAO와 연계되도록)
	public List<Map<String, Object>> getMemberList(Map<String, Object> pMap) {
		logger.info("getMemberList 호출성공");
		//Dao계층의 메소드 호출 +DB조회결과를 리턴받음(타입체크필수!!)
		List<Map<String,Object>> memberList = null;
		memberList = sqlMemberDao.getMemberList(pMap); 
		//DB조회결과를 반환
		return memberList;
	}
	
	
}
