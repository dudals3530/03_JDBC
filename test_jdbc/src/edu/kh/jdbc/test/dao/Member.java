package edu.kh.jdbc.test.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
	
	private int memberNo;
	private String memberName;
	private String memberId;
	private String memberPw;
	
	
}
