package edu.kh.jdbc;

import java.security.DrbgParameters.NextBytes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {
		
		// 부서명을 입력받아
		//해당부서에 근무하는 사원의
		//사번, 이름,부서명, 직급명을
		//직급코드 오름차순으로 조회
		
		//[실행화면]
		// 부서명 입력 : 총무부
		// 200/ 선동일 / 총무부/ 대표
		// 202 / 노옹철 / 총무부 / 부사장 
		// 201 / 송종기 / 총무부 / 부사장
		
        // 부서명 입력 : 개발팀
		// 일치하는 부서가 없습니다!
		
		// hint : SQL 에서 문자열은 양쪽 '' (홑따옴표) 필요
		// ex) c총무부 입력 - > '총무부'
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; 
		Scanner sc = null;
		
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String str ="jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pass = "kh1234";
			
			conn = DriverManager.getConnection(str,user,pass);

			System.out.print("부서명 입력 : ");
			sc = new Scanner(System.in);
			String input = sc.next();
		
			
			
			
			
			
			String sql = "	SELECT EMP_ID , EMP_NAME, DEPT_TITLE, JOB_NAME "
					+ "	FROM EMPLOYEE "
					+ "	JOIN JOB USING (JOB_CODE) "
					+ "	JOIN DEPARTMENT ON (DEPT_CODE = DEPT_CODE) "
					+ "	WHERE DEPT_TITLE = '" + input +"' "
					+ "  ORDER BY JOB_CODE";
			
			stmt = conn.createStatement();
			rs= stmt.executeQuery(sql);
			boolean flag = true;
				
				while(rs.next()) {
				
				String id = rs.getString("EMP_ID");
				String name = rs.getString("EMP_NAME");
				String title = rs.getString("DEPT_TITLE");
				String job = rs.getString("JOB_NAME");
				flag = false;
				System.out.printf("%s / %s / %s / %s\n",id,name,title,job);
				}
			if(flag) {
				System.out.println("일치하는 부서가 없습니다!");
			}
			
			
			// return 이용법 
		//	if(!rs.next()) {// 조회 결과가 없다면
	               //     System.out.println("일치하는 부서가 없습니다!");
	                 //   return;
		//	}
                        //return 구문을 만나게되면 아랫단에 있는 구문들은 실행안하지만!
			            // but!!!! finally 구문은 실행함!
			/*
			 * do {String id = rs.getString("EMP_ID");
				String name = rs.getString("EMP_NAME");
				String title = rs.getString("DEPT_TITLE");
				String job = rs.getString("JOB_NAME");
				System.out.printf("%s / %s / %s / %s\n",id,name,title,job);
				}
				
			 * */
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			
				try {
					if(rs != null)rs.close();
					if(stmt != null)stmt.close();
					if (conn!=null)conn.close();
				
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
	}
		
		
}


