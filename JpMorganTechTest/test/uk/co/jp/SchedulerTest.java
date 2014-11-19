package uk.co.jp;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.co.jp.data.object.Message;
import uk.co.jp.data.queue.JpMessageQueue;


/**
 * @author Catherine McNeil
 *
 */
public class SchedulerTest {
	
	static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");

	GatewayImpl gateway;
	JpMessageQueue queue;
	Scheduler scheduler;
	
	@Before
	public void setUp(){
		gateway = new GatewayImpl(8);
		queue = new JpMessageQueue(10, JpMessageQueue.messageComparator );
		scheduler = new Scheduler(queue, gateway);
	}
	
	@Test
	public void canAddMessageToQueue() throws ParseException{
		Message message = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));
		scheduler.addNewMessage(message);
		assertTrue(queue.contains(message));		
	}
	
	@Test
	public void canProcessMessagesByReceivedDateAndGroupIdsAlreadyInProcess() throws ParseException{
		Message msg1 = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));
		Message msg2 = new Message(12222, 1, (Date) formatter.parse("01/02/14 14:00:00"));
		Message msg3 = new Message(13333, 2, (Date) formatter.parse("01/01/14 13:00:00"));
		Message msg4 = new Message(14444, 3, (Date) formatter.parse("01/01/14 14:30:00"));
		Message msg5 = new Message(15555, 2, (Date) formatter.parse("02/01/14 18:30:00"));
		Message msg6 = new Message(16666, 1, (Date) formatter.parse("02/01/14 00:30:00"));
		Message msg7 = new Message(17777, 1, (Date) formatter.parse("01/01/14 21:30:00"));
		Message msg8 = new Message(18888, 3, (Date) formatter.parse("01/01/14 14:40:00"));
		
		queue.add(msg1);
		queue.add(msg2);
		queue.add(msg3);
		queue.add(msg4);
	
		scheduler.run();
		
		assertTrue(scheduler.getLatestMessage().getId() == 13333);
		
		queue.add(msg5);
		queue.add(msg6);
		queue.add(msg7);
		queue.add(msg8);
		
		scheduler.run();
		assertTrue(scheduler.getLatestMessage().getId() == 11111);
		
		queue.clear();
	}
	
	@Test
	public void shouldNotProcessCancelledGroups() throws ParseException{
		gateway = new GatewayImpl(5);
		scheduler = new Scheduler(queue, gateway);
		Message msg1 = new Message(12222, 1, (Date) formatter.parse("01/02/14 14:00:00"));
		Message msg2 = new Message(13333, 3, (Date) formatter.parse("02/02/14 13:00:00"));

		
		queue.add(msg1);
		queue.add(msg2);
		
		scheduler.cancelGroup(3);
		scheduler.run();
		
		assertTrue(scheduler.getLatestMessage().getId() == 12222);
		
		
	}
}
