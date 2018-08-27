package com.di;
/**
 * [[ 스프링을 사용하는 목적 ]]
 * 하나.	클래스간의 결합도를 낮춰주는 코딩을 전개할 수 있다.
 * 		결합도를 낮춰주는 코드는 인터페이스나 추상클래스 중심으로 코딩을 전개할 때 누릴 수 있다.
 * 둘.	초보개발자와 숙련자 사이의 갭을 줄일 수 있다.
 * 		일관성 있는 구조작업이 가능해진다.
 */
public interface HelloBean {
	//bean을 제공할 인터페이스에서 인사를 하는 형태를 생성
	public String getGreeting(String msg);
}
