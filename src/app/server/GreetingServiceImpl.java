package app.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import app.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String login(String id, String pwd) throws Exception{
		System.out.println(("Start.."));
		String session="";
		try{
			session = getSession(id, pwd);//JSESSIONID리턴
			System.out.println(session);
			if(session != null && session.trim().length() != 0){
				System.out.println("LOGIN OK!!");
			}
		}catch(Exception e){
			System.out.println("Failed...");
		}
		return session;
	}
	
	public String getSession(String id, String pwd) throws Exception{
		String jsessionId = "";
		String urlstr = "http://hantalk.hansol.net/j_spring_security_check";
		URL url = new URL(urlstr);
		Properties param = new Properties();
		param.put("j_username", id);
		param.put("j_password", pwd);

		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setUseCaches(false);
		http.setInstanceFollowRedirects(false);

		DataOutputStream out = new DataOutputStream(http.getOutputStream());
		
		String content = "";
		for(Enumeration<Object> e = param.keys(); e.hasMoreElements(); ){
			String key = (String)e.nextElement();
			String value = param.getProperty(key);
			content += key+"="+URLEncoder.encode(value, "UTF-8")+"&";
		}
		out.writeBytes(content);
		out.flush();
		out.close();
		System.out.println(http.getHeaderFields());

		String location = http.getHeaderField("Location");
		if(location.length() > 70){
			int idx1 = location.indexOf("=")+1;
			int idx2 = location.indexOf('?');
			if(idx1 != -1 && idx2 != -1){
				jsessionId = "JSESSIONID="+location.substring(idx1,idx2 ) + ";";
				System.out.println(jsessionId);
			}
		} else {
			jsessionId = http.getHeaderField("set-cookie");
			jsessionId = jsessionId.substring(0, 42);
			System.out.println(jsessionId);
		}
		
		return jsessionId;
	}
	
	public String getAccountInfo(String session, String id)  throws Exception{
		String url = new String("http://hantalk.hansol.net/ajax/getAccountInfo.action");
		Properties param = new Properties();
		param.put("user_id", id);
		System.out.println("in getaccount");
		String json = request(session, url, param);
		//System.out.println("AccountInfo :" +json);
		return json;
	}
	
	public String getTimeline(String session, String offset, String length) throws Exception{
		return getTimeline(session, offset, length, null);
	}
	
	public String getTimeline(String session, String offset, String length,	String group) throws Exception {
		String url = new String("http://hantalk.hansol.net/ajax/getTimeline.action");
		
		Properties param = new Properties();
		param.put("offset",	offset);
		param.put("length", length);
		
		if ( group != null )
			param.put("ug_id", group);
		
		return request(session, url, param);
	}
	
	public String request(String session, String urlstr, Properties param) throws Exception{		
		return request(session, urlstr, param, false);
	}
	
	public String request(String session, String urlstr, Properties param, boolean post) throws Exception{
		String paramStr = "";
		System.out.println("in request");
		if(param != null){
			for(Enumeration<Object> e = param.keys(); e.hasMoreElements(); ){
				String key = (String)e.nextElement();
				String value = param.getProperty(key);
				paramStr += key+"="+URLEncoder.encode(value, "UTF-8")+"&";
			}
		}
		
		URL url = post ? new URL(urlstr) : new URL(urlstr +"?"+paramStr);

		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setUseCaches(false);
		http.setRequestProperty("Cookie", session);
		http.setDoInput(true);
		
		if(post){
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			DataOutputStream out = new DataOutputStream(http.getOutputStream());
			out.writeBytes(paramStr);
			out.flush();
			out.close();

		}else{
			http.setRequestMethod("GET");
			http.connect();			
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
		String result = "";
		String line = "";
		while ((line = in.readLine()) != null) {
			System.out.println(line);
			result +=line;
		}
		System.out.println(result.substring(10, result.length()-1));
		return result.substring(10, result.length()-1);	
	}

	@Override
	public String getExtend(String session, String offset) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReplyAll(String session, String postId, int maxReply) throws Exception {
		// TODO Auto-generated method stub
		String url = new String("http://hantalk.hansol.net/ajax/getReplyAll.action");
		
		Properties param = new Properties();
		param.put("post_id", postId);
		param.put("max", Integer.toString(maxReply));
		
		return request(session, url, param);
	}
	
	public String post(String session, String group, String text, String parent, String via, boolean reply) throws Exception{
		String url = new String("http://hantalk.hansol.net/ajax/post.action");
		Properties param = new Properties();
		param.put("ug_id", group);
		param.put("parent_post_id", parent == null ? "-1" :parent);
		param.put("via", via == null ? "Xwing Demo" : via);
		param.put("profile", "false");
		param.put("post_text", text);

		URL u = new URL(url);
		HttpURLConnection http = (HttpURLConnection) u.openConnection();
		http.setUseCaches(false);
		http.setRequestProperty("Cookie", session);
		http.setDoInput(true);
		
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		String boundary = Long.toHexString(System.currentTimeMillis());
		http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);		
		String charset = "UTF-8";
		PrintWriter writer = null;
		
		try {
		    OutputStream output = http.getOutputStream();
		    writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!
		    
			for(Enumeration<Object> e = param.keys(); e.hasMoreElements(); ){
				String key = (String)e.nextElement();
				String value = param.getProperty(key);
			    writer.println("--" + boundary);
			    writer.println("Content-Disposition: form-data; name=\""+key+"\"");
			    writer.println();
			    writer.println(value);
			}

			writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"upload_file\"; filename=\"\"");
		    writer.println("Content-Type: application/octet-stream");
		    writer.println();
		    writer.println(); // Important! Indicates end of binary boundary.

		    writer.println("--" + boundary+"--");
		    writer.println();
		} finally {
		    if (writer != null) writer.close();
		}
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
		String line = "";
		String result = "";
		
		while ((line = in.readLine()) != null) {
			result +=line;
		}
		
		System.out.println(result.substring(33, result.length()-1));
		http.disconnect();
//		return json;
		return result.substring(33, result.length()-1);	
	}

}
