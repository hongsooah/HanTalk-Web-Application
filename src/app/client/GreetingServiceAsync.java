package app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void login(String id, String pwd, AsyncCallback<String> callback) throws Exception;
	void getAccountInfo(String session, String id, AsyncCallback<String> callback)  throws Exception;
	void getTimeline(String session, String offset, String length, AsyncCallback<String> callback) throws Exception;
	void getTimeline(String session, String offset, String length, String group, AsyncCallback<String> callback) throws Exception;
	void getExtend(String session, String offset, AsyncCallback<String> callback) throws Exception;
	void getSession(String id, String pwd, AsyncCallback<String> asyncCallback)throws Exception;
	void getReplyAll(String session, String postId, AsyncCallback<String> callback);
	void post(String session, String group, String text, String parent,	String via, boolean reply, AsyncCallback<String> callback);

}
