package edu.kh.jdbc.test.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class TestTemplate {
	/*
	 * Template : 양식 , 틀 ,주형
	 *  -> "미리 만들어둿따" 의미 
	 * 
	 * 	JDBCTemplate :
	 *  JDBC 관련 작업을 위한 코드를 
	 *  미리 작성해서 제공하는 클래스
	 *
	 * 	-Connection 생성
	 *  -AutoCommit false
	 *  -commit / rollback
	 *  -각종 자원 반환 close()
	 *  
	 *  *************** 중요 ****************
	 *  어디서든지 JDBCTemplate 클래스를
	 *  객체로 만들지 않고도 메서드를 사용 할수 있도록 하기위해
	 *  모든 메서드를 public static 으로 선언
	 *  -> 싱글톤 패턴 적용
	 *  
	 * */
	
        //  필드  
	private static Connection conn = null;
	
	// -> static 메서드에서 사용할 필드
	
		// 메서드
	
	public static Connection getConnection() {
		
		
		try {
			// 1. Properties 객체 생성
			
			Properties prop = new Properties();
			
			
			//2. Properties 가 제공하는 메서드를 이용해서 driver.xml 파일 내용을 읽어오기
			
			String filepath = "testdriver.xml";
			prop.loadFromXML(new FileInputStream(filepath));
			
			//3. prop 에 저장된 값을 이용해서 
			// Connection 객체 생성
			
			
			//conn = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:Xe",
			//                                           "kh" , "kh1234" )
			
	        //4. 만들어진 Connection 에서 AutoCommit 끄기
		}catch (Exception e) {
			// TODO: handle exception
		}
		// 이전에 참조하던 Connection 객체가 존재하고
					// 아직 close() 된상태가 아니라면
					// 새로 만들지 않고 기존 Connection 반환
		
		
		
		return conn;
		
		
	}
	
	
	
	
}
