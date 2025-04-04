package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {
	public static void main(String[] args) {
		
		/*
		 * 아이디 , 비밀번호 , 이름을 입력받아
		 * 아이디, 비밀번호 가 일치하는 사용자의
		 * 이름을 수정해라 (UPDATE)
		 * 
		 * 1. PreparedStatement 이용하기
		 * 2. commit//rollback 처리하기
		 * 3. 성공시 수정성공! 실패시 수정실패! 해주기
		 * */
		
		Connection conn= null;
		PreparedStatement pre = null;
		Scanner sc = null;
		
		try {
			 //2) Connection 객체 생성 (DriverManager를 통해서)
			  // 2-1  OracleDriver 메모리에 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2-2) DB 연결 정보 작성
			String str ="jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pass = "kh1234";
			
			conn = DriverManager.getConnection(str,user,pass);
			conn.setAutoCommit(false);
			
			sc = new Scanner(System.in);
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("변경할이름 입력 : ");
			String name = sc.nextLine();
			
			String sql = """
					UPDATE TB_USER 
					SET USER_NAME= ?
					WHERE USER_ID = ?     
					AND USER_PW = ?
					""";
					
			
			pre =conn.prepareStatement(sql);  
			                                
			
			pre.setString(1,name);
			pre.setString(2, id);
			pre.setString(3, pw);
			
			
			
			
			int r =pre.executeUpdate();
			
			
			if (r > 0) {
				System.out.println("수정완료");
				System.out.println(name +"님으로 수정되었습니다");
				conn.commit();
			}else {
				System.out.println("수정실패");
				conn.rollback();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(pre != null)pre.close();
				if(conn != null)conn.close();
				if (sc != null)sc.close();
					
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

}
