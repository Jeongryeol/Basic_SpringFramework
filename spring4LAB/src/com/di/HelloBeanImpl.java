package com.di;
/**
 * HelloBean 인터페이스를 구현하는 구현체클래스
 * 	구현체 클래스는 Impl이라는 이름으로 명명한다.
 * 
 * 객체주입방법은 다음과 같이 2가지가 있다.
 * 	1) 생성자 객체 주입법
 * 	2) setter 객체 주입법
 * 
 * Maven(메이븐)으로 실습하지 않는 이유 : 자동화 기능을 제공하는 메이븐은 다양한 오픈소스를 대응하기에 부족한 면이 있다.
 * 프로젝트마다 다양한 오픈소스를 사용하기 때문에 수동으로 스프링프레임웍을 쓰는 방식으로 교육을 진행한다.
 */
//역주입, 역제어, 의존성주입 등..
public class HelloBeanImpl implements HelloBean {
	
	//객체주입받을 자리생성 : xml로 부터 주입받을 객체를 빈값으로 생성해둠 ( 대상 xml :helloBean.xml )
	/* <property name="msg"> */
	String msg = null;

	
	//setter객체 주입법 코드 - 라이프사이클을 관리받는다
	//누가 관리하지? spring container[엔진] : spring-core.jar
		//엔진 : c언어로 구성됨 -->> 하드웨어접근 -->> C++로 발전
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	@Override//구현체 클래스가 구현되서 실핼될때 xml이 객체를 주입해줌.
	public String getGreeting(String msg) {
		return msg+", spring!!";
	}

}
