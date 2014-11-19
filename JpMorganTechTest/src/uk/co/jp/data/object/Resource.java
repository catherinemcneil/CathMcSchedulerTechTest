package uk.co.jp.data.object;

/**
 * @author Catherine McNeil
 *
 */
public class Resource {

	private Integer groupInProcess;
	private boolean idle = true;

	public void sendMessage(Message message) {
		idle = false;
	}

	public Integer getGroupInProcess() {
		return groupInProcess;
		
	}

	public void setGroupInProgress(int groupId) {
		this.groupInProcess = groupId;
		
	}

	public boolean isIdle() {
		return idle;
	}

	public void messageComplete(Message message) {
		message.complete();
		idle = true;
		groupInProcess = null;
	}

	public void setIdle(boolean idle) {
		this.idle = idle;	
	}

}
