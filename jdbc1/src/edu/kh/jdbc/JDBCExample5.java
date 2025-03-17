package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		// 아이디 ,비밀번호 , 이름을 입력받아
		//TB_USER 테이블에 삽입 (INSERT) 하기
		
		
		/*
		 * java.sql.PreparedStatment
		 * - SQL 중간에 ? (위치홀더,placeholder) 를 작성하여
		 *  ? 자리에 java 값을 대입할 준비가 되어있는 Statement
		 *  
		 *  장점 1 : SQL 작성이 간단해짐
		 *  장점 2 : ? 에 값 대입시 자료형에 맞는 형태의 리터럴으로 대입됨!
		 *  		ex) String 대입 -> '값' (자동으로 '' 추가)
		 *          ex) int 대입   -? 값 
		 *  장점 3 : 성능, 속도에서 우위를 가지고 있음
		 *  
		 *   ** PreparedStatment 는 Statement 자식 **
		 *    Statement - > SELECT / DML 다됨
		 *    PreparetStatment - > SELECT / DML 다됨
		 * 
		 * */
		
		//1. JDBC 객체 참조 변수선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		//SELECT 가 아니기 때문에 ResultSet 은 필요가 없슴메
		Scanner sc = null;
		
		try {
			//2. DriverManager 이용해서 Connection 객체 생성 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String str ="jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pass = "kh1234";
			
			conn = DriverManager.getConnection(str,user,pass);
			
			//3. SQL 작성
			sc = new Scanner(System.in);
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("이름 입력 : ");
			String name = sc.nextLine();
			
			String sql = """
					INSERT INTO TB_USER 
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT )
					""";
			
			//4. PreparedStatement 객체 생성
			// - > 객체 생성과 동시에 SQL이 담겨지게됨
			//-> 미리 ?(위치홀더)에 값을 받을 준비를 해야되기 때문에
			
			pstmt=conn.prepareStatement(sql);
			// prepareStatement 는 SQL 문을 바로 받아야 되기때문에 () 안에 SQL구문 변수를 넣어준다
			
			//5. ? 위치홀더 알맞은 값대입
			// pstmt.set자료형(?순서, 대입할값)
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3,name);
			// - > 여기까지 작성하면 SQL 완료된 상태!!
			
			// +DML 수행전에 해줘야 할것!
			// AutoCommit 끄기!
			// -> 왜 끄는건가 ? 개발자가 트랜잭션을 마음대로 제어하기 위해서
			conn.setAutoCommit(false); // 이게 오토커밋을 끈거
			
			// 6. SQL(INSERT) 수행후 결과(int) 반환받기
			// executeQuery(): SELECT 수행후 , ResultSet 반환
			// executeUpdate(): DML 수행후 , 결과 행 갯수 (int) 반환
			// -> 보통 DML 실패 0, 성공시 0초과된값이 반환됨.
			
			// pstmt 에서 executeQuery() , excecuteUpdate() 매개변수 자리에 아무것도 없어야한다!
			int result =pstmt.executeUpdate();

			//7. result 값에 따른 결과처리 + 트랜잭션 제어처리
			
			if (result > 0 ) {// Insert 성공시
				System.out.println(name + "님이 추가되었습니다.");
				conn.commit(); // COMMit 수행 -> DB에 INSERT 영구반영
				
				
			}else {// 실패
				System.out.println("추가 실패");
			    conn.rollback(); // 실패시 롤백해서 transaction 에 있는것을 버려버림.
			}
			
			
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt != null)pstmt.close();
				if (conn != null)conn.close();
				if (sc != null)sc.close();
			}catch (Exception e) {
				
			}
		}
		
	
		
		
		
		
	}
}
