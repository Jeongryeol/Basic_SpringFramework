package com.data_structure;

import java.util.List;

public class ListSetter {
	
	//반드시 null로 초기화
	List<String> listBean = null;
	
	//setter객체 주입코드 : 실질적인 제어역전을 이뤄주는 코드 
		//객체의 생성 및 생명주기(Lifecycle)에 대한 제어권이
		//개발자에게서 Spring엔진(spring container)로 넘어감을 의미
	public void setListBean(List<String> listBean) {
		this.listBean = listBean;
	}
	/* 
	 * 	[기존방식 : 개발자가 제어권을 가진 기존 코딩방식]
	 * 		자바기반의 어플리케이션을 개발할 때 자바 객체를 생성하고
	 * 		서로간의 의존관계를 연결시키는 작업에 대한 제어권은
	 * 		보통 개발되는 어플리케이션에 있음
	 * 
	 * 	[문제제기]
	 * 		독립적인 역할을 하는 컴포넌트 간의 결합도가 높아서(직접 인스턴스화 했기 때문에)
	 * 		컴포넌트의 확장(재사용)이 어렵다는 문제 발견됨
	 * 
	 * 	[문제해결 : 제어역전(IoC,Inversion of Control) 개념 제시 ]
	 * 		제어권이 Container(BeanFactory, ApplicationContext)에게 넘어가서
	 * 		객체의 생명주기를 Container가 전담하게 함
	 * 		예시 - Servlet, EJB(중량컨테이너..자동코드생산[에러발생대응어려움]) throw Exception
	 * 
	 * 		장점 - 컴포넌트 간의 결합도를 낮추어 컴포넌트의 재사용과 확장을 쉽게 함
	 * 			   컴포넌트의 의존관계 변경이 용이해짐
	 */
	
}
