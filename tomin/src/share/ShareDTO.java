package share;

public class ShareDTO {
	int shareID;
	String shareImg;
	String shareContent;
	String shareDate;
	int likeCount;
	String userEmail;
	String userNick;
	String userImg;
	String boardType;
	
	public int getShareID() {
		return shareID;
	}
	public void setShareID(int shareID) {
		this.shareID = shareID;
	}
	public String getShareImg() {
		return shareImg;
	}
	public void setShareImg(String shareImg) {
		this.shareImg = shareImg;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getShareDate() {
		return shareDate;
	}
	public void setShareDate(String shareDate) {
		this.shareDate = shareDate;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	
	public ShareDTO() {
		
	}
	
	public ShareDTO(int shareID, String shareImg, String shareContent, String shareDate, int likeCount,
			String userEmail, String userNick, String userImg, String boardType) {
		super();
		this.shareID = shareID;
		this.shareImg = shareImg;
		this.shareContent = shareContent;
		this.shareDate = shareDate;
		this.likeCount = likeCount;
		this.userEmail = userEmail;
		this.userNick = userNick;
		this.userImg = userImg;
		this.boardType = boardType;
	}
	
}
