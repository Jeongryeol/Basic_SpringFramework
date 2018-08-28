package com.data_structure;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ListMain {

	public static void main(String[] args) {
		//ListBean.xml -->> Bean -->> List<String>
		String conPath = "com\\data_structure\\ListBean.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(conPath);
		ListSetter listTest = (ListSetter)context.getBean("listTest");//bean id로부터 ~ bean객체 생성
		List<String> ltest = listTest.listBean;//property name으로부터 ~ List객체 생성
		
		//출력
		for(String msg:ltest) {
			System.out.println(msg);
		}
	}
	
}
