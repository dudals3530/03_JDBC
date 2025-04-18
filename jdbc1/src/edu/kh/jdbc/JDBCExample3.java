package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {

	public static void main(String[] args) {
		
		//입력받은 최소 급여 이상
		// 입력받은 최대 급여 이하를 받는
		// 사원의 사번, 이름 , 급여를 급여 내림차순으로 조회
		
		//- > 이클립스 콘솔 출력
		
		// [실행화면]
        // 최소 급여 :
		// 최대 급여 :
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Scanner sc = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String str = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pass = "kh1234";
			
			conn =DriverManager.getConnection(str,user,pass);
			
			sc = new Scanner(System.in);
			
			System.out.print("최소 급여: " );
			int inputMin = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int inputMax = sc.nextInt();
			
			String sql = "  SELECT EMP_ID , EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY BETWEEN "+ inputMin+" AND "+inputMax
					+" ORDER BY SALARY DESC";
					
			String sql1 = """
					 SELECT EMP_ID , EMP_NAME, SALARY
					 FROM EMPLOYEE
					 WHERE SALARY BETWEEN """+ " "+ inputMin + " AND " +inputMax +
					  " ORDER BY SALARY DESC";
					
			
					
			//Statment 객체 생성
			stmt = conn.createStatement();
			// sql 쿼리문 statment 에 담아주고 그걸 rs 에 담아줌 
			rs = stmt.executeQuery(sql1);
			
			while (rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("Salary");
				
				System.out.printf("%s / %s / %d \n",empId,empName,salary);
			}
			
      			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
				try {
					if(rs != null)rs.close();
					if(stmt != null)stmt.close();
					if(conn != null)conn.close();
					if (sc != null)sc.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
		}
		
	}
}
