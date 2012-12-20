package vn.fiosoft.setting.manageaccount;

public class Account {
	
	public static final int SYNC_ERROR = 0;
	public static final int SYNC_ON = 1;
	public static final int SYNC_OFF = 2;
	
	private long pid;
	private String email;
	private int sync;

	public Account(){
		this.pid = 0;
		this.email = "";
		this.sync = 0;		
	}
	
	public Account(long pid, String email, int sync) {
		this.pid = pid;
		this.email = email;
		this.sync = sync;
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

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

}
