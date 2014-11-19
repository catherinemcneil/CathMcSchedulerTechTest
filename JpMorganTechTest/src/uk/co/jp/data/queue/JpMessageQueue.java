package uk.co.jp.data.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import uk.co.jp.data.object.Message;

public class JpMessageQueue extends PriorityQueue<Message>{

	 
	 public static Comparator<Message> messageComparator = new Comparator<Message>(){
         
	        @Override
	        public int compare(Message m1, Message m2) {
	        	if(m1 != null && m2 !=null){
	        	 if (m1.getDate().before(m2.getDate())){
	        		 return -1;
	        	 }else
	        		 return 1;
	        	}
	        	return -1;
	        }
	    };

	public JpMessageQueue(int arg0, Comparator<? super Message> arg1) {
		super(10, messageComparator);
	}

	public JpMessageQueue() {
		super();
		
	}
		
	
}