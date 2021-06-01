package com.kh.spring.board.model.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BoardExt extends Board {
	
	private boolean hasAttachment;
	
	private List<Attachment> attachList;

	public BoardExt(
			int no, String title, String memberId, 
			String content, Date regDate, int readCount,
			boolean hasAttachment) {
		super(no, title, memberId, content, regDate, readCount);
		this.hasAttachment = hasAttachment;
	}
	//mybatis는 기본생성자와 setter를 이용하므로 parameter생성자는 없어도 됨

	
}