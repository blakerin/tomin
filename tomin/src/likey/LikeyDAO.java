package likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class LikeyDAO {

	public int like(String userEmail, int shareID) {
		String SQL = "INSERT INTO likey VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL); 
			pstmt.setString(1, userEmail);
			pstmt.setInt(2, shareID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return -1; // 추천 중복 오류
	}
	
	public int deleteAll(int shareID) {
		String SQL = "DELETE FROM likey WHERE shareID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
			// pstmt.setInt(1, Integer.parseInt(freeID));
			return pstmt.executeUpdate(); // 실행한 결과 반환
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return -1; // 오류
	}
	
	public int likeCheck(int shareID, String userEmail) {
		String SQL = "SELECT * FROM likey WHERE shareID = ? AND userEmail = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
			pstmt.setString(2, userEmail);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userEmail)) {
					return 1; // 추천누름
				}
			}
			return -1; // 추천안누름
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return -2; // 데이터베이스 오류
	}
}
