package com.other;

public class A {
	//생성자 주입법 예제
	private String name = "이순신";
	public A() {}
	public A(String name) {
		this.name = name;
	}
	public A(B prop2) {
		this.prop1 = prop2;
	}
	
	//[권장사항]setter 메소드 주입법 예제
	private B prop1 = null;
	public void setProp1(B prop1) {
		this.prop1 = prop1;
	}
	
	public String toString() {
		return "나는 A클래스 타입입니다.";
	}
}
