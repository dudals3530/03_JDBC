package edu.kh.jdbc.test.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class TestCreateXMLFile {

	
	public static void main(String[] args) {// 메인메서드 안에서 수행
		
		//XML (eXtensible Markup Language) : 확장가능한 마크업 언어 -> 단순화된 데이터 기술 형식
		
				//XML 에 저장되는 데이터 형식 Key : value 형식 (Map)
				// - > Key, Value 모두 String (문자열) 형식
				
				//XML 파일을 읽고 , 쓰기위한 IO 관련된 클래스 필요
				
				// * Properties 컬렉션 객체 *
				// - Map 의 후손 클래스
				// - Key, Value 모두 String (문자열 형식)
				// - XML 파일을 읽고 , 쓰는데 특화된 메서드 제공
		
		// try catch 문안에서 해줄것
		
		try {
			//스캐너필요
			Scanner sc = new Scanner(System.in);
			
			
			//Properties 객체 생성
			Properties prop = new Properties();
			
			//생성할 파일이름 입력받기
			System.out.print("생성할 파일 이름 : ");
			String input = sc.next();
			
			// FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(input + ".xml");
			
			// Properties 객체를 이용해서 XML 파일 생성 storeToXML 이걸이용해야함.
			prop.storeToXML(fos, input + "testXML");
			
			System.out.println(input+".xml 파일 생성완료"); // 파일이 생성완료되었으면 확인해줄 구문
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("XML 파일 생성중 예외발생");
		}
			
			
			
			
			
			
		
		
		
	}
}
