package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	//Connection 데이터 베이스 접근하게 해주는 하나의 객체
	private Connection conn;
	private PreparedStatement pstmt;
	//하나의 정보를 담을 수 있는 하나의 객체
	private ResultSet rs;
	
	//생성자를 만들어줍니다.
	public UserDAO() {
		
		try {
			
			//localhost:3306 는 컴퓨터에 설치된 mariadb 서버
			//project 는 테이블 이름
			String dbURL = "jdbc:mariadb://localhost:3306/project?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "sykim300";
			
			//driver는 mariadb에 접속할 수 있도록 도와주는 하나의 라이브러리 매개체
			Class.forName("org.mariadb.jdbc.Driver");
			conn=DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//실제 로그인을 시도하는 함수
	//아이디와 비밀번호를 받아옴
	public int login(String userID, String userPassword) {
		
		//실제로 db에 입력할 sql문
		String SQL = "SELECT userPassword FROM USER WHERE userID=?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1;	//로그인 성공
				else
					return 0;	//비밀번호 불일치
			}
			return -1;			//아이디 불일치
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;				//데이터베이스 오류
	}
	
	//회원가입 기능 구현
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;				//데이터베이스 오류
	}
}
