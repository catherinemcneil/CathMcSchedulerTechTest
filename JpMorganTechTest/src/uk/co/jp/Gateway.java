package uk.co.jp;

import uk.co.jp.data.exception.NoResourcesException;
import uk.co.jp.data.object.Message;

/**
 * @author Catherine McNeil
 *
 */
public interface Gateway {

	/**
	 * @param message
	 */
	public void messageComplete(Message message);
	/**
	 * @param msg
	 * @throws NoResourcesException 
	 */
	public void send(Message msg) throws NoResourcesException;

}
