package app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JSONapp implements EntryPoint {
	
	private static final String JSON_URL = GWT.getModuleBaseURL() + "tempServlet";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private Label textArea;
	private TextBox idField;
	private PasswordTextBox pwdField;
	private VerticalPanel vpanel;
	private Button sendButton;
	private Button infoButton;
	private Button timeButton;
	private HorizontalPanel hpanel;
	private String session="";
	private Image img;
	private Image img2;
	private FlexTable table;
	private FlexTable replyTable;
	private VerticalPanel rowPanel;
	private HorizontalPanel contentPanel;
	private Label content_infoLabel;
	private Label contentLabel;
	private HorizontalPanel replyPanel;
	private Label replyLabel;
	private Button extendButton;
	public static int extend=0;
	public static int length=10;
	private Label loginDisplay;
	private Button groupButton;
	private HorizontalPanel postPanel;
	private TextBox postField;
	private Button postButton;
	
	public void onModuleLoad() {
		
		table = new FlexTable();
		vpanel = new VerticalPanel();
		postPanel = new HorizontalPanel();
		idField = new TextBox();
		pwdField = new PasswordTextBox();
		textArea = new Label();
		sendButton = new Button("Login");
		infoButton = new Button("Info");
		timeButton = new Button("TimeLine");
		groupButton = new Button("group");
		hpanel = new HorizontalPanel();
		extendButton = new Button("Extend");
		loginDisplay = new Label("Enter your account");
		postField = new TextBox();
		postButton = new Button("Post");
		//img = new Image();
		
		table.setWidth("800");
		textArea.setWidth("500");
		textArea.setHeight("400");
		textArea.setVisible(false);
		idField.setText("karroo");
		pwdField.setText("111111");
		extendButton.setVisible(false);
		extendButton.setWidth("800");
		loginDisplay.setVisible(true);
		postPanel.setVisible(false);
		
		postPanel.add(postField);
		postPanel.add(postButton);
		hpanel.add(sendButton);
		hpanel.add(infoButton);
		hpanel.add(timeButton);
		hpanel.add(groupButton);
		vpanel.add(idField);
		vpanel.add(pwdField);
		vpanel.add(hpanel);
		vpanel.add(loginDisplay);
		vpanel.add(postPanel);
		vpanel.add(textArea);
		
		
		RootPanel.get("login").add(vpanel);
		RootPanel.get("extend").add(extendButton);
		
		String url = JSON_URL;
		url = URL.encode(url);
		/*
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

	    try {
	      Request request = builder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          System.out.println(exception.toString());
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode()) {
	        	  //String a = response.getText().replace("for (;;);", "");
	        	  String a = response.getText();
	        	  //a.trim();
	        	  //a = "["+a+"]";
	              goLoopPerData(asArrayOfStockData(a));
	            
	          } else {
	            System.out.println("Couldn't Retrieve JSON (" + response.getStatusText()
	                + ")");
	          }
	          
	        }
	      });
	    } catch (RequestException e) {
	      System.out.println("Couldn't retrieve JSON: " + e.toString());
	    }
	    */
	    /////////////////////event//////////////////////////////////////////
		extendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				length += 10;
				String len = length + "";
				try {
					greetingService.getTimeline(session, "0", len, new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
						}
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							String json = "[" + result + "]";
							goLoopPerData_time(asArrayOfStockData_time(json));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String id = idField.getValue().toString();
				String pwd = pwdField.getValue().toString();
				textArea.setVisible(true); //label
				
				greetingService.getJson(id, pwd, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						//textArea.setText(caught.toString());
						loginDisplay.setText(caught.toString());
					}
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						sendButton.setEnabled(false);
						idField.setEnabled(false);
						pwdField.setEnabled(false);
						session = result;
						loginDisplay.setText("Login OK!");
					}
				});
			}
		});
	    infoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String id = idField.getValue().toString();
				
				try {
					greetingService.getAccountInfo(session, id, new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
						}
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							String json = "[" + result + "]";
							goLoopPerData_info(asArrayOfStockData(json));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    timeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String id = idField.getValue().toString();
				String ex = extend + ""; //0
				String len = length + ""; //20
				try {
					greetingService.getTimeline(session, ex, len, new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
						}
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							String json = "[" + result + "]";
							goLoopPerData_time(asArrayOfStockData_time(json));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    groupButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String id = idField.getValue().toString();
				String ex = extend + ""; //0
				String len = length + ""; //20
				try {
					greetingService.getTimeline(session, ex, len, "testing", new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
						}
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							String json = "[" + result + "]";
							goLoopPerData_time(asArrayOfStockData_time(json));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	   ////////////////////////////////////////////////////////////////////////////////////////
	    
	}
	
	private void goLoopPerData_info(JsArray<Data> data){
		for(int i=0; i<data.length(); i++){
			processData_info(data.get(i));
		}
	}
	
	
	private void goLoopPerData_time(JsArray<Time> time){
		System.out.println(time);
		for(int i=0; i<time.length(); i++){
			processData_time(time.get(i));
		}
	}
	private void processData_info(Data data){
		
		textArea.setText(
				"Id: " + data.getProfileVoList_userId()+"\n"
				+ "Department: "+data.getDeptName()+"\n"
				+ "Email: "+data.getEmail()+"\n"
				+ "Mobile: "+data.getMobile()+"\n"
				+ "Month: "+data.getDtLastLogin_month()+"\n"
				+ "Day: "+data.getDtLastLogin_day()+"\n"
				+ "Date: "+data.getDtLastLogin_date()+"\n"
				);
		
		textArea.setVisible(true);
		table.setVisible(false);
		extendButton.setVisible(false);
		loginDisplay.setVisible(false);
	}

	private void processData_time(Time time){
		int child=0;
		table.clear();
		for(int i=0; i<length*2; i+=2){
			
			rowPanel = new VerticalPanel();
			contentPanel = new HorizontalPanel();
			content_infoLabel = new Label();
			contentLabel = new Label();
			replyTable = new FlexTable();
			//replyPanel = new HorizontalPanel();
			//replyLabel = new Label();
			img = new Image();
			img.setSize("50", "50");
			replyTable.setCellSpacing(20);
			
			img.setUrl("http://hantalk.hansol.net/data-imgs/avatar/default/"+time.getProfileVoList_imgPath(i/2));
			content_infoLabel.setText("Id: "+time.getId(i/2)+"\n"
									+"Department: "+time.getDeptName(i/2)+"\n"
									+"Email: "+time.getEmail(i/2)+"\n");
			contentLabel.setText(time.getPostVo_postText(i/2));
			
			for(child=0; child<5; child++){
				
				replyPanel = new HorizontalPanel();
				img2 = new Image();
				img2.setSize("40", "40");
				replyLabel = new Label();
				
				if(time.getReplyImage(i/2, child).equals("x")){
					break;
				}
				else{
				img2.setUrl("http://hantalk.hansol.net/data-imgs/avatar/default/"+time.getReplyImage(i/2, child));
				replyLabel.setText(time.getReply(i/2,child));
				
				replyPanel.add(img2);
				replyPanel.add(replyLabel);
				replyTable.setWidget(child, 0, replyPanel);
				}
			}
			contentPanel.add(img);
			contentPanel.add(content_infoLabel);
			replyPanel.add(img2);
			replyPanel.add(replyLabel);
			rowPanel.add(contentPanel);
			rowPanel.add(contentLabel);
			//rowPanel.add(replyPanel);
			
			//table.setWidget(i, 0, content_infoLabel);
			table.setWidget(i, 0, rowPanel);
			table.setWidget(i+1, 0, replyTable);
			RootPanel.get("panel").add(table);
			
		}
		
		extendButton.setVisible(true);
		textArea.setVisible(false);
		table.setVisible(true);
		loginDisplay.setVisible(false);
	}
	
	private final native JsArray<Data> asArrayOfStockData(String json) /*-{
	  return eval(json);
	}-*/;
	private final native JsArray<Time> asArrayOfStockData_time(String json) /*-{
	  return eval(json);
	}-*/;	
	
	
}
