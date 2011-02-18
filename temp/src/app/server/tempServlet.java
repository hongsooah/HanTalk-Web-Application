package app.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class tempServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println(("start.."));
		try{
		String session = tempServlet.login("karroo", "111111");//JSESSIONID리턴
		System.out.println(session);
		if(session != null && session.trim().length() != 0){
			System.out.println("LOGIN OK!!");
			String json = tempServlet.getAccountInfo(session, "karroo");
			//String json = tempServlet.getTimeline(session, "0");
//			test.getTimeline();
//			test.post("�꾨Т��Xwing 踰좏� 諛쒗몴�뚯뿉��xwing�쇰줈 �쒗넚��援ы쁽��紐⑥뒿��蹂댁뿬 �쒕┫ �щ쭩��蹂댁엯�덈떎.", "9934");
			//HantalkAgent.post(session, "�쒕뵒���쒗넚���レ뿀�듬땲��. Xwing beta 諛쒗몴�뚯뿉��Xwing�쇰줈 �쒗넚���쇰� 援ы쁽��紐⑥뒿��蹂댁뿬 �쒕┫ ���덉쓣 �щ쭩���뺤씤���쒓컙��湲곕줉�⑸땲�� ", "-1", "湲덉슂�����諛� 二쇰쭚�먮룄 異쒓렐���뚯궗");
			//System.out.println(json);
			//System.out.println(json.length());
			//String res = json.substring(10, 798);
			PrintWriter out = resp.getWriter();
			
			//out.println(res+"}]");
			out.println("["+json+"]");
			//out.println("[{\"name\":\"hongsooah\",\"phone\":\"0109998888\",\"date\":{\"year\":\"2011\",\"month\":\"2\"}},{\"name\":\"kimnana\",\"phone\":\"0102223333\",\"date\":{\"year\":\"2011\",\"month\":\"1\"}}]");
			//out.println("[{\"accountType\":\"USER\",\"deptName\":\"R&D???\",\"dtLastLogin\":{\"date\":14,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":1,\"seconds\":0,\"time\":1297609200000,\"timezoneOffset\":-540,\"year\":111},\"dtLastPost\":{\"date\":28,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":0,\"seconds\":0,\"time\":1296140400000,\"timezoneOffset\":-540,\"year\":111},\"email\":\"karroo@hansol.com\",\"enable\":\"YES\",\"favProfile\":\"USER\",\"lastPostId\":10606,\"mobile\":\"01022972633\",\"password\":\"3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d\",\"phone\":\"02-6005-3847\",\"positionName\":\"???\",\"postCount\":46,\"profileSize\":2,\"profileVoList\":{\"imgPath\":\"karroo.png\",\"profileDesc\":\"?????,\"profileType\":\"DEFAULT\",\"userId\":\"karroo\",\"userName\":\"?????},\"sabun\":\"087619\",\"userId\":\"karroo\"}]");
			//out.println("[{\"result\":[{\"accountType\":\"USER\",\"deptName\":\"R&D파트\",\"dtLastLogin\":{\"date\":14,\"day\":1,\"hours\":0,\"minutes\":0,\"month\":1,\"seconds\":0,\"time\":1297609200000,\"timezoneOffset\":-540,\"year\":111},\"dtLastPost\":{\"date\":28,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":0,\"seconds\":0,\"time\":1296140400000,\"timezoneOffset\":-540,\"year\":111},\"email\":\"karroo@hansol.com\",\"enable\":\"YES\",\"favProfile\":\"USER\",\"lastPostId\":10606,\"mobile\":\"01022972633\",\"password\":\"3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d\",\"phone\":\"02-6005-3847\",\"positionName\":\"???\",\"postCount\":46,\"profileSize\":2,\"profileVoList\":[{\"imgPath\":\"karroo.png\",\"profileDesc\":\"?????\",\"profileType\":\"DEFAULT\",\"userId\":\"karroo\",\"userName\":\"?????\"},{\"imgPath\":\"\",\"profileDesc\":\"dslfk\",\"profileType\":\"USER\",\"userId\":\"karroo\",\"userName\":\"fsdlkj\"}]}]}]");
			//out.println("[{\"result\":[{\"name\":\"hongsooah\",\"age\":\"21\",\"date\":[{\"month\":\"3\"}]}]}]");
		}
		}catch(Exception e){
			System.out.println("failed...");
		}
	}
		public static String login(String id, String pwd) throws Exception{
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
		
		public static String getAccountInfo(String session, String id)  throws Exception{
			String url = new String("http://hantalk.hansol.net/ajax/getAccountInfo.action");
			Properties param = new Properties();
			param.put("user_id", id);
			String json = request(session, url, param);
			//System.out.println("AccountInfo :" +json);
			return json;
		}
		
		public static String getTimeline(String session, String offset) throws Exception{
			String url = new String("http://hantalk.hansol.net/ajax/getTimeline.action");
			Properties param = new Properties();
			param.put("offset",	offset);
			String json = request(session, url, param);
			return json;
		}
		
		
		
		public static String request(String session, String urlstr, Properties param) throws Exception{
			String json = request(session, urlstr, param, false);
			return json;
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
		
	public static Boolean post(String session, String group, String text, String parent) throws Exception{
		try {
			String url = new String("http://hantalk.hansol.net/ajax/post.action");
			Properties param = new Properties();
			param.put("ug_id", group);
			param.put("parent_post_id", parent == null ? "-1" :parent);
			param.put("via", "web application");
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
		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
