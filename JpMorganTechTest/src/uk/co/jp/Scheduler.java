package uk.co.jp;

import java.util.Iterator;
import java.util.List;

import uk.co.jp.data.exception.NoResourcesException;
import uk.co.jp.data.object.Message;
import uk.co.jp.data.object.Resource;
import uk.co.jp.data.queue.JpMessageQueue;

/**
 * @author Catherine McNeil - Class responsible for managing and polling queue.
 * 							Also determines from Gateway which groups are in process
 *
 */
public class Scheduler {

	private JpMessageQueue queue;
	private GatewayImpl gateway;
	private Message latestMessage;
	private int cancelledGroup;

	public Scheduler(JpMessageQueue queue, GatewayImpl gateway) {
		this.queue = queue;
		this.gateway = gateway;
	}

	public void run() {
		selectNextMessage();
	}

	public void selectNextMessage() {
		List<Integer> groupIDs = gateway.getGroupsInProgress();
		Message message = queue.poll();
			if (!groupCancelled(message)) {
				Iterator<Integer> groups = groupIDs.iterator();
				while (groups.hasNext())
					if (message.getGroupId() == groups.next().intValue()) {			
						try {
							gateway.send(message);
							setLatestMessage(message);
						} catch (NoResourcesException e) {
							//Cannot send due to no resources
							queue.add(message);
							break;
						}
					}else{
						selectNewGroupAndMessageToProcess(message);
					}
				selectNewGroupAndMessageToProcess(message);
			}
	}

	private boolean groupCancelled(Message message) {
		if (message.getGroupId() == cancelledGroup) {
			return true;
		} else
			return false;
	}

	private void selectNewGroupAndMessageToProcess(Message message) {
		try {
			gateway.send(message);
			setLatestMessage(message);
		} catch (NoResourcesException e) {
			//Add it back onto queue
			queue.add(message);
			e.printStackTrace();
		}
	}

	public Message getLatestMessage() {
		return latestMessage;
	}

	public void setLatestMessage(Message latestMessage) {
		this.latestMessage = latestMessage;
	}

	public void cancelGroup(int group) {
		this.cancelledGroup = group;
	}

	public void addNewMessage(Message message) {
		queue.add(message);
		
	}
}
