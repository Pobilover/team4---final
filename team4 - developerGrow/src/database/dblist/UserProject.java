package database.dblist;

public class UserProject {
	private int id;
	private int userId;
	private int infoId;
	private int projectId;
	private boolean proceeding;
	private boolean complete;
	private int lastHour;
	private int lastMin;
	
	public UserProject(int id, int userId, int infoId, int projectId, boolean proceeding, boolean complete, int lastHour, int lastMin) {
		super();
		this.id = id;
		this.userId = userId;
		this.infoId = infoId;
		this.projectId = projectId;
		this.proceeding = proceeding;
		this.complete = complete;
		this.lastHour = lastHour;
		this.lastMin = lastMin;
	}
	
	public UserProject(int userId, int infoId, int projectId, boolean proceeding, boolean complete, int lastHour, int lastMin) {
		super();
		this.userId = userId;
		this.infoId = infoId;
		this.projectId = projectId;
		this.proceeding = proceeding;
		this.complete = complete;
		this.lastHour = lastHour;
		this.lastMin = lastMin;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public int getLastHour() {
		return lastHour;
	}
	public void setLastHour(int lastHour) {
		this.lastHour = lastHour;
	}
	
	public int getLastMin() {
		return lastMin;
	}

	public void setLastMin(int lastMin) {
		this.lastMin = lastMin;
	}
	
	public boolean isProceeding() {
		return proceeding;
	}

	public void setProceeding(boolean proceeding) {
		this.proceeding = proceeding;
	}

	@Override
	public String toString() {
		return "UserProject [id=" + id + ", userId=" + userId + ", infoId=" + infoId + ", projectId=" + projectId
				+ ", proceeding=" + proceeding + ", complete=" + complete + ", lastHour=" + lastHour + ", lastMin="
				+ lastMin + "]";
	}
}
