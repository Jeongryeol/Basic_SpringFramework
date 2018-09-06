package com.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dao.SqlOrderDao;
import com.vo.OrderVO;

@Service//안쓰면.. : org.springframework.beans.factory.UnsatisfiedDependencyException
public class OrderLogic {
	Logger logger = Logger.getLogger(OrderLogic.class);
	
	//구현체클래스없이 인터페이스를 통해서 객체를 주입하는 방식 (하이버네이트와 유사)
	@Resource(name="sqlOrderDao")
	public SqlOrderDao sqlOrderDao = null;
	
	public List<OrderVO> getOrderList(Map<String, Object> pMap) {
		logger.info("getOrderList 호출성공");
		List<OrderVO> orderList = null;
		orderList = sqlOrderDao.getOrderList(pMap);
		logger.info("size : "+orderList.size());
		return orderList;
	}
	
	public int orderInsert(Map<String,Object> pMap) {
		int result =0;
		//단위테스트 : 샘플데이터 입력하는 코드
			pMap.put("ord_no",1004);
			pMap.put("oname","테스터");
			pMap.put("odate","2018-09-14");
		result = sqlOrderDao.orderInsert(pMap);
		logger.info("result : "+result);
		return result;
	}

}