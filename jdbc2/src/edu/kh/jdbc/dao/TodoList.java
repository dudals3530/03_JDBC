package edu.kh.jdbc.dao;

import java.sql.Date;

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
public class TodoList {
	
	private int memberNo;
	private String title;
	private int tdnum;
	private String details;
	private String status;
	private Date date;
	
}
