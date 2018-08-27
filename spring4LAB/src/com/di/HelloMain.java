package com.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Spring Framework를 통해 객체를 주입받는 방식에 대한 스터디를 실행하는 클래스
 * 스프링이 객체를 주입하는 방법은 다음 2가지이다.
 * 	1)생성자 객체 주입
 * 	2)setter메소드 객체 주입
 */
public class HelloMain {
	/*
	 * 스프링 프레임워크를 이용한 Bean 등록방법	[ http://denodo1.tistory.com/188 ]
	 * 	1) xml 이용하기 : setter + BeanFactory
	 * 	2) xml 이용하기 : setter + 생성자 이용하기
	 * 	3) 어노테이션 이용하기 : 직접 Bean 등록
	 * 	4) 어노테이션 이용하기 : Component-Scan 
	 */
	public static void main(String[] args) {
		/** 1번 방법(1) : xml + 구현체클래스 setter 메소드 */
			//xml문서로부터  ApplicationContext 객체를 주입받음
		String conPath = "com\\di\\helloBean.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(conPath);
			//Bean객체 등록하기 : <bean id="helloBean" class="com.di.HelloBeanImpl">
		HelloBean bean1 = (HelloBean)context.getBean("helloBean");
		String insa = bean1.getGreeting("xml setter를 이용해서 Bean 객체 주입하기	: 안녕");
			//출력하기
		System.out.println(insa);
		System.out.println("===============================================================");
		
		/** 1번 방법(2) : xml + 구현체클래스 setter 메소드 + BeanFactory */
			//Xml묺서로부터 BeanFactory 객체를 주입받음
		String path_win = "E:\\dev_spring201804\\spring4LAB\\src\\com\\di\\helloBean.xml";
		String path_mac = "src/com/di/helloBean.xml";
		Resource resource = new FileSystemResource(path_mac);
		BeanFactory factory = new XmlBeanFactory(resource);
			//Bean객체 등록하기 : <bean id="helloBean" class="com.di.HelloBeanImpl">
		HelloBean bean2 = (HelloBean)factory.getBean("helloBean");
		String insa2 = bean2.getGreeting("xml factory를 이용해서 Bean 객체 주입하기	: 처음해보는");
			//출력하기
		System.out.println(insa2);
		System.out.println("===============================================================");
		
		/** 2번방법 : xml + 생성자 이용하기 */
			//xml문서로부터  ApplicationContext 객체를 주입받음
		String conPath2 = "com\\di\\helloBean.xml";
		ApplicationContext context2 = new ClassPathXmlApplicationContext(conPath);
			//생성자로 객체 이름을 주입하기 : xml문서에서 정보객체가 입력됨
		Car myCar = (Car)context2.getBean("myCar");
			//메소드로 출력하기
		System.out.println("나의 "+myCar.toString()+"		<< 생성자로 주입");
		
		Car herCar = (Car)context2.getBean("herCar");
		System.out.println("그녀의 "+herCar.toString()+"	<< 디펄트");
	}
	
}
