package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import schedule.ScheduleDTO;
import share.ShareDTO;
import util.DatabaseUtil;

public class ScheduleDAO {

	public String getDate() { // 현재 시간 가져오기
		String SQL = "SELECT NOW()";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return ""; // 데이터베이스 오류
	}
	
	public int getNext() { // 게시글 번호
		String SQL = "SELECT scheduleID FROM schedule ORDER BY scheduleID DESC";
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
	
	public int write(ScheduleDTO scheduleDTO) {
		String SQL = "INSERT INTO schedule VALUES (?, ?, ?, ?, ?, 'schedule')";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, scheduleDTO.getScheduleTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, scheduleDTO.getScheduleContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, scheduleDTO.getScheduleDate().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, scheduleDTO.getUserEmail().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
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
	
	public ArrayList<ScheduleDTO> getList(int pageNumber, String userEmail) {
		ArrayList<ScheduleDTO> scheduleList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT scheduleID, scheduleTitle, scheduleContent, date_format(scheduleDate, '%Y-%m-%d') as scheduleDate, userEmail, boardType FROM schedule WHERE scheduleID < ? AND userEmail = ? ORDER BY scheduleID DESC LIMIT 10"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			pstmt.setString(2, userEmail);
			rs = pstmt.executeQuery();
			scheduleList = new ArrayList<ScheduleDTO>();
			while(rs.next()) {
				ScheduleDTO schedule = new ScheduleDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)
				);
				scheduleList.add(schedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return scheduleList;
	}
	
	public ScheduleDTO getScheduleInfo(int scheduleID) { // 게시글 보기
		String SQL = "SELECT scheduleID, scheduleTitle, scheduleContent, date_format(scheduleDate, '%Y-%m-%d') as scheduleDate, userEmail, boardType FROM schedule WHERE scheduleID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scheduleID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ScheduleDTO schedule = new ScheduleDTO();
				schedule.setScheduleID(rs.getInt(1));
				schedule.setScheduleTitle(rs.getString(2));
				schedule.setScheduleContent(rs.getString(3));
				schedule.setScheduleDate(rs.getString(4));
				schedule.setUserEmail(rs.getString(5));
				schedule.setBoardType(rs.getString(6));
				return schedule;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return null; // 데이터베이스 오류
	}
	
	public int update(int scheduleID, String scheduleTitle, String scheduleContent) {
		String SQL = "UPDATE schedule SET scheduleTitle=?, scheduleDate=? WHERE scheduleID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, scheduleTitle.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, scheduleContent.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(3, scheduleID);
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
	
	public int delete(int scheduleID) {
		String SQL = "DELETE FROM schedule WHERE scheduleID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scheduleID);
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
