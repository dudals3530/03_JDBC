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

@NoArgsConstructor
public class TestTodo {

private String memberNo; 	
private String title; // 제목
private int todoNo; //todo 숫자
private String details;
private Date setDate; //작성일	
private String status; //완료여부	
@Override
public String toString() {
	return " 제목 : " + title + " TODO의번호 : " + todoNo + "TODO세부적으로할일 : " + details
			+ "지정날짜 : " + setDate + "현재완료상태 (y/n)" + status + "]";
}
	
}
