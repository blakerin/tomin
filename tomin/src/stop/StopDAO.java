package stop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import stop.StopDTO;
import util.DatabaseUtil;

public class StopDAO {
	
	public int getNext() { // 게시글 번호
		String SQL = "SELECT stopID FROM stop ORDER BY stopID DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1; // 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return -1; // 데이터베이스 오류
	}
	
	public int stop(StopDTO stopDTO) {
		String SQL = "INSERT INTO stop VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setInt(2, stopDTO.getStopDays());
			pstmt.setString(3, stopDTO.getUserEmail());
			pstmt.setString(4, stopDTO.getStopDate());
			pstmt.setString(5, stopDTO.getStopDateEnd());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<StopDTO> getList(int pageNumber) {
		ArrayList<StopDTO> stopList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT * FROM stop WHERE stopID < ? ORDER BY stopID DESC LIMIT 10"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			stopList = new ArrayList<StopDTO>();
			while(rs.next()) {
				StopDTO stop = new StopDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)
				);
				stopList.add(stop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return stopList;
	}
	
	public StopDTO getStopView(String userEmail) { // 게시글 보기
		String SQL = "SELECT * FROM stop WHERE userEmail=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				StopDTO stop = new StopDTO();
				stop.setStopID(rs.getInt(1));
				stop.setStopDays(rs.getInt(2));
				stop.setUserEmail(rs.getString(3));
				stop.setStopDate(rs.getString(4));
				stop.setStopDateEnd(rs.getString(5));
				return stop;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return null; // 데이터베이스 오류
	}
	
	public int delete(String userEmail) {
		String SQL = "DELETE FROM stop WHERE userEmail = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userEmail);
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
	
	public int delete(int stopID) {
		String SQL = "DELETE FROM stop WHERE stopID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, stopID);
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
}
