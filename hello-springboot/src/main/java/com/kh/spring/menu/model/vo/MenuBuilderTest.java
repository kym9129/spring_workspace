package com.kh.spring.menu.model.vo;

public class MenuBuilderTest {

	public static void main(String[] args) {
		MenuBuilderTest mt = new MenuBuilderTest();
		mt.test1();

	}
	
	private void test1() {
		// 1. 기본생성자 + setter
		// 2. 파라미터 생성자
		// 3. Builder패턴 : vo객체에서 lombok @Builder로 사용 가능
		Menu m = 
			Menu.builder()
					.id(1)
					.name("도토리묵")
					.restaurant("다람쥐네")
					.price(8000)
					.build();
	}

}
