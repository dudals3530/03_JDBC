package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {
   
	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
				
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
				
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부


		Connection conn= null;
		PreparedStatement pre = null;
		Scanner sc = null;
		ResultSet rs = null;
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String str ="jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pass = "kh1234";
			
			conn = DriverManager.getConnection(str,user,pass);
			conn.setAutoCommit(false);
			
			sc = new Scanner(System.in);
			
			System.out.print("조회할 성별(M/F) : ");
			String gen = sc.next().toUpperCase();
			
			String orderby = "";
			
			
			
		
			
			
			System.out.print("급여 범위(최소, 최대 순서로 작성) :");
			int sal1 = sc.nextInt();
			int sal2 = sc.nextInt();
			
		
			System.out.print("급여 정렬(1.ASC, 2.DESC) : ");
			int order = sc.nextInt();
			
		
			
			
			
			
		
			
			String sql = """
					 SELECT EMP_ID , EMP_NAME, DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F' )"성별" ,SALARY, JOB_NAME, 
                     NVL(DEPT_TITLE, '없음') DEPT_TITLE
                     FROM EMPLOYEE
                     JOIN JOB USING (JOB_CODE)
					 LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
					 WHERE DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F' ) = ?
					 AND SALARY BETWEEN ? AND ?
					 ORDER BY SALARY """;
					
			if (order ==1) sql += " ASC";
			else           sql += " DESC";
					
					
			
			pre = conn.prepareStatement(sql);
			pre.setString(1, gen );
			pre.setInt(2, sal1);
			pre.setInt(3, sal2);
			
			
			rs = pre.executeQuery();
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명 ");
			System.out.println("--------------------------------------------------------");
			
			boolean flag = true; // true : 조회결과가 없음 , false 조회결과가 존재함!
			
			
			while (rs.next()){
					flag = false;
				    //                  컬럼명, 별칭 , 조회된 컬럼순서 다 가능!
					String empId = rs.getString(1);
			        String empName = rs.getString(2);
			        String gender = rs.getString(3);
			        int salary = rs.getInt(4);
			        String jobName = rs.getString(5);
			        String deptTitle = rs.getString(6);
					
			        
				
				
			System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",empId,empName,gender,salary,jobName,deptTitle);	
			}
			
			if (flag) { 
				System.out.println("조회결과없음");
			}
			
			
			
		}catch (Exception e) {
			// 아무것도 안적혀 있으니까 class not found exception이 출력이 안됬습니다...
		}finally { 
			
			try {
				
				if (rs != null) rs.close();
				if (pre != null) pre.close();
				if (conn != null) conn.close();
				if (sc!=null) sc.close();
				
			}catch (Exception e) {
				
			}
		}
		
		
		
		
	}
}
