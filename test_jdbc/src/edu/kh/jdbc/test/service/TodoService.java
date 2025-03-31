package edu.kh.jdbc.test.service;

import java.sql.Connection;


import edu.kh.jdbc.test.common.TestTemplate;
import edu.kh.jdbc.test.dao.Member;
import edu.kh.jdbc.test.dao.TodoDAO;

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

	

	
				
			
			
				
			
			
		
		
}
