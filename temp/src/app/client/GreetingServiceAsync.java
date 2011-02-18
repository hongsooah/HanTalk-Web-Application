package app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void getJson(String id, String pwd, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getAccountInfo(String session, String id, AsyncCallback<String> callback)  throws Exception;
	void getTimeline(String session, String offset, String length, AsyncCallback<String> callback) throws Exception;
	void getTimeline(String session, String offset, String length, String group, AsyncCallback<String> callback) throws Exception;
	void getExtend(String session, String offset, AsyncCallback<String> callback) throws Exception;
	
}
