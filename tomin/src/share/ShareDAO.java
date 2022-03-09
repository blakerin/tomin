package share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import share.ShareDTO;
import util.DatabaseUtil;

public class ShareDAO {

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
		String SQL = "SELECT shareID FROM share ORDER BY shareID DESC";
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
	
	public int write(ShareDTO shareDTO) {
		String SQL = "INSERT INTO share VALUES (?, ?, ?, ?, 0, ?, ?, ?, 'share')";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, shareDTO.getShareImg().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, shareDTO.getShareContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, shareDTO.getShareDate().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, shareDTO.getUserEmail().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, shareDTO.getUserNick().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(7, shareDTO.getUserImg().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
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
	
	public ArrayList<ShareDTO> getList(int pageNumber) {
		ArrayList<ShareDTO> shareList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "select shareID, shareImg, shareContent, shareDate, likeCount, u.userEmail, u.userNick, u.userImg, boardType from user u, share s where u.userEmail=s.userEmail and shareID < ? ORDER BY shareID DESC LIMIT 10";
			//String SQL = "SELECT * FROM share WHERE shareID < ? ORDER BY shareID DESC LIMIT 10"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			shareList = new ArrayList<ShareDTO>();
			while(rs.next()) {
				ShareDTO share = new ShareDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),//Img
						rs.getString(9)
				);
				shareList.add(share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return shareList;
	}
	
	public ArrayList<ShareDTO> getUserShare1(String userEmail) {
		ArrayList<ShareDTO> shareList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT * FROM share WHERE userEmail=? ORDER BY shareID DESC LIMIT 3"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userEmail);
			rs = pstmt.executeQuery();
			shareList = new ArrayList<ShareDTO>();
			while(rs.next()) {
				ShareDTO share = new ShareDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9)
				);
				shareList.add(share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return shareList;
	}
	
	public ArrayList<ShareDTO> getUserShare2(String userEmail) {
		ArrayList<ShareDTO> shareList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT * FROM share WHERE userEmail=? ORDER BY shareID DESC LIMIT 3,3"; // limit 앞 공백에 주의!! 없으면 에러
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userEmail);
			rs = pstmt.executeQuery();
			shareList = new ArrayList<ShareDTO>();
			while(rs.next()) {
				ShareDTO share = new ShareDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9)
				);
				shareList.add(share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return shareList;
	}
	
	public ArrayList<ShareDTO> shareContentView(int shareID) {
		ArrayList<ShareDTO> shareList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "select shareID, shareImg, shareContent, shareDate, likeCount, u.userEmail, u.userNick, u.userImg, boardType from user u, share s where u.userEmail=s.userEmail and shareID = ?";
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,shareID);
			rs = pstmt.executeQuery();
			shareList = new ArrayList<ShareDTO>();
			while(rs.next()) {
				ShareDTO share = new ShareDTO(
						rs.getInt(1), // select문에서 첫번째 항목 정수형 반환
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9)
				);
				shareList.add(share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}
		return shareList;
	}
	
	public ShareDTO getShareInfo(int shareID) { // 게시글 보기
		String SQL = "SELECT * FROM share WHERE shareID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ShareDTO share = new ShareDTO();
				share.setShareID(rs.getInt(1));
				share.setShareImg(rs.getString(2));
				share.setShareContent(rs.getString(3));
				share.setShareDate(rs.getString(4));
				share.setLikeCount(rs.getInt(5));
				share.setUserEmail(rs.getString(6));
				share.setUserNick(rs.getString(7));
				share.setUserImg(rs.getString(8));
				share.setBoardType(rs.getString(9));
				return share;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn != null) conn.close();} catch (Exception e) { e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch (Exception e) { e.printStackTrace();}
			try {if(rs != null) rs.close();} catch (Exception e) { e.printStackTrace();}
		}return null; // 데이터베이스 오류
	}
	
	public String getShareContent(int shareID) {
		String SQL = "SELECT shareContent FROM share WHERE shareID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
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
		}
		return null;
	}
	
	public int like(int shareID) {
		String SQL = "UPDATE share SET likeCount = likeCount + 1 WHERE shareID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
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
	
	public int update(int shareID, String shareImg, String shareContent) {
		String SQL = "UPDATE share SET shareImg=?, shareContent=? WHERE shareID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, shareImg.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, shareContent.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(3, shareID);
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
	
	public int delete(int shareID) {
		String SQL = "DELETE FROM share WHERE shareID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, shareID);
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
