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
		 * 1. 회원가입 2. 로그인 3. 내 TODO 전체 조회 (번호, 제목, 완료여부, 작성일) 4. 새로운 Todo 추가 5. Todo 수정
		 * (제목, 내용) // 칼국시 맛있겠다 6. 완료여부변경 (Y <-> N) 7. Todo 삭제 8. 로그아웃
		 */
		int input = 0;

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

				switch (input) {

				case 1:
					signUp();
					break;
				case 2:
					login();
					break;
				case 3:
					selectTodo();
					break;
				case 4:
					addTodo();
					break;
				case 5:
					updateTodo();
					break;
				case 6:
					updateStatus();
					break;
				case 7: deleteTodo(); break;
				case 8: // logout(); break;
				case 0:
					System.out.println("프로그램종료");
					break;
				default:
					System.out.println("1~8번만 눌러주세용");

				}

			} catch (InputMismatchException e) {
				e.printStackTrace();
				System.out.println("번호만눌러주세요");
			} catch (Exception e) {
				e.printStackTrace();

			}

		} while (input != 0);

	}

	/**7 todo를 삭제하는 메서드
	 * @throws Exception 
	 * 
	 */
	private void deleteTodo() throws Exception {
		
		if (loginMem == null) {
			System.out.println("로그인먼저해주세요");
			return;
		}
		
		selectTodo();
		
		System.out.println("삭제하고싶은 Todo의 번호를입력해주세요 : ");
		int deleteNo = sc.nextInt();
		
		System.out.print("비밀번호를 입력해주세요");
		String pw = sc.next();
		
		if (!pw.equals(loginMem.getMemberPw())) {
			System.out.println("비밀번호가 맞지않습니다!!");
			return;
		}
		
		int result = tus.deleteTodo(deleteNo);
		
		
	}

	/**
	 * 6 완료여부 변경 하는 메서드 Y이면 N N이면 Y로
	 * 
	 * @throws Exception
	 * 
	 */
	private void updateStatus() throws Exception {

		if (loginMem == null) {
			System.out.println("로그인먼저해주세요");
			return;
		}
		
		selectTodo();
		
		System.out.print("완료여부 변경할 ToDo의 번호를 입력해주세요 : ");
		int todoNum = sc.nextInt();
		boolean flag = false;

		while (!flag) {
			System.out.print("완료여부를 어떻게 바꾸시겠습니까? (y/n) : ");
			String yn = sc.next();

			if (yn.equals("y")) {
				flag = true;
				yn = "Y";
			} else if (yn.equals("n")) {
				flag = true;
				yn = "N";
			} else {
				System.out.println("잘못입력하셨습니다.");
			}

			int result = tus.updateStatus(todoNum,yn);
			
			if(result > 0) {
			
				System.out.println("완료여부 변경을 성공하였습니다");
				System.out.println("현재 todoList 목록");
				selectTodo();
			
			}else {
				System.out.println("완료여부변경의 실패하였습니다.");
			}
			
		}
		
			
			

	}

	/**
	 * 5 todoList 수정하는 메서드
	 * 
	 * @throws Exception
	 */
	private void updateTodo() throws Exception {

		if (loginMem == null) {
			System.out.println("로그인먼저해주세요");
			return;
		}

		selectTodo();
		System.out.println("-----------------------");

		boolean flag = false;

		while (!flag) {
			System.out.println("변경할 내용있다면(y/n)? 를 선택해주세요. ");
			String ans = sc.next();
			if (ans.equalsIgnoreCase("Y")) {

				flag = true;
				System.out.print("변경하실 todo의 번호를 입력해주세요");

				int todoNum = sc.nextInt();
				System.out.print("수정제목입력 : ");
				String updateTitle = sc.next();

				System.out.print("수정할일입력 : ");
				String updateDetails = sc.next();

				int result = tus.updateTodo(todoNum, updateTitle, updateDetails);

				if (result > 0) {

					System.out.println("수정성공!!");
					System.out.println("현재의 todoList");

					selectTodo();

				} else {
					System.out.println("수정실패!!");
				}

			} else if (ans.equalsIgnoreCase("N")) {
				flag = true;
				System.out.println("메뉴화면으로 돌아갑니다.");
				return;
			} else {
				System.out.println("잘못입력하셧습니다");

			}
		}

	}

	/**
	 * 4. todoList를 추가하는 메서드
	 * 
	 * @throws Exception
	 */
	private void addTodo() throws Exception {
		if (loginMem == null) {
			System.out.println("로그인먼저해주세요");
			return;
		}
		int memNo = loginMem.getMemberNo();

		System.out.print("제목을 지정해주세요 : ");
		String title = sc.next();
		System.out.print("할일의 세부사항을입렵해주세요 : ");
		String details = sc.next();

		int result = tus.addTodo(memNo, title, details);

		if (result > 0) {
			System.out.print(loginMem.getMemberName());
			System.out.print("님의" + title + " 의" + details + " 할일이 추가되었습니다.");
		}

	}

	/**
	 * 3. todolist 전체조회
	 * 
	 * @throws Exception
	 */
	private void selectTodo() throws Exception {
		if (loginMem == null) {
			System.out.println("로그인이 안되어있씁니다.");
			return;
		}

		int result = loginMem.getMemberNo();
		List<TestTodo> todoList = tus.selectTodo(result);

		if (todoList.isEmpty()) {
			System.out.println("존재하는 Todo가없습니다 . 추가해주세요.");
			return;

		} else {
			System.out.println(loginMem.getMemberName() + "님의 TODO는현재");
			for (TestTodo todo : todoList) {
				System.out.println(todo);

			}

		}

	}

	/**
	 * 2 로그인 하는 메서드
	 * 
	 * @throws Exception
	 * 
	 */
	private void login() throws Exception {

		if (loginMem != null) {
			System.out.println("현재로그인중입니다.");
			return;
		}

		System.out.print("아이디 입력 : ");
		String inputId = sc.next();

		System.out.print("비밀번호 입력 : ");
		String inputPw = sc.next();

		Member mem = tus.login(inputId, inputPw);

		if (mem != null) {

			loginMem = mem;

			System.out.println(mem.getMemberName() + "님 로그인성공");
		} else {
			System.out.println("로그인실패");
		}

	}

	/**
	 * 1 회원가입하는 메서드
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

		if (result > 0) {
			System.out.println(inputName + "님의 회원가입완료");
		} else {
			System.out.println("아이디 중복임. 회원가입실패");
		}

	}

}
