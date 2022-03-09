package report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import report.ReportDTO;
import util.DatabaseUtil;

public class ReportDAO {
	
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
		String SQL = "SELECT reportID FROM report ORDER BY reportID DESC";
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
	
	public int write(ReportDTO reportDTO) {
		String SQL = "INSERT INTO report VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, reportDTO.getUserEmail());
			pstmt.setString(3, reportDTO.getReportTarget());
			pstmt.setString(4, reportDTO.getReportReason());
			pstmt.setString(5, reportDTO.getReportContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, reportDTO.getReportAddr());
			pstmt.setString(7, reportDTO.getReportDate());
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
	
	public ArrayList<ReportDTO> getList(int pageNumber) {
		ArrayList<ReportDTO> reportList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT * FROM report WHERE reportID < ? ORDER BY reportID DESC LIMIT 10"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			reportList = new ArrayList<ReportDTO>();
			while(rs.next()) {
				ReportDTO report = new ReportDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
				);
				reportList.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return reportList;
	}
	
	public ReportDTO getReportView(int reportID) { // 게시글 보기
		String SQL = "SELECT * FROM report WHERE reportID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reportID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ReportDTO report = new ReportDTO();
				report.setReportID(rs.getInt(1));
				report.setUserEmail(rs.getString(2));
				report.setReportTarget(rs.getString(3));
				report.setReportReason(rs.getString(4));
				report.setReportContent(rs.getString(5));
				report.setReportAddr(rs.getString(6));
				report.setReportDate(rs.getString(7));
				return report;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return null; // 데이터베이스 오류
	}
	
	
	public int delete(int reportID) {
		String SQL = "DELETE FROM report WHERE reportID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reportID);
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
}
