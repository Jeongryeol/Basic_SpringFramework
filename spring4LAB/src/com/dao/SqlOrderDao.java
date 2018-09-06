package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vo.OrderVO;

@Repository(value="sqlOrderDao")//mapperInterface를 이용해서 구현체없이 구현하기
public interface SqlOrderDao {//메소드이름과 sqlMapperXML문서의 id를 일치시켜야 스프링이 이해합니다.
	//주문조회
	public List<OrderVO> getOrderList(Map<String,Object> pMap);
	//주문등록
	public int orderInsert(Map<String,Object> pMap);
	//주문수정
	
	//주문삭제
}
