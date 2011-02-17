package app.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	String login(String id, String pwd) throws Exception;
	String getAccountInfo(String session, String id)  throws Exception;
	String getTimeline(String session, String offset, String length) throws Exception;
	String getTimeline(String session, String offset, String length, String group) throws Exception;
	String getExtend(String session, String offset) throws Exception;
	String getSession(String id, String pwd) throws Exception;
	String getReplyAll(String session, String postId) throws Exception;
	String post(String session, String group, String text, String parent, String via ,boolean reply) throws Exception;
}
