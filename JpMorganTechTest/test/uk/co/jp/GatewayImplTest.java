package uk.co.jp;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.co.jp.data.exception.NoResourcesException;
import uk.co.jp.data.object.Message;

public class GatewayImplTest {
	
	static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
	
	GatewayImpl gateway;
	
	@Before
	public void setUp(){
		gateway = new GatewayImpl(1);
	}
	
	@Test
	public void canSendMessageIfResourceAvailable() throws ParseException{
		Message message = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));
		try {
			gateway.send(message);
			assertEquals(gateway.getAvailableResources().size(), 0);
		} catch (NoResourcesException e) {
			
			e.printStackTrace();
		}
	}
	@Test
	public void messageWillNotBeSentIfNoResourcesAvailable() throws ParseException{
		gateway = new GatewayImpl(0);
		Message message = new Message(11111, 2, (Date) formatter.parse("01/01/14 14:00:00"));
		try {
			gateway.send(message);
			fail();
		} catch (NoResourcesException e) {
			
			e.printStackTrace();
		}
		
	}
}
