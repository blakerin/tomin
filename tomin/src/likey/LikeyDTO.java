package likey;

public class LikeyDTO {

	String userEmail;
	int shareID;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getShareID() {
		return shareID;
	}
	public void setShareID(int shareID) {
		this.shareID = shareID;
	}
	
	public LikeyDTO(String userEmail, int shareID) {
		super();
		this.userEmail = userEmail;
		this.shareID = shareID;
	}
}
