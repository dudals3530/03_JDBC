package edu.kh.jdbc.dao;

// import static ~.*; : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 클래스명. 메서드명()이 아닌 메서드명() 만 작성해도 호출 가능하게함.
import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.dto.User;

// (Model 중 하나)DAO (Data Access Object) 
// 데이터가 저장된 곳에 (DB) 에 접근하는 용도의객체
// -> DB 에 접근하여 JAVA에서 원하는 결과를 얻기위해
//    SQL을 수행하고 결과를 반환 받는 역할
public class UserDAO {

	// 필드
	// -DB 접근 관련한 JDBC 객체 참조 변수 미리 선언
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	//메서드
	/** 전달받은 Connection을 이용해서 DB에 접근하여
	 * 전달받은 아이디(input)와 일치하는 User 정보를 DB 조회하기
	 * 
	 * @param conn : Service 에서 생성한 Connection 객체
	 * @param input : View에서 입력받은 아이디
	 * @return 아이디가 일치하는 회원의 User 또는 null
	 */
	public User selectId(Connection conn, String input) {
		
		// 1. 결과 저장용 변수 선언 
		User user = null;
		
		try {
			
			// 2. SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
			
			//3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//4. ?(위치 홀더) 에 알맞은 값 세팅
			
			pstmt.setString(1, input);
		    
			//5. SQL 수행후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			//6.조회결과가 있을경우 - > id가 일치하는 경우는 한개의 행만 가능!
			//                          중복되는 아이디가 없다고 가정
			// -> 1행만 조회되기 대문에 while 문 보다는 if를 사용하는 게 효과적
			if (rs.next()) {
				// 첫행에 데이터가 존재한다면
				
				// 각컬럼의 값 얻어오기
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName= rs.getString("USER_NAME");
				// java.sql.date
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				// 조회된 컬럼값을 이용해서 User 객체 생성
				user = new User(userNo,userId,userPw,userName,enrollDate.toString() );
		     //                                                User 에 enrollDAte는 String 형이기 때문에 이렇게해줘야
				                                            // User를 참조할수있음
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 사용 한 JDBC 객체 자원 반환 (close)
			//JDBCTemplate.close(rs);
			//JDBCTemplate.close(pstmt);
			
			close(rs);
			close(pstmt);
			
			// Connection 객체는 생성된 Service 단에서 close!!
		}
		
		
		return user; // 결과 반환 (생성된 User 객체 또는 null)
	}



	



	/**1. user 등록 DAO 
	 * @param conn : DB 연결정보가 담겨있는 Connection 객체
	 * @param user : 입력받은 id, pw ,name 이 셋팅된 객체
	 * @return INSERT 결과 행의 개수
	 * @throws Exception 
	 */
	public int insertUser(Connection conn, User user) throws Exception {
		
		//SQL 수행중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져처리
		// -> catch 문 필요없다!
		
		//1 . 결과저장용 변수선언
		int result = 0;
		
		try {
			// 2 . SQL 작성
			String sql = """
					 INSERT INTO TB_USER VALUES(
					 SEQ_USER_NO.NEXTVAL,
					 ?, ?, ?, DEFAULT)
					""";
			//3. preparedStatement 작성
			pstmt = conn.prepareStatement(sql);
			
			//4. ? (위치홀더)알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			//5. SQL 수행후 결과 반환받기
			
			result = pstmt.executeUpdate();
			
		}finally {
			//6. 사용한 jdbc 객체 자원 반환 (close)
			close(pstmt);
		}
		
		
		// 결과저장용 변수에 저장된 값 반환
		return result;
	}







	/**2. User 전체 조회 DAO
	 * @param conn
	 * @return userList
	 */
	public List<User> selectAll(Connection conn) throws Exception {
		//1. 결과 저장용 변수선언
		
		List<User> userList = new ArrayList<User>();
		
		try {
			//2. SQL 작성
			String sql = """
				     SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
				 	 TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
				 	 FROM TB_USER
					 ORDER BY USER_NO
					""";
			
			//3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			//4.? 에 알맞은 값 대입 (없으면 패스)
			
			//5. SQL(SELECT) 수행(executeQuery) 후 결과 반환(ResultSet) 받기
			rs = pstmt.executeQuery();
			
			//6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기
			// 몇행이 조회될지 모른다 - > while
			// 무조건 1행이 조회된다 - > if
			while (rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				// java.sql.Date 타입으로 값을 저장하지 않은이유
				// -> SELECT 문에서 TO_CHAR() 를 이용하여 문자열로 변환해 조회했기
				//때문에
				User user = new User(userNo,userId,userPw,userName,enrollDate);
				userList.add(user);
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		// 조회 결과가 담긴 List 반환
		return userList;
	}







	/**3. 이름에 검색어가 포함되는회원 모두 조회 DAO
	 * @param conn
	 * @param keyword
	 * @return searchList
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {
		// 결과 저장용 변수 선언
		List<User> userList = new ArrayList<User>();
		
		try {//2 . SQL문 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO
					""";
			//PreparedStatement 객체 생성
			
			pstmt = conn.prepareStatement(sql);
			
			// ? (위치 홀더) 값 세팅
			pstmt.setString(1, keyword);
			// DB 수행후 결과 반환받기
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo,userId,userPw,userName,enrollDate);
				userList.add(user);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userList;
	}







	/**4. USER_NO를 입력받아 일치하는 User 조회 DAO
	 * @param conn
	 * @param input
	 * @return user 객체 or null
	 */
	public User selectUser(Connection conn, int input) throws Exception {
		//1 . 결과저장용 변수선언
		User user = null;
		
		try {
			String sql = """
					 SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			//2.SQL 문 작성
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				 user = new User(userNo,userId,userPw,userName,enrollDate);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return user;
	}







	/** USER_NO를 입력받아 일치하는 User 삭제 DAO 
	 * @param conn
	 * @param input
	 * @return result
	 */
	public int deleteUser(Connection conn, int input) throws Exception {
		//1 . 결과저장용 변수선언
		int result = 0;
		
		try { //2.SQL 문 작성
			String sql = """
					 DELETE FROM TB_USER
					 WHERE USER_NO = ?
					""";
			//3. pstmt 생성
			pstmt = conn.prepareStatement(sql);
			
			//4. ? 셋
			pstmt.setInt(1, input);
			
			// 결과 값을 
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}







	/** ID, PW 가 일치하는 회원의 User_NO 조회 DAO
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return userNo
	 */
	public int selectUser(Connection conn, String userId, String userPw) throws Exception {
		//1 결과저장용 변수선언
		int userNo = 0;
		
		try {//2. sql작성
			 String sql = """
			 		 SELECT USER_NO
			 		 FROM TB_USER
			 		 WHERE USER_ID = ?
			 		 AND USER_PW = ?
			 		""";
			 
			 pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setString(1, userId);
			 pstmt.setString(2,userPw);
			 
			 rs = pstmt.executeQuery();
			 // 조회된 1행이 있을경우
			 
			 if(rs.next()) {
				 userNo = rs.getInt("USER_NO");
			 }
			 
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo; // 조회성공 USER_NO , 실패 0반환
	}







	/**userNo가 일치하는 회원의 이름 수정 DAO
	 * @param conn
	 * @param userNo
	 * @param userName
	 * @return result
	 */
	public int updateName(Connection conn, int userNo, String userName) throws Exception {
		int result = 0;
		
		try {
			String sql = """
					UPDATE TB_USER
					SET USER_NAME = ?
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}







	/** 아이디 중복확인 DAO
	 * @param conn
	 * @param userId
	 * @return count
	 * @throws Exception
	 */
	public int idCheck(Connection conn, String userId) throws Exception {
		
		int count = 0;
		
		try {
			String sql = """
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ? 
					
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
		if(rs.next()) {
			count =rs.getInt(1); // 조회된 컬럼 순서번호를 이용해
			                     // 컬럼값 얻어오기
		}
			
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return count;
	}
	
	
	
	
	
	
}
