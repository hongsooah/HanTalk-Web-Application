package app.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Timeline extends JavaScriptObject {
		
	protected Timeline(){}
	
	public final native String getAccount() /*-{ 
	return this.account.profileVoList[1] == null ?this.account.profileVoList[0].userName:this.account.profileVoList[1].userName; }-*/;
	public final native String getImgPath() /*-{ return this.account.profileVoList[1] == null ?
		"http://hantalk.hansol.net/data-imgs/avatar/default/" + this.account.profileVoList[0].imgPath:
		"http://hantalk.hansol.net/data-imgs/avatar/user/" + this.account.profileVoList[1].imgPath; }-*/;
	public final native String getPostText() /*-{ return this.postVo.postText ; }-*/;
	public final native int getPostId() /*-{ return this.postVo.postId ; }-*/;
	public final native int getReplyCount() /*-{ return this.replyCount ; }-*/;
	
	public final native String getfavProfile() /*-{ return this.postVo.favProfile ; }-*/;
	// 선호 프로필 바꾸기 미구현
	
	public final native String getChildren(int i) /*-{ return this.children[i] == null ?
		 "null" : "not null" }-*/;
	
	public final native String getChildrenAccount(int i) /*-{ return this.children[i].account.profileVoList[1] == null ?
	this.children[i].account.profileVoList[0].userName:this.children[i].account.profileVoList[1].userName; }-*/;
	public final native String getChildrenImgPath(int i) /*-{ return this.children[i].account.profileVoList[1] == null ?
	"http://hantalk.hansol.net/data-imgs/avatar/default/" + this.children[i].account.profileVoList[0].imgPath:
	"http://hantalk.hansol.net/data-imgs/avatar/user/" + this.children[i].account.profileVoList[1].imgPath; }-*/;
	public final native String getChildrenPostText(int i) /*-{ return this.children[i].postVo.postText; }-*/;
	
//	//public final native String getResult(int i ) /*-{ return this.result[i] == null ? "null" : "not null" }-*/;
//	public final native String getDeptName(int i)/*-{return this.account.deptName}-*/;
//	public final native String getEmail(int i)/*-{return this.account.email}-*/;
//	public final native String getProfileVoList_imgPath(int i)/*-{
//														if(this.account.profileVoList[0]==null){
//														return "";
//														}
//														return this.account.profileVoList[0].imgPath;
//														}-*/;
//	public final native String getPostVo_postText(int i)/*-{return this.postVo.postText}-*/;
//	public final native String getId(int i)/*-{return this.account.profileVoList[0].userId}-*/;
//	public final native String getReplyImage(int i, int child)/*-{
//																var reply=null;
//																if(this.children[child]==null){
//																	return "x";
//																}
//																else{
//																	return this.children[child].account.profileVoList[0].imgPath;
//																}	
//																}-*/;
//	public final native String getReply(int i, int child)/*-{
//												var reply=null;
//												//for(var j=0; j<5; j++){
//													if(this.children[child]==null){
//														return reply;
//													}
//													else{
//														return child+1+"> "+this.children[child].postVo.userId+"\n"+this.children[child].postVo.postText+"\n";
//													}
//												//}//for
//												
//												}-*/;
	

}