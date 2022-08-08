package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public BbsDAO() {
		
		try {
			
			String dbURL = "jdbc:mariadb://localhost:3306/project?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "sykim300";			
			Class.forName("org.mariadb.jdbc.Driver");
			conn=DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//현재 시간을 가져오는 함수, 게시판에 글을 쓸 때 현재 서버시간을 표시해주는 역할
	public String getDate() {
		
		String SQL = "SELECT NOW()";
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";	//데이터베이스 오류
	}
	
	public int getNext() {
		
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;	//첫번째 게시물인 경우
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;		//데이터베이스 오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		
		String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			//1번은 게시물 번호여야 하니까 getNext()를 사용
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);		//available 삭제 여부 확인
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;			//데이터베이스 오류
	}
	
	//글 목록창 불러오는 함수
	public ArrayList<Bbs> getList(int pageNumber) {
		
		//AVAILABLE이 1인 글만 가져옴
		//위에서 10개의 글까지만 가져옴
		//글 번호를 내림차순으로 함
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? and bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		
		//Bbs 클래스에서 나오는 인스턴스를 보관하는 리스트 생성
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			//글 출력 개수
			pstmt.setInt(1,  getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//페이지 처리 함수
	public boolean nextPage(int pageNumber) {
		
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? and bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		
		//Bbs 클래스에서 나오는 인스턴스를 보관하는 리스트
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			
			//결과가 하나라도 존재한다면 다음페이지로 넘어갈 수 있음
			if (rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//아니라면 false
		return false;
	}
	
	//글 내용을 불러오는 함수
	public Bbs getBbs(int bbsID) {
		
		//특정 게시글의 모든 정보를 가져오는 쿼리문
		String SQL = "SELECT * FROM BBS WHERE bbsID =?";
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				//글에 대한 정보를 담을 객체 생성
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//정보가 없으면 null값 반환
		return null;
	}

}
