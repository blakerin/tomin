package notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import notice.NoticeDTO;
import util.DatabaseUtil;

public class NoticeDAO {

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
		String SQL = "SELECT noticeID FROM notice ORDER BY noticeID DESC";
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
	
	public int write(NoticeDTO noticeDTO) {
		String SQL = "INSERT INTO notice VALUES (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, noticeDTO.getNoticeTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, noticeDTO.getNoticeContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, noticeDTO.getNoticeDate().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
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
	
	public ArrayList<NoticeDTO> getList(int pageNumber) {
		ArrayList<NoticeDTO> noticeList = null;
		String SQL = "SELECT * FROM notice WHERE noticeID < ? ORDER BY noticeID DESC LIMIT 10";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			noticeList = new ArrayList<NoticeDTO>();
			while(rs.next()) {
				NoticeDTO notice = new NoticeDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4)
				);
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return noticeList;
	}
	
	public NoticeDTO getNoticeView(int noticeID) { // 게시글 보기
		String SQL = "SELECT * FROM notice WHERE noticeID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, noticeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				NoticeDTO notice = new NoticeDTO();
				notice.setNoticeID(rs.getInt(1));
				notice.setNoticeTitle(rs.getString(2));
				notice.setNoticeContent(rs.getString(3));
				notice.setNoticeDate(rs.getString(4));
				return notice;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return null; // 데이터베이스 오류
	}
	
	public int update(int noticeID, String noticeTitle, String noticeContent) {
		String SQL = "UPDATE notice SET noticeTitle=?, noticeContent=? WHERE noticeID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, noticeTitle.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, noticeContent.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(3, noticeID);
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
	
	public int delete(int noticeID) {
		String SQL = "DELETE FROM notice WHERE noticeID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, noticeID);
			// pstmt.setInt(1, Integer.parseInt(noticeID));
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
