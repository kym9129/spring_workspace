package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Data
public class Member {
	
	private String id;
	private String password;
	private String name;
	private String gender;
	private Date birthday; //시분초정보 필요없어서 sql.Date, 필요하면 util.Date사용
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	private boolean enabled;	// 회원활성화 여부 (db:1/0, java:T/F)
}
