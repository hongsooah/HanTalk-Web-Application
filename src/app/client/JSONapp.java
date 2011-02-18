package app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class JSONapp implements EntryPoint {
	
	private static final String JSON_URL = GWT.getModuleBaseURL() + "tempServlet";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private Label textArea;
	private TextBox idField;
	private PasswordTextBox pwdField;
	private VerticalPanel loginPanel;
	private Button loginButton;
	private Button infoButton;
	private Button homeButton;
	private HorizontalPanel hpanel;
	private String session="";
	private Image img;
	private Image img2;
	private FlexTable table;
	private FlexTable replyTable;
	private HorizontalPanel rowPanel;
	private HorizontalPanel contentPanel;
	private Label nameLabel;
	private Label textLabel;
	private Label replyLabel;
	private Button expandButton;
	public static int extend=0;
	public static int length=10;
	private Label loginDisplay;
	private VerticalPanel middlePanel;
	private VerticalPanel buttonPanel;
	private FlowPanel textPanel;
	private InlineLabel showAllReply;
	private Button groupButton;
	private String currentGroup;
	private FlowPanel postPanel;
	private TextBox postTextBox;
	private Button postButton;
	private HorizontalPanel replyPanel;
	private FlexTable moreReplyTable;
	private FlexTable defaultReplyTable;
	private FlowPanel replyPostPanel;
	private String postId;
	private VerticalPanel replyMorePanel;
	
	public void onModuleLoad() {
		
		/*Initialize Variables*/
		table = new FlexTable();
		loginPanel = new VerticalPanel();
		idField = new TextBox();
		pwdField = new PasswordTextBox();
		textArea = new Label();
		loginButton = new Button("Login");
		infoButton = new Button("Info");
		homeButton = new Button("Home");
		groupButton = new Button("Group");
		hpanel = new HorizontalPanel();
		expandButton = new Button("Expand");
		loginDisplay = new Label("Enter your account");
		buttonPanel = new VerticalPanel();
		middlePanel = new VerticalPanel();
		postPanel = new FlowPanel();
		
		/*Add To Panel*/
		loginPanel.add(loginDisplay);
		loginPanel.add(idField);
		loginPanel.add(pwdField);
		loginPanel.add(loginButton);
		hpanel.add(infoButton);
		hpanel.add(homeButton);
		hpanel.add(groupButton);
		buttonPanel.add(hpanel);
		middlePanel.add(textArea);
		middlePanel.add(postPanel);
		middlePanel.add(table);
		
		/*Set Properties*/
		table.setWidth("600");
		textArea.setWidth("500");
		textArea.setHeight("400");
		textArea.setText("Well Come!!");
		idField.setText("karroo");
		pwdField.setText("111111");
		expandButton.setWidth("600");
		loginPanel.setVisible(true);
		buttonPanel.setVisible(false);
		textArea.setVisible(false);
		table.setVisible(false);
		expandButton.setVisible(false);

		//
		postTextBox = new TextBox();
		postTextBox.setSize("600", "25");
		postTextBox.setText("나누고 싶은 hantalk?");
		postButton = new Button("Post");
		
		postPanel.add(postTextBox);
		postPanel.add(postButton);
		postButton.setVisible(false);
		
		postPanel.setVisible(false);
		//
		
		/*Set Style*/
		loginPanel.setStyleName("panel");
		buttonPanel.setStyleName("panel");
		middlePanel.setStyleName("panel");
		
		/*Add To RootPanel*/
		RootPanel.get("login").add(loginPanel);
		RootPanel.get("button").add(buttonPanel);
		RootPanel.get("content").add(middlePanel);
		RootPanel.get("expand").add(expandButton);
		
			
	    /*Button Event*/
	    loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String id = idField.getValue().toString();
				String pwd = pwdField.getValue().toString();
				//textArea.setVisible(true); //label
				
				try {
					greetingService.getSession(id, pwd, new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							//textArea.setText(caught.toString());
							loginDisplay.setText(caught.toString());
						}
						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							session = result;
							//Login OK!!//
							
							loginPanel.setVisible(false);
							buttonPanel.setVisible(true);
							textArea.setVisible(true);
							table.setVisible(false);
							expandButton.setVisible(false);
						}
					});
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    
	    infoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String id = idField.getValue().toString();
				System.out.println("click ok");
				try {
					greetingService.getAccountInfo(session, id, new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							System.out.println(caught);
							textArea.setText(caught.toString());
						}
						public void onSuccess(String result) {
							process_UserInfo(jsonUserInfoArray(result));
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
				setWaitPost();
				try {
					greetingService.getTimeline(session, "0", "20", "testing", new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
							System.out.println(caught);
						}
						public void onSuccess(String result) {
							currentGroup = "testing";
							process_Timeline(jsonTimelineArray(result));
							
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
				
	    homeButton.addClickHandler(new ClickHandler() {
	    	
			public void onClick(ClickEvent event) {
				setWaitPost();
				System.out.println("click ok");
				try {
					greetingService.getTimeline(session, "0", "20", new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							textArea.setText(caught.toString());
							System.out.println(caught);
						}
						public void onSuccess(String result) {
							System.out.println("click ok");
							process_Timeline(jsonTimelineArray(result));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    
	    expandButton.addClickHandler(new ClickHandler() {
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
							process_Timeline(jsonTimelineArray(result));
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    
	    postTextBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setWritePost();
				
			}
		});
		
		postButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					greetingService.post(session, currentGroup, postTextBox.getValue().toString(), null, "WEB", false, new AsyncCallback<String>() {
						
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							try {
								greetingService.getTimeline(session, "0", "20", currentGroup, new AsyncCallback<String>(){

									public void onFailure(Throwable caught) {
										// Show the RPC error message to the user
										textArea.setText(caught.toString());
										System.out.println(caught);
									}
									public void onSuccess(String result) {
										currentGroup = "testing";
										process_Timeline(jsonTimelineArray(result));
										setWaitPost();
									}
								});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
	}// end ModuleLoad Function
	
	/*User-Infomation*/
		
	private void process_UserInfo(JsArray<UserInfo> array){
		System.out.println(array);
		UserInfo user = array.get(0);
		System.out.println(user);
		textArea.setText(
				"Id: " + user.getProfileVoList_userId()+"\n"
				+ "Department: "+user.getDeptName()+"\n"
				+ "Email: "+user.getEmail()+"\n"
				+ "Mobile: "+user.getMobile()+"\n"
				+ "Month: "+user.getDtLastLogin_month()+"\n"
				+ "Day: "+user.getDtLastLogin_day()+"\n"
				+ "Date: "+user.getDtLastLogin_date()+"\n"
				);
		
		loginPanel.setVisible(false);
		buttonPanel.setVisible(true);
		textArea.setVisible(true);
		table.setVisible(false);
		expandButton.setVisible(false);
		postPanel.setVisible(false);
	}
	
	/*Timeline*/
		
	private void process_Timeline(JsArray<Timeline> array){
		
		postPanel.setVisible(true);
				
		table.clear();
		
		for(int i=0; i<array.length(); i++){
			final Timeline result = array.get(i);
			
			/*Initialize Variables*/
			rowPanel = new HorizontalPanel();
			contentPanel = new HorizontalPanel();
			textPanel = new FlowPanel();
			replyPanel = new HorizontalPanel();
			
			nameLabel = new InlineLabel(result.getAccount() + " ");
			textLabel = new InlineLabel(result.getPostText());
			
			replyTable = new FlexTable();
			replyMorePanel = new VerticalPanel();
			
			defaultReplyTable = new FlexTable();
			replyPostPanel = new FlowPanel();
			
			/*Set Properties*/
			
			replyTable.setWidget(1, 0, defaultReplyTable);
			replyTable.setWidget(2, 0, replyPostPanel);
			replyTable.setCellSpacing(1);
			
			img = new Image(checkNoImg(result.getImgPath()));
			img.setSize("50", "50");
			
			
			/* Set Reply Table */
			int replyCount = result.getReplyCount();
			if ( replyCount > 3 ) {
				moreReplyTable = new FlexTable();
				HorizontalPanel replyAllPanel = new HorizontalPanel();
				
				showAllReply = new InlineLabel((replyCount - 3) + "개의 댓글 모두 보기");
				addClickEvent(showAllReply, result, moreReplyTable);
				
				replyAllPanel.add(showAllReply);
				moreReplyTable.setWidget(0, 0, replyAllPanel);
				replyTable.setWidget(0, 0, moreReplyTable);
			} 	
			
			if ( result.getChildren(0).equals("not null") ) {
				for(int j=0; result.getChildren(j).equals("not null") ; j++){
					/*Initialize Variables*/
					defaultReplyTable.setWidget(j, 0, getReplyRowPanel(result, j, true));	//Update ReplyTable
				}
			}
			
			replyPostPanel.add(addReplyPostPanel(result.getPostId(),defaultReplyTable.getRowCount(), defaultReplyTable));
			
			
			/*Add To Panel*/
			contentPanel.add(img);
			textPanel.add(nameLabel);
			textPanel.add(textLabel);
			rowPanel.add(contentPanel);
			rowPanel.add(textPanel);
			replyPanel.add(new Label("             "));
			replyPanel.add(replyTable);
			
			/*Update Table*/
			table.setWidget(2*i, 0, rowPanel);
			table.setWidget(2*i+1, 0, replyPanel);
			
		}
		
		loginPanel.setVisible(false);
		buttonPanel.setVisible(true);
		textArea.setVisible(false);
		table.setVisible(true);
		expandButton.setVisible(true);
	}
	
	private void addClickEvent(InlineLabel label, Timeline data, final FlexTable table){
		final InlineLabel showAllReply = label;
		final Timeline result = data;
		showAllReply.addClickListener(new ClickListener() {
			
			@Override
			public void onClick(Widget sender) { 
				postId = Integer.toString(result.getPostId());
				// TODO Auto-generated method stub	
				System.out.println(">> " + postId + " <<");
				greetingService.getReplyAll(session, postId, result.getReplyCount() -3, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						showAllReply.setVisible(false);
						
						JsArray<Timeline> reply = jsonTimelineArray(result);
						System.out.println(reply.length());
						for ( int i = 0 ; i < reply.length() ; i++ ){
							final Timeline moreReply = reply.get(i);
							setMoreReplyTable(i+1, 0, getReplyRowPanel(moreReply, 0, false), table);
						}
					}
				});
			}
		});
	}
	
	private void setMoreReplyTable(int row, int column, Widget widget, final FlexTable table){
		table.setWidget(row, column, widget);
	}
	
	private HorizontalPanel getReplyRowPanel(Timeline result, int j, boolean reply){
		HorizontalPanel replyRowPanel = new HorizontalPanel();
		HorizontalPanel replyContentPanel = new HorizontalPanel();
		FlowPanel replyTextPanel = new FlowPanel();
		
		if ( reply ) {
					
			img2 = new Image(checkNoImg(result.getChildrenImgPath(j)));
			img2.setSize("40", "40");
			InlineLabel replyNameLabel = new InlineLabel(result.getChildrenAccount(j) + " ");
			InlineLabel replyTextLabel = new InlineLabel(result.getChildrenPostText(j));
			
			replyContentPanel.add(img2);
			replyTextPanel.add(replyNameLabel);
			replyTextPanel.add(replyTextLabel);
			replyRowPanel.add(replyContentPanel);
			replyRowPanel.add(replyTextPanel);
		} else {
			img2 = new Image(checkNoImg(result.getImgPath()));
			img2.setSize("40", "40");
			InlineLabel replyNameLabel = new InlineLabel(result.getAccount() + " ");
			InlineLabel replyTextLabel = new InlineLabel(result.getPostText());
			
			replyContentPanel.add(img2);
			replyTextPanel.add(replyNameLabel);
			replyTextPanel.add(replyTextLabel);
			replyRowPanel.add(replyContentPanel);
			replyRowPanel.add(replyTextPanel);
		}
			return replyRowPanel;
	}
	
	private FlowPanel addReplyPostPanel(final int postId, final int i, final FlexTable table){
		FlowPanel replyPostPanel = new FlowPanel();
		final TextBox replyTextBox = new TextBox();
		final Button replyButton = new Button("댓글달기");
		replyTextBox.setText("댓글을 기다려요");
		replyTextBox.setSize("500", "20");
		replyTextBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				replyTextBox.setSize("400", "40");
				replyTextBox.setText("");
				replyButton.setVisible(true);
				
			}
		});
		
		replyPostPanel.add(replyTextBox);
		replyPostPanel.add(replyButton);
		replyButton.setVisible(false);
		replyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				try {
					greetingService.post(session, currentGroup, replyTextBox.getValue().toString(), Integer.toString(postId), "WEB", false, new AsyncCallback<String>() {
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
						
							replyTextBox.setText("댓글을 기다려요");
							replyTextBox.setSize("500", "20");
							replyButton.setVisible(false);
							Timeline data = jsonTimelineArray(result).get(0);
							int count = table.getRowCount();
							table.setWidget(count, 0, getReplyRowPanel(data, 0, false));
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		return replyPostPanel;
	}
	
	
	private void setWaitPost(){
		postTextBox.setSize("550", "20");
		postTextBox.setText("나누고 싶은 hantalk?");
		postButton.setVisible(false);
	}
	
	private void setWritePost(){
		postTextBox.setSize("400", "40");
		postTextBox.setText("");
		postButton.setVisible(true);
	
	}
	
	private String checkNoImg(String Url){
		if (Url.equals("http://hantalk.hansol.net/data-imgs/avatar/default/") | Url.equals("http://hantalk.hansol.net/data-imgs/avatar/user/"))
			return "http://hantalk.hansol.net/default-imgs/avatar-default.png";
		else
			return Url;
	}
	
	private final native JsArray<UserInfo> jsonUserInfoArray(String json) /*-{
	  return eval(json);
	}-*/;
	private final native JsArray<Timeline> jsonTimelineArray(String json) /*-{
	  return eval(json);
	}-*/;
}
