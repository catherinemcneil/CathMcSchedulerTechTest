package uk.co.jp.queue;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import uk.co.jp.data.object.Message;
import uk.co.jp.data.queue.JpMessageQueue;

/**
 * @author Catherine McNeil
 *
 */
public class JpMessageQueueTest {

	 static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
	  
	@Test
	public void canAddAMessageToMessageQueue(){
		Message message  = new Message(12345, 2);
		JpMessageQueue queue = new JpMessageQueue();
		queue.add(message);
		assertTrue(queue.poll().getGroupId() == 2);
	}
	
	@Test
	public void canAddManyMessagesAndTheyAreOrderedByReceivedDateTime() throws ParseException{
		Message msg1 = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));
		Message msg2 = new Message(12222, 1, (Date) formatter.parse("01/02/14 14:00:00"));
		Message msg3 = new Message(13333, 2, (Date) formatter.parse("01/01/14 13:00:00"));
		Message msg4 = new Message(14444, 3, (Date) formatter.parse("01/01/14 14:30:00"));
		JpMessageQueue queue = new JpMessageQueue(10, JpMessageQueue.messageComparator);
	
		queue.offer(msg1);
		queue.offer(msg2);
		queue.offer(msg3);
		queue.offer(msg4);
		
		assertTrue(queue.poll().getId() == msg3.getId());
		assertTrue(queue.poll().getId() == msg1.getId());
		assertTrue(queue.poll().getId() == msg4.getId());
		assertTrue(queue.poll().getId() == msg2.getId());
		
	}


}
