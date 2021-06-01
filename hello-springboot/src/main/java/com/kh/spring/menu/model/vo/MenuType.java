package com.kh.spring.menu.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuType {
	
	KR("kr"), CH("ch"), JP("jp");
	
	private String value;
	
	/**
	 * enum 생성자의 접근제한자는 private
	 * 외부에서 접근/생성 불가
	 */
	MenuType(String value){
		this.value = value;
	}
	
	@JsonValue
	//getter
	public String getValue() {
		return this.value;
	}
	
	//setter
	public static MenuType menuTypeValueOf(String value) {
		switch(value) {
		case "kr" : return KR;
		case "ch" : return CH;
		case "jp" : return JP;
		default:
			throw new AssertionError("Unknown MenuType : " + value);
		}
	}

}
