package report;

public class ReportDTO {

	int reportID;
	String userEmail;
	String reportTarget;
	String reportReason;
	String reportContent;
	String reportAddr;
	String reportDate;
	
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getReportTarget() {
		return reportTarget;
	}
	public void setReportTarget(String reportTarget) {
		this.reportTarget = reportTarget;
	}
	public String getReportReason() {
		return reportReason;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportAddr() {
		return reportAddr;
	}
	public void setReportAddr(String reportAddr) {
		this.reportAddr = reportAddr;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	
	public ReportDTO() {
		
	}
	
	public ReportDTO(int reportID, String userEmail, String reportTarget, String reportReason, String reportContent,
			String reportAddr, String reportDate) {
		super();
		this.reportID = reportID;
		this.userEmail = userEmail;
		this.reportTarget = reportTarget;
		this.reportReason = reportReason;
		this.reportContent = reportContent;
		this.reportAddr = reportAddr;
		this.reportDate = reportDate;
	}
}
