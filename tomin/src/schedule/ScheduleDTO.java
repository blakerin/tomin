package schedule;

public class ScheduleDTO {
	int scheduleID;
	String scheduleTitle;
	String scheduleContent;
	String scheduleDate;
	String userEmail;
	String boardType;
	public int getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	public String getScheduleTitle() {
		return scheduleTitle;
	}
	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	public String getScheduleContent() {
		return scheduleContent;
	}
	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	
	public ScheduleDTO() {
		
	}
	
	
	public ScheduleDTO(int scheduleID, String scheduleTitle, String scheduleContent, String scheduleDate,
			String userEmail, String boardType) {
		super();
		this.scheduleID = scheduleID;
		this.scheduleTitle = scheduleTitle;
		this.scheduleContent = scheduleContent;
		this.scheduleDate = scheduleDate;
		this.userEmail = userEmail;
		this.boardType = boardType;
	}

	
}
