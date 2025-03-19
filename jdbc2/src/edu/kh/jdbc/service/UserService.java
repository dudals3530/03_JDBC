package edu.kh.jdbc.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

// (Model 주 하나)Service : 비즈니스 로직을 처리하는 계층
// 데이터를 가공하고 트랜잭션(commit, rollback) 관리 수행
public class UserService {
	
	//필드 
	private UserDAO dao = new UserDAO();  // UserDAO랑 소통할친구
	
	
	
	//메서드
	/**전달받은 아이디와 일치하는 User 정보 반환 서비스
	 * @param input (입력된 아이디)
	 * @return 아이디가 일치하는 회원 정보가 담긴 User 객체
	 *         없으면 null 반환
	 */
	public User selectId(String input) {
	    
		// 1. 커넥션 생성 -> Service 단에서 메서드만들때 첫번째로는 커넥션생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공 (하게없으면 넘어감)
		// 전달값이 여러개일떄
		// User 객체를 생성해서 
		// Setter를 사용해서 하나의 User로 세팅해줘서 
		// 이거를 넘겨준다 DAO로 
		
		// 3. DAO 메서드 호출 결과 반환
	 User user = dao.selectId(conn,input);
		
		// 4. DML (commit/rollback) (SELECT 엿으면 넘어가도됨)
		
		// 5 . 다쓴 커넥션 자원 반환
		JDBCTemplate.close(conn);
		// 6. 결과를 view 리턴 
		
		
		return user;
	}



	/**1. User 등록 서비스
	 * @param user : 입력받은 id , pw ,name이 셋팅된 객체
	 * @return 결과행의 개수
	 * @throws Exception 
	 */
	public int insertUser(User user) throws Exception {
		
		//1 . 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO 메서드 호출 결과 반환받기
		int result = dao.insertUser(conn,user);
		
		//4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어처리
		if (result > 0 ) {// insert 성공
			JDBCTemplate.commit(conn);
		
		}else{// insert 실패
			JDBCTemplate.rollback(conn);
		}
		//5.Connection 반환하기
		JDBCTemplate.close(conn);
		
		// 6. 결과 반환
		return result;
	}



	/** 2. User 전체 조회 서비스
	 * @return 조회된 User들이 담긴 List
	 */
	public List<User> selectAll() throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO 메서드 호출(SELECT) 결과 반환(List<User>)받기 
		List<User> userList = dao.selectAll(conn);
		
		//4. DML 수행결과에 따라 트랜잭션 제어처리
		
		//5. Connection 반환하기
		JDBCTemplate.close(conn);
		
		//6. 결과 반환
		return userList;
	}



	/**3. User 중 이름에 검색어가 포함된 회원 조회 서비스
	 * @param keyword : 입력한 키워드
	 * @return searchList : 조회된 회원 리스트
	 */
	public List<User> selectName(String keyword) throws Exception{
         //1 .커넥션 생성
		
		Connection conn = JDBCTemplate.getConnection();
		
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO메서드 호출(SELECT) 결과반환 (List<User>)받기
		List<User> userList = dao.selectName(conn,keyword);
		
		//4. DMl 수행결과에 따라 트랜잭션 제어처리
		
		//5. Connection 반환하기
		JDBCTemplate.close(conn);
		
		//결과 반환
		return userList;
	}



	/**4. USER_NO를 입력받아 일치하는 User 조회
	 * @param input
	 * @return user (조회된 회원정보, 또는 null)
	 */
	public User selectUser(int input) throws Exception {
		 //1 .커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO메서드 호출(SELECT) 결과반환
		User user = dao.selectUser(conn,input);
		
		//4. DMl 수행결과에 따라 트랜잭션 제어처리
		
		//5. Connection 반환하기
		JDBCTemplate.close(conn);
		return user;
	}



	/**USER_NO를 입력받아 일치하는 user 삭제 서비스
	 * @param input
	 * @return result
	 */
	public int deleteUser(int input) throws Exception {
		 //1 .커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO메서드 호출(SELECT) 결과반환
		int result = dao.deleteUser(conn,input);
		
		//4. DMl 수행결과에 따라 트랜잭션 제어처리
		if (result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		//5. Connection 반환하기
		JDBCTemplate.close(conn);
		
		return result;
	}



	/** ID, pw가 일치하는 회원의 User_NO 조회 서비스
	 * @param userId
	 * @param userPw
	 * @return userNo
	 */
	public int selectUserNO(String userId, String userPw) throws Exception {
		 //1 .커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO메서드 호출(SELECT) 결과반환
		int userNo = dao.selectUser(conn, userId,userPw);
		
		JDBCTemplate.close(conn);
		return userNo;
	}



	/** userNo가 일치하는 회원의 이름 수정 서비스
	 * @param userNo
	 * @param userName
	 * @return result
	 */
	public int updateName(int userNo, String userName) throws Exception {
		 //1 .커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		//2. 데이터 가공 (할게없으면 넘어감)
		
		//3. DAO메서드 호출 결과반환
		int result = dao.updateName(conn, userNo,userName);
		
		//4. DMl 수행결과에 따라 트랜잭션 제어처리
				if (result > 0) {
					JDBCTemplate.commit(conn);
				}else {
					JDBCTemplate.rollback(conn);
				}
		JDBCTemplate.close(conn);
		
		return result;
	}



	/** 아이디 중복 확인 서비스
	 * @param userId
	 * @return count
	 */
	public int idCheck(String userId)throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = dao.idCheck(conn,userId);
		
		JDBCTemplate.close(conn);
		
		return count;
	}



	/** userList 에있는 모든 User 객체를 INSERT 서비스
	 * @param userList
	 * @return count
	 */
	public int multiInsertUser(List<User> userList) throws Exception {
		
		// 다중 INSERT 방법
		//1) SQL 을 이용한 다중 INSET
		//2) Java 반복문을 이용한 다중 INSERT(이거사용!)
		Connection conn = JDBCTemplate.getConnection();
		
		int count = 0; //삽입 성공한 행의 개수 count
		
		// 1행씩 삽입
		for(User user : userList) {
			int result = dao.insertUser(conn, user);
			count += result; //삽입 성공한 행의 개수를 count 누적
		}

		// 트랜잭션 제어처리
		// 전체 삽입 성공 commit / 아니면 rollback(일부 삽입, 전체 실패)
		if (count == userList.size()) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return count;
	}



	
	


	
	
	
	
	
}
