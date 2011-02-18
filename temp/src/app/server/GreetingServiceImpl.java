package app.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
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

	public String getJson(String id, String pwd) throws Exception{
		System.out.println(("start.."));
		String session="";
		try{
		session = tempServlet.login(id, pwd);//JSESSIONID리턴
		System.out.println(session);
		if(session != null && session.trim().length() != 0){
			System.out.println("LOGIN OK!!");
			//String json = tempServlet.getAccountInfo(session, id);
//			String json = tempServlet.getTimeline(session, "0");
//			test.getTimeline();
//			test.post("�꾨Т��Xwing 踰좏� 諛쒗몴�뚯뿉��xwing�쇰줈 �쒗넚��援ы쁽��紐⑥뒿��蹂댁뿬 �쒕┫ �щ쭩��蹂댁엯�덈떎.", "9934");
//			HantalkAgent.post(session, "�쒕뵒���쒗넚���レ뿀�듬땲��. Xwing beta 諛쒗몴�뚯뿉��Xwing�쇰줈 �쒗넚���쇰� 援ы쁽��紐⑥뒿��蹂댁뿬 �쒕┫ ���덉쓣 �щ쭩���뺤씤���쒓컙��湲곕줉�⑸땲�� ", "-1", "湲덉슂�����諛� 二쇰쭚�먮룄 異쒓렐���뚯궗");
			//System.out.println(json);
			}
		}catch(Exception e){
			System.out.println("failed...");
		}
		return session;
	}
	public String login(String id, String pwd) throws Exception{
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
		if(location != null){
			int idx1 = location.indexOf("=")+1;
			int idx2 = location.indexOf('?');
			if(idx1 != -1 && idx2 != -1){
				jsessionId = "JSESSIONID="+location.substring(idx1,idx2 ) + ";";
				System.out.println(jsessionId);
			}
		}

		return jsessionId;
	}
	public String getAccountInfo(String session, String id)  throws Exception{
		String url = new String("http://hantalk.hansol.net/ajax/getAccountInfo.action");
		Properties param = new Properties();
		param.put("user_id", id);
		String json = request(session, url, param);
		//System.out.println("AccountInfo :" +json);
		return json;
	}
	
	public String getTimeline(String session, String offset, String length) throws Exception{
		return getTimeline(session, offset, length, null);
	}
	
	public String getTimeline(String session, String offset, String length, String group) throws Exception{
		String url = new String("http://hantalk.hansol.net/ajax/getTimeline.action");
		Properties param = new Properties();
		
		param.put("offset",	offset);
		param.put("length", length);
		if ( group != null){
			System.out.println("in group" + group);
			param.put("ug_id", group);
		} else {
			System.out.println("no group");
		}
			
		
		String json = request(session, url, param);
		return json;
	}
	
	
	
	public static String request(String session, String urlstr, Properties param) throws Exception{
		String json = request(session, urlstr, param, false);
		return json;
	}
	public String getExtend(String session, String offset) throws Exception{
		return "";
	}
	

	public static String request(String session, String urlstr, Properties param, boolean post) throws Exception{
		String paramStr = "";
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
		String line = "";
		String result = "";
		
		while ((line = in.readLine()) != null) {
			System.out.println(line);
			result +=line;
		}
		String json = result;
		return json;
		
}

	
}
