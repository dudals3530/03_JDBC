package edu.kh.jdbc.test.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.test.dao.Member;
import edu.kh.jdbc.test.dto.TestTodo;
import edu.kh.jdbc.test.service.TodoService;

public class TodoView {

	private Scanner sc = new Scanner(System.in);
	private TodoService tus = new TodoService();
	private Member loginMem = null;

	public void todomenu() {
		/*
		 * 
		 * 1. 회원가입
			2. 로그인
			3. 내 TODO 전체 조회 (번호, 제목, 완료여부, 작성일)
			4. 새로운 Todo 추가
			5. Todo 수정 (제목, 내용)
			6. 완료여부변경 (Y <-> N)
			7. Todo 삭제
			8. 로그아웃
		 * */
		int input= 0;
		
	do {
		
		try {
		System.out.println("\n===== todolist 프로그램 =====\n");
		System.out.println("1.회원가입(INSERT)");
		System.out.println("2.로그인");
		System.out.println("3 내 전체 TODO조회(번호, 제목,완료여부,작성일)SELECT");
		System.out.println("4. 새로운 Todo 추가(INSERT)");
		System.out.println("5. Todo 수정 (제목, 내용))(UPDATE)");
		System.out.println("6. 완료여부변경 (Y <-> N)(UPDATE)");
		System.out.println("7. Todo 삭제(DELETE)");
		System.out.println("8. 로그아웃");
		System.out.println("0. 프로그램 종료");
		
		System.out.print("원하는 메뉴를 입력해주세요 : ");
		input = sc.nextInt();
		
		switch(input) {
				
		case 1: signUp(); break;
		case 2: login(); break;
		case 3: selectTodo(); break;
		case 4: //addTodo(); break;
		case 5: //updateTodo(); break;
		case 6: //updateStatus(); break;
		case 7: //delecteTodo(); break;
		case 8: //logout();    break;
		case 0: System.out.println("프로그램종료"); break;
		default: System.out.println("1~8번만 눌러주세용");
		
		
		}
		
		}catch (InputMismatchException e) {
			e.printStackTrace();
			System.out.println("번호만눌러주세요");
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	
	}while(input !=0);
		
	
		
		
		
	}

	
	private void selectTodo() throws Exception {
		if (loginMem == null ) {
			System.out.println("로그인이 안되어있씁니다.");
			return;
		}
		
	 int result	=loginMem.getMemberNo();
	 List<TestTodo> todoList = tus.selectTodo(result);
	 
	 if(todoList.isEmpty()) {
		 System.out.println("존재하는 Todo가없습니다 . 추가해주세요.");
		 return;
		 
	 }else {
		 System.out.println(loginMem.getMemberName()+"님의 TODO는현재");
		 for(TestTodo todo :todoList) {
			 System.out.print(
			 + todo.getTodoNo()+todo.getTitle()+todo.getTitle()
			 +todo.getStatus()+todo.getSetDate()+"입니다.");
		 }
		 
	 }
	 
	}


	//2.
	/**로그인 하는 메서드
	 * @throws Exception 
	 * 
	 */
	private void login() throws Exception {
		
		if( loginMem != null) {
			System.out.println("현재로그인중입니다.");
			return;
		}
		
		System.out.print("아이디 입력 : ");
		String inputId = sc.next();
		
		System.out.print("비밀번호 입력 : ");
		String inputPw= sc.next();
		
		
		Member mem = tus.login(inputId,inputPw);
		
		if(mem != null) {
			
			loginMem = mem;
			 System.out.println(mem.getMemberName()+"님 로그인성공");
		}else {
			System.out.println("로그인실패");
		}
		  
		
		
		
	}

	
	//1.
	/**
	 * 회원가입하는 메서드
	 */
	private void signUp() throws Exception {
		System.out.println("\n----회원가입---\n");
		
		System.out.print("id 입력");
		String inputId = sc.next();
		System.out.print("비밀번호 입력");
		String inputpw = sc.next();
		System.out.print("이름 입력");
		String inputName = sc.next();
		
		Member mem = new Member();
		mem.setMemberId(inputId);
		mem.setMemberPw(inputpw);
		mem.setMemberName(inputName);
		
		int result = tus.signup(mem);
		
		if(result > 0) {
			System.out.println(inputName+"님의 회원가입완료");
		}else {
			System.out.println("아이디 중복임. 회원가입실패");
		}
		
	}
		
	
	}
	


			
			



