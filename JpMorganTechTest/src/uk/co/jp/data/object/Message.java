package uk.co.jp.data.object;

import java.util.Date;

/**
 * @author Catherine McNeil
 *
 */
public class Message {

	private int id;
	private int groupId;
	private Date date;
	private boolean complete;

	public Message(int id, int groupId) {
		this.id = id;
		this.groupId = groupId;
	}

	public Message(int id, int groupId, Date date) {
		this.id = id;
		this.groupId = groupId;
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
		
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
		
	}
	
	public int getGroupId(){
		return groupId;
	}
	
	public int getId(){
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void complete() {
		this.complete = true;
		
	}

	public boolean isComplete() {
		return complete;
	}

}
