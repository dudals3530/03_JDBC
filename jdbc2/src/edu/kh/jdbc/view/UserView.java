package edu.kh.jdbc.view;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

// View : 사용자와 직접 상호작용하는 화면 (UI)를 담당,
// 입력을 받고 결과를 출력하는 역할
public class UserView {
	
	// 필드
	private Scanner sc = new Scanner(System.in);
	private UserService service = new UserService(); // UserService랑 소통할 친구
	
	
	
	//메서드
	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() {
		
		// 입력 된 ID 와 일치하는 USER 정보 조회
		System.out.print("ID 입력 : ");
		String input = sc.next();
		
		//서비스 호출 후 결과 반환받기
		User user = service.selectId(input);
		
		// 결과에 따라 사용자에게 보여줄 응답화면 결정
		if(user == null) {
			System.out.println("엄서용");
		
		}else {
		System.out.println(user);
		}
	}

	/** User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu() {
		
		int input = 0;
		
		do {
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch(input) {
				case 1: insertUser(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;
				case 4: selectUser(); break;
				case 5: deleteUser(); break;
				case 6: updateName(); break;
				case 7: insertUser2(); break;
				case 8: multiInsertUser(); break;
				
				case 0 : System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				
				System.out.println("\n-------------------------------------\n");
				
			} catch (InputMismatchException e) {
				// Scanner를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못 입력 하셨습니다***\n");
				
				input = -1; // 잘못 입력해서 while문 멈추는걸 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거
				
			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}
			
		}while(input != 0);
		
	} // mainMenu() 종료

	
	

	//1
	/**
	 * 1. User 등록 관련 view
	 * @throws Exception 
	 */
	private void insertUser() throws Exception {// 다른클래스에서 이메서드가 불릴 일이 없기때문에 private 해도 상관없음
		//user 등록 (INSERT)
		System.out.println("\n =====1. User 등록 ===== \n");
		
		System.out.print("새로운 아이디 입력 : ");
		String inputId = sc.next();
		
		System.out.print("비밀번호 입력 : ");
		String inputPw = sc.next();
		
		System.out.print("이름 입력 : ");
		String inputName = sc.next();
		
		//입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
		// User Dto 객체를 생성한 후 필드에 값을 셋팅
		User user = new User();
		
		// setter 이용
		user.setUserId(inputId);
		user.setUserPw(inputPw);
		user.setUserName(inputName);
		
		// 서비스 호출 (INSERT) 후 결과 반환 (int, 결과행의 개수)받기
		int result = service.insertUser(user); // user를 전달인자로 서비스단에 보내면서 값을 받아올 변수
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0) {
			System.out.println("\n"+inputId + "사용자가 등록되었습니다.\n");
		}else {
			System.out.println("\n******* 등록실패 ********\n");
			
		}
	}
	//2
	/**
	 * 2. User 전체 조회 관련 view (SELECT)
	 * @throws 
	 */
	private void selectAll() throws Exception {
		System.out.println("\n========2 . User 전체 조회 ==============\n");
		
		//서비스 호출(SELECT) 후 결과 (List<User>) 반환받기
		List<User> userList =service.selectAll();
		
		//결과에 따라 처리하기
		
		//조회 결과가 없을경우
		if(userList.isEmpty()) {// userList가 비어있따면
			System.out.println("\n******조회 결과가 없습니다 ********\n");
			return;
		}
		// 있을 경우 향상된 for문 이용해서 userList 에있는 user 출력
		for(User user : userList) {
			System.out.println(user);
		}
		
	}
	//3
	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회
	 */
	private void selectName() throws Exception {
		System.out.println("\n=========3 . User 중 이름에 검색어가 포함된 회원 조회 =========\n");
		System.out.print("검색어 입력 : ");
		String keyword = sc.next();
		List<User> userList = service.selectName(keyword);
		
		//서비스 호출후 결과 반환받기
		if(userList.isEmpty()) {// userList가 비어있따면
			System.out.println("\n******조회 결과가 없습니다 ********\n");
			return;
		}for(User user : userList) {
			System.out.println(user);
		}
	}
	//4
	/**
	 * 4. USER_NO를 입력받아 일치하는 user 조회
	 */
	private void selectUser() throws Exception{

		System.out.println("\n=======USER_NO를 입력 받아 일치하는 User 조회(SELECT)======\n");
		
		System.out.print("사용자 번호 입력 : ");
		int input = sc.nextInt();
		
		//사용 자번호 == PK == 중복이 있을수 없다
		// == 일치하는 사용자가 있다면 딱 1행만 조회된다.
		// -> 1행의 조히결과를 담기 위해서 User DTO 객체 1개 사용
		
		
		User user = service.selectUser(input);
		
		// 조회결과가 없으면 null, 있으면 null 아님
		if(user == null) {
			System.out.println("USER_NO가 일치하는 회원없음");
			return;
		}
		System.out.println(user);
		
	}
	//5
	/**5. USER_NO 를 입력받아 일치하는 User 삭제
	 * 
	 */
	private void deleteUser() throws Exception {
		
		System.out.println("\n======USER_NO를 입력 받아 일치하는 User 삭제(DELETE)=======\n");
		
		System.out.print("사용자 번호 입력 : ");
		
		int input = sc.nextInt();
		 
		int result = service.deleteUser(input);
		
		// 조회결과가 없으면 null ??? 일거얌 ㅇㅇ..
		if (result > 0) {
			System.out.println(input+"번 삭제성공");
			
		}else {
			System.out.println("사용자번호가 일치하는 User가 존재하지 않음");
		}
	
		
		
		
	}
	//6
	/**
	 * 6. ID, PW가 일치하는 회원(SELECT)이 있을경우 이름 수정(UPDATE)
	 */
	private void updateName() throws Exception {
		
		System.out.println("\n==ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)==\n");
		System.out.print("아이디 입력 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 입력 : ");
		String userPw= sc.next();
		
		// 입력 받은 ID, PW 가 일치하는 회원이 존재하ㄴ는지 조회 (SELECT)
		// - > USER_NO 조회
		int userNo = service.selectUserNO(userId, userPw);
		
		if(userNo == 0) {
			System.out.println("아이디, 비밀번호가 일치하는 사용자가없음");
			return;
		}
		// 조회결과 있음!
		System.out.println("수정할 이름 입력 : ");
		String userName= sc.next();
		// 위에서 조회된 회원(user_No)의 이름수정
		// 서비스 호출 (UPDATE) 후 결과 반환 (int) 반환 받기	
		
		int result = service.updateName(userNo,userName);
			
		if(result > 0) System.out.println("수정성공");
		else System.out.println("수정실패!");
			
		
			
		
		
		
		
		
	}
	//7
	/**7. User 등록 (아이디 중복검사)
	 * 
	 */
	private void insertUser2() throws Exception {
		
		System.out.println("====7. User 등록(아이디 중복 검사)===");
		
		String userId = null; // 입력된 아이디를 저장할 변수
		
		while(true) {
			
			System.out.print("ID : ");
			userId = sc.next();
			
			//입력받은 userId가 중복인지 검사하는
			//서비스 (SELECT) 호출후
			//결과(int, 중복 == 1, 아니면 == 0) 반환 받기
			int count = service.idCheck(userId);

			if(count == 0) {
				System.out.println("사용가능한 아이디입니다.");
				break;
			}
			System.out.println("이미 사용중인아이디입니다. 다시입력해주세요");
		}
		
		//pw, name 입력받기
		System.out.print("비밀번호 입력 : ");
		String inputPw = sc.next();
		
		System.out.print("이름 입력 : ");
		String inputName = sc.next();
		
		//입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
		// User Dto 객체를 생성한 후 필드에 값을 셋팅
		User user = new User();
		
		// setter 이용
		user.setUserId(userId);
		user.setUserPw(inputPw);
		user.setUserName(inputName);
		
		// 서비스 호출 (INSERT) 후 결과 반환 (int, 결과행의 개수)받기
		int result = service.insertUser(user); // user를 전달인자로 서비스단에 보내면서 값을 받아올 변수
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0) {
			System.out.println("\n"+userId + "사용자가 등록되었습니다.\n");
		}else {
			System.out.println("\n******* 등록실패 ********\n");
			
		}
		
	}
	//8
	/**8. 여러 User 등록하기
	 * 
	 */
	private void multiInsertUser() throws Exception {
		
		/*
		 * 등록할 User 수 : 2
		 * 
		 * 1번째 userId : user100
		 * -> 사용 가능한 ID 입니다
		 * 1번째 userPw : pass100
		 * 1번째 userName:유저백
		 * ----------------------
		 * 2번째 userId ? user200
		 * -> 사용 가능한 ID 입니다
		 * 2번째 userPw : pass200
		 * 2번째 userName:유저이백
		 * 
		 *  -- 전체 삽입 성공 / 삽입실패
		 *  
		 * */
		
		System.out.println("\n===========8. 여러 User 등록하기============\n");
		
		System.out.print("등록할 유저수 : ");
		int input = sc.nextInt();
		sc.nextLine(); // 버퍼 개행문자 제거
		
		// 입력받은 회원정보를 저장할 List 객체 생성
		List<User> userList = new ArrayList<User>();
		
		for(int i = 0; i < input; i++) {
			
			String userId = null; // 입력된 아이디를 저장할 변수
			
			while(true) {
				
				System.out.print((i+1) + "번째 userId : ");
				userId = sc.next();
				
				//입력받은 userId가 중복인지 검사하는
				//서비스 (SELECT) 호출후
				//결과(int, 중복 == 1, 아니면 == 0) 반환 받기
				int count = service.idCheck(userId);

				if(count == 0) {
					System.out.println("사용가능한 아이디입니다.");
					break;
				}
				System.out.println("이미 사용중인아이디입니다. 다시입력해주세요");
			}
			
			//pw, name 입력받기
			System.out.print((i+1) + "번째 userPw : ");
			String inputPw = sc.next();
			
			System.out.print((i+1) + "번째 userName : ");
			String inputName = sc.next();
			
			System.out.println("------------------------------");
			
			//입력받은 값 3개를한번에 묶어서 전달할수 있도록
			//user 객체를 생성한 후 userList에 추가
			User user = new User();
			
			user.setUserId(userId);
			user.setUserPw(inputPw);
			user.setUserName(inputName);
		
			// userList에 user 추가
			userList.add(user);
			
		}//for문 종료
		
		// 입력 받은 모든 사용자를 insert 하는 서비스 호출
		// -> 결과로 삽입된 행의 개수 반환
		int result = service.multiInsertUser(userList);
		
		if (result == userList.size()) {
			System.out.println("전체삽입 성공!!");
		}else {
			System.out.println("삽입실패");
		}
		
	}

	

	

	

	

	

	

	
	
	
	
}

