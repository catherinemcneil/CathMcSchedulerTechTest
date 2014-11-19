package uk.co.jp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.jp.data.exception.NoResourcesException;
import uk.co.jp.data.object.Message;
import uk.co.jp.data.object.Resource;

/**
 * @author Catherine McNeil - Concrete class that manages resources, and sends messages
 * 							based on resources available
 *
 */
public class GatewayImpl implements Gateway {

	public static final int numberOfResources = 4;
	ResourceFactory resourceFactory = new ResourceFactory();
	private List<Resource> resources = new ArrayList<Resource>();
	
	
	//Constructor used for unit testing
	public GatewayImpl(int resources) {
		generateResources(resources);
	}
	
	public GatewayImpl() {
		generateResources();
	}
	
	public void generateResources() {
		for(int i = 0; i < numberOfResources; i++){
			getResources().add(resourceFactory.getResource());
		}
	}
	
	public void generateResources(int resources) {
		for(int i = 0; i < resources; i++){
			getResources().add(resourceFactory.getResource());
		}
	}
	
	
	
	public List<Resource> getResources() {
		return resources ;
	}
	
	public void send(Message message) throws NoResourcesException{
		if(!getAvailableResources().isEmpty()){
			Resource resource = getAvailableResources().get(0);
			resource.sendMessage(message);
			resource.setGroupInProgress(message.getGroupId());
			resource.setIdle(false);
		}else{
			throw new NoResourcesException("No available resources at Gateway for message" + message.getId());
		}
	}
	
	public List<Resource> getAvailableResources(){
		List<Resource> resources = new ArrayList<Resource>();
		Iterator<Resource> res= getResources().iterator();
		while(res.hasNext()){
			Resource resource = res.next();
			if(resource.isIdle()){
				resources.add(resource);
			}
		} 
		return resources;
	}
	
	public List<Integer> getGroupsInProgress(){
		List<Integer> groupIDs = new ArrayList<Integer>();
		Iterator<Resource> res= getResources().iterator();
		while(res.hasNext()){
			Resource resource = res.next();
			if(!resource.isIdle()){
				groupIDs.add(resource.getGroupInProcess());
			}
		}
		return groupIDs;
	}

	public void messageComplete(Message message) {
		message.complete();
	}

}
