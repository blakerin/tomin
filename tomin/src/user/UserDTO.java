package user;

public class UserDTO {
	
	private String userEmail;
	private String userNick;
	private String userPassword;
	private String userImg;
	private String userIntro;
	private String userEmailHash;
	private boolean userEmailChecked;
	
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserIntro() {
		return userIntro;
	}
	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}
	public String getUserEmailHash() {
		return userEmailHash;
	}
	public void setUserEmailHash(String userEmailHash) {
		this.userEmailHash = userEmailHash;
	}
	public boolean isUserEmailChecked() {
		return userEmailChecked;
	}
	public void setUserEmailChecked(boolean userEmailChecked) {
		this.userEmailChecked = userEmailChecked;
	}
	
	public UserDTO() {
	}
	
	public UserDTO(String userEmail, String userNick, String userPassword, String userImg, String userIntro,
			String userEmailHash, boolean userEmailChecked) {
		super();
		this.userEmail = userEmail;
		this.userNick = userNick;
		this.userPassword = userPassword;
		this.userImg = userImg;
		this.userIntro = userIntro;
		this.userEmailHash = userEmailHash;
		this.userEmailChecked = userEmailChecked;
	}
	

}
