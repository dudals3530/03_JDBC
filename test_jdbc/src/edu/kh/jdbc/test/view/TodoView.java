package edu.kh.jdbc.test.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.test.dao.Member;
import edu.kh.jdbc.test.service.TodoService;

public class TodoView {

	private Scanner sc = new Scanner(System.in);
	private TodoService tus = new TodoService();

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
		case 4: addTodo(); break;
		case 5: updateTodo(); break;
		case 6: updateStatus(); break;
		case 7: delecteTodo(); break;
		case 8: logout();    break;
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

	/**
	 * 회원가입하는 메서드
	 */
	private void signUp() {
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
	}

	
	}
	
}
			
			



