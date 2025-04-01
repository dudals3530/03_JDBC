package edu.kh.jdbc.test.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.test.common.TestTemplate;
import edu.kh.jdbc.test.dao.Member;
import edu.kh.jdbc.test.dao.TodoDAO;
import edu.kh.jdbc.test.dto.TestTodo;

public class TodoService {

	
	private TodoDAO ttd = new TodoDAO();

	/**회원가입하는 메서드 
	 * @param mem
	 * @return
	 */
	public int signup(Member mem) throws Exception {
	
		Connection conn = TestTemplate.getConnection();
			
		int result = ttd.signup(conn,mem);
		
				if (result > 0 ) {// insert 성공
					TestTemplate.commit(conn);
				
				}else{
					TestTemplate.rollback(conn);
				}
				
				 TestTemplate.close(conn);
				
				return result;
		
	}
	/**로그인하는 서비스 메서드
	 * @param 
	 * @param 
	 * @return
	 */
	public Member login(String inputId, String inputPw) throws Exception {
      
		Connection conn = TestTemplate.getConnection();
		
		Member mem = ttd.login(conn,inputId,inputPw);
		
		TestTemplate.close(conn);
		return mem;
		
	}
	public List<TestTodo> selectTodo(int result) throws Exception {
		
		Connection conn = TestTemplate.getConnection();
		 
		List<TestTodo> todoList = null;

		  todoList = ttd.selectTodo(conn,result);
		if(!todoList.isEmpty()) {
			TestTemplate.commit(conn);
		}else {
			TestTemplate.rollback(conn);
		}
		  
		 TestTemplate.close(conn);
		
		
		  
		return todoList;
				
	}



	



	

	
				
			
			
				
			
			
		
		
}
