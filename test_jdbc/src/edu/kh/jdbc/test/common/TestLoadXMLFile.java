package edu.kh.jdbc.test.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TestLoadXMLFile {

	public static void main(String[] args) {
		// try catch 문 이용
		
		try {	
			
		Properties prop = new Properties();
			
			//driver.xml 파일을 읽기 위한 inputStream 객체생성
			FileInputStream fis = new FileInputStream("testdriver.xml");

			// 연결된 driver.xml 파일에 있는 내용을 모두 읽어와
			// Properties 객체에 K:V 형식으로 저장
			prop.loadFromXML(fis);
			
			// prop.getProperty("key") : key 가 일치하는 속성값 (value)를 얻어옴
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,userName,password);
			
			System.out.println(conn);
			
			
			/*// XML 파일 읽어오기 (FileInputStream, Properties)
			Properties prop = new Properties();
			//driver.xml 파일을 읽기 위한 inputStream 객체생성
			FileInputStream fis = new FileInputStream("testdriver.xml");
			
			// 연결된 driver.xml 파일에 있는 내용을 모두 읽어와 loadFromXML 이걸 사용하기
			prop.loadFromXML(fis);
			
			// Properties 객체에 K:V 형식으로 저장 getProperty 메서드 이용  key값을 저장할 String 형 만들어서 저장해주기
			// prop.getProperty("key") : key 가 일치하는 속성값 (value)를 얻어옴
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			
			// Class.Forname 을 여기서 이제 쉽게 사용해보자 .. 
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,userName,password);
			//Connection 객체 conn 을 만들어줘서 DriverManager를 통해 DB 의 연결 값들를 가져오자 url,사용자계정, 비밀번호
			System.out.println("완료");*/
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		}
		
		
	
		
	
		//oracle.jdbc.driver.T4CConnection@29176cc1

		
		  /*
	      * 
	      * 왜 XML 파일을 이용해서 JDBC를 진행하는가 ? 
	      * 
	      * 1. DB 연결 정보 / 드라이버 정보등 코드 중복제거
	      * 2. 보안 측면에서 별도 관리 필요!
	      * 3. 재컴파일을 진행하지 않기 위해서
	      * 4. XML 파일에 작성된 문자열 형태를 그대로 읽어오기 때문에
	      *    XML 파일에 SQL 문 작성시 다루기가 좀더 편리해짐.
	      * 
	      * 
	      * 
	      * 
	      * 
	      * 
	   
	      * 
	      * */
		
		
	}
	
	}
	
