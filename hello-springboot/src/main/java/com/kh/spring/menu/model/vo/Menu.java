package com.kh.spring.menu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
	
	private int id;
	private String restaurant;
	private String name;
	private int price;
	private MenuType type; // kr, ch, jp -> 보통 상수로 처리.
	private String taste; // mild, hot

}
