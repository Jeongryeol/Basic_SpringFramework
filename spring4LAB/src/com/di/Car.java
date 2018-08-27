package com.di;

public class Car {

	String carName	= "Tivoli";
	String carColor	= "Black";
	int wheelNum	= 4;
	
	public Car() {}
	//생성자를 이용한 Bean객체 주입을 위해 준비한 생성
	public Car(String carName, String carColor, int wheelNum) {
		this.carName  = carName;
		this.carColor = carColor;
		this.wheelNum = wheelNum;
	}//setter 객체 주입법 코드 (java를 주로 활용하는 코드법)
	
	@Override
	public String toString() {
		return "자동차의 이름은 "+carName+"이고, 색상은 "+carColor+"이며, 바퀴수는 "+wheelNum+"입니다.";
	}
}
