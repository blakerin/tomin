package stop;

public class StopDTO {

	int stopID;
	int stopDays;
	String userEmail;
	String stopDate;
	String stopDateEnd;
	
	
	public int getStopID() {
		return stopID;
	}
	public void setStopID(int stopID) {
		this.stopID = stopID;
	}
	public int getStopDays() {
		return stopDays;
	}
	public void setStopDays(int stopDays) {
		this.stopDays = stopDays;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public String getStopDateEnd() {
		return stopDateEnd;
	}
	public void setStopDateEnd(String stopDateEnd) {
		this.stopDateEnd = stopDateEnd;
	}

	public StopDTO() {
		
	}
	
	public StopDTO(int stopID, int stopDays, String userEmail, String stopDate, String stopDateEnd) {
		super();
		this.stopID = stopID;
		this.stopDays = stopDays;
		this.userEmail = userEmail;
		this.stopDate = stopDate;
		this.stopDateEnd = stopDateEnd;
	}
	
	
}
