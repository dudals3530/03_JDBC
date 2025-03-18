package edu.kh.jdbc.service;

import java.sql.Connection;

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



	/** 입력값으로 받은 새로운 USER 를 하나의 USER로 Setter 해줘서
	 *  전달인자를 간략히 해줌 , 그걸  DAO로 넘겨주고 반환받아올거임.
	 * @param inputId
	 * @param inputPw
	 * @param inputName
	 * @return user
	 */
	public User createId(String inputId, String inputPw, String inputName) {
		
		//1. 커넥션생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2. 데이터 가공 !
		User user = null;
		user.setUserId(inputId);
		user.setUserPw(inputPw);
		user.setUserName(inputName);
		
		//3. DAO 메서드 호출결과 반환
		user = dao.createId(conn, user);
		
		//4. DML(Commit/rollback)(SELECT이면 skip)
		if(user !=null) {
			JDBCTemplate.commit(conn);
			System.out.println("커밋완료");
		}else {
			JDBCTemplate.rollback(conn);
			System.out.println("트랜잭션에있는거 롤백함..");
		}
		
			JDBCTemplate.close(conn);
		
		return user;
	}


	
	
	
	
	
}
