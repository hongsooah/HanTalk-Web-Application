package app.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Time extends JavaScriptObject {
		
	protected Time(){}
	
	//public final native String getResult(int i ) /*-{ return this.result[i] == null ? "null" : "not null" }-*/;
	public final native String getDeptName(int i)/*-{return this.result[i].account.deptName}-*/;
	public final native String getEmail(int i)/*-{return this.result[i].account.email}-*/;
	public final native String getProfileVoList_imgPath(int i)/*-{
														if(this.result[i].account.profileVoList[0]==null){
														return "";
														}
														return this.result[i].account.profileVoList[0].imgPath;
														}-*/;
	public final native String getPostVo_postText(int i)/*-{return this.result[i].postVo.postText}-*/;
	public final native String getId(int i)/*-{return this.result[i].account.profileVoList[0].userId}-*/;
	public final native String getReplyImage(int i, int child)/*-{
																var reply=null;
																if(this.result[i].children[child]==null){
																	return "x";
																}
																else{
																	return this.result[i].children[child].account.profileVoList[0].imgPath;
																}	
																}-*/;
	public final native String getReply(int i, int child)/*-{
												var reply=null;
												//for(var j=0; j<5; j++){
													if(this.result[i].children[child]==null){
														return reply;
													}
													else{
														return child+1+"> "+this.result[i].children[child].postVo.userId+"\n"+this.result[i].children[child].postVo.postText+"\n";
													}
												//}//for
												
												}-*/;
	

}