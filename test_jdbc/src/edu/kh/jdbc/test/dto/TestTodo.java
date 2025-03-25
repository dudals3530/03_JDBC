package edu.kh.jdbc.test.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestTodo {

private String member_no; 	
private String title; // 제목
private int todoNo; //todo 숫자
private String details;
private Date setDate; //작성일	
private char status; //완료여부	
	

}
