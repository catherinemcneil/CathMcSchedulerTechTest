package uk.co.jp.object;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.co.jp.data.object.Message;
import uk.co.jp.data.object.Resource;


/**
 * @author Catherine McNeil
 *
 */
public class ResourceTest {

	static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");	
	Resource resource;
	
	@Before
	public void setUp(){
		resource = new Resource();
	}
	
	@Test
	public void markMessageAsCompleteWhenProcessingDone() throws ParseException{
		Message message = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));;
		resource.messageComplete(message);
		assertEquals(message.isComplete(), true);
	}
	
	@Test
	public void markResourceAsIdleWhenProcessingDone() throws ParseException{
		Message message = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));;
		resource.messageComplete(message);
		assertEquals(resource.isIdle(), true);
	}
}
