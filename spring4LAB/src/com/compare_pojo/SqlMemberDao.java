package com.compare_pojo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

public class SqlMemberDao {
	Logger logger = Logger.getLogger(SqlMemberDao.class);

	//ORM Solution Layer 객체주입 코드 추가 :: MyBatis를 'XML문서에 등록된 객체'로 주입
	public SqlSessionTemplate sqlSessionTemplate = null;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Map<String, Object>> getMemberList(Map<String, Object> pMap) {
		logger.info("getMemberList 호출 성공");
		List<Map<String, Object>> memberList = null;
		memberList = sqlSessionTemplate.selectList("getMemberList", pMap);
		return memberList;
	}
	
}
