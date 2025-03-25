package edu.kh.jdbc.test.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TestTemplate {
	
	//Connection 객체 생성해서 반환하는메서드
	//commit 메서드
	//rollback 메서드
	//Connection close 메서드
	//Statement close (PreparedeStatement 도다형성때문에 close 가능함) 메서드
	//ResultSet close 메서드
	
	
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
	
	/** 호출시 Connection 객체를 생성해서 반환하는 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		
		try {
			
			
			// 이전에 참조하던 Connection 객체가 존재하고
			// 아직 close() 된상태가 아니라면
			// 새로 만들지 않고 기존 Connection 반환
			
			if (conn !=null && !conn.isClosed()) {
				return conn;
				
			}
			
			// 1. Properties 객체 생성
			
			Properties prop = new Properties();
			
			
			//2. Properties 가 제공하는 메서드를 이용해서 driver.xml 파일 내용을 읽어오기
			
			String filepath = "testdriver.xml";
			prop.loadFromXML(new FileInputStream(filepath));
			
			//3. prop 에 저장된 값을 이용해서 
			// Connection 객체 생성
			Class.forName(prop.getProperty("driver"));
			
			//conn = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:Xe",
			//                                           "kh" , "kh1234" )
			conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("userName"),
					                           prop.getProperty("password")              );
			
			//4. 만들어진 Connection 에서 AutoCommit 끄기
			conn.setAutoCommit(false);	
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		// 이전에 참조하던 Connection 객체가 존재하고
					// 아직 close() 된상태가 아니라면
					// 새로 만들지 않고 기존 Connection 반환
		
		return conn;
		
		
		
		
	}
	

	
	/** 전달받은 conn 을 commit 하는 static 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
		// 전달받은 커넥션이 null 이아니고 닫혀잇지 않으면 
		// commit 하기
		
		try {
			if (conn != null && !conn.isClosed()) {
				 conn.commit();
			}
		} catch (Exception e) {
			System.out.println("커밋중예외발생");
			e.printStackTrace();
		}
		
	}

	/**전달받은 conn 을 rollback 하는 static 메서드
	 * 
	 */
	public static void rollback(Connection conn) {
		
		try {// 전달받은 conn이 null이아니고 닫혀잇지 않다면
			// rollback!
			if(conn != null && !conn.isClosed()) 
			conn.rollback();
				
			
		}catch (Exception e) {
			System.out.println("롤백중 예외발생!");
			e.printStackTrace();
		}
			
		}


	/** 전달받은 conn을 close 하는 메서드
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		
	   try {
		   if (conn != null && !conn.isClosed()) {
			   conn.close();
		   }
		   
	   }catch (Exception e) {
		System.out.println("conn close 중 예외발생");
		   e.printStackTrace();
		
	}
	

	}

	
	/** Statement close 하는 메서드
	 * @param stmt
	 */
	public static void close (Statement stmt) {
		
		try {
			if (stmt != null && !stmt.isClosed()) stmt.close();
			
		}catch (Exception e) {
			System.out.println("Statment close 중 예외발생");
			e.printStackTrace();
		}
		
	}
	
	
	/**rs close 하는 메서드
	 * @param rs
	 */
	public static void close (ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed() )rs.close();
			
		}catch (Exception e) {
			System.out.println("rs close중 예외발생");
			e.printStackTrace();
		
	}

	}
	


}





