package vn.fiosoft.setting.accountmanage;

public class Account {
	
	public static final int INACTIVATED = 0;
	public static final int ACTIVATED = 1;
	
	
	private long pid;
	private String email;
	private int status;

	public Account(){
		this.pid = 0;
		this.email = "";
		this.status = 0;		
	}
	
	public Account(long pid, String email, int status) {
		this.pid = pid;
		this.email = email;
		this.status = status;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
