package com.kh.spring.websocket.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * javascript객체와 맞아야 함
 * 	const msg = {
		from : "${loginMember.id}",
		to : url eq "admin/notice" ? "all" : "honggd",
		message : $message.val(),
		type : "NOTICE",
		time : Date.now()
	};
 *
 */
@Data
@NoArgsConstructor
public class Notice {
	private String from;
	private String to;
	private String message;
	private String type;
	private long time;
}
