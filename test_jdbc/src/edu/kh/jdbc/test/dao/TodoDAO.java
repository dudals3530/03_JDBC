package edu.kh.jdbc.test.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.test.common.TestTemplate;
import edu.kh.jdbc.test.dto.TestTodo;

public class TodoDAO {

	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	
	public int signup(Connection conn, Member mem) {
		
			//SQL 수행중 발생하는 예외를
			// catch로 처리하지 않고, throws를 이용해서 호출부로 던져처리
				// -> catch 문 필요없다!
				
				//1 . 결과저장용 변수선언
				// 2 . SQL 작성
		//3. preparedStatement 작성
		//4. ? (위치홀더)알맞은 값 대입
		//5. SQL 수행후 결과 반환받기
		//6. 사용한 jdbc 객체 자원 반환 (close)
		// 결과저장용 변수에 저장된 값 반환
		int result = 0;
		
		try {
			String sql = """
	                 INSERT INTO MEMBER (
	                 MEMBER_NO, MEMBER_NAME, MEMBER_ID, MEMBER_PW
	                 ) VALUES (
	                 MEM_NO.NEXTVAL, ?, ?, ?
	                 )
	                """;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,mem.getMemberName() );
			pstmt.setString(2,mem.getMemberId() );
			pstmt.setString(3,mem.getMemberPw());
			
			result = pstmt.executeUpdate();
	
		}catch (SQLException e) {
			
			
			System.out.println("아이디 중복임!!");
			
			{
				
			}
		}finally {
			TestTemplate.close(pstmt);
		}
		return result;
		
		
		
	}




	public Member login(Connection conn, String inputId, String inputPw) throws Exception {
	
		Member mem = null;
		
		try {
			String sql = """
					SELECT * FROM MEMBER
					WHERE MEMBER_ID = ?
					AND MEMBER_PW = ? 
					""";
			 
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int no = rs.getInt("MEMBER_NO");
				String name = rs.getString("MEMBER_NAME");
				String id = rs.getString("MEMBER_ID");
				String pw = rs.getString("MEMBER_PW");
				
				mem = new Member(no,name,id,pw);
			}
			
		}finally {
			TestTemplate.close(rs);
			TestTemplate.close(pstmt);
		}
		
		return mem;
		
	}




	public List<TestTodo> selectTodo(Connection conn, int result) throws Exception {
		
		List<TestTodo> todoList = new ArrayList<>();
		
		try {
			
			String sql = """
					SELECT MEMBER_NO, MEMBER_NAME, TODO_NO,TITLE, DETAILS, STATUS, SET_DATE
                    FROM MEMBER
                    JOIN TODOLIST USING (MEMBER_NO)
                    WHERE MEMBER_NO = ?
					
					""";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, result);
			
			rs= pstmt.executeQuery();
			
			while (rs.next()) {
                String memberNo = rs.getString("MEMBER_NO");
                String name = rs.getString("MEMBER_NAME");
                int todoNo = rs.getInt("TODO_NO");
                String title = rs.getString("TITLE");
                String details = rs.getString("DETAILS");
                String status = rs.getString("STATUS");
                Date date = rs.getDate("SET_DATE");

                TestTodo todo = new TestTodo(memberNo, title, todoNo, details, date, status);

                todoList.add(todo);
            }
			
		}finally {
			TestTemplate.close(rs);
			TestTemplate.close(pstmt);
			
		}
		
		return todoList;
	}




	
}
