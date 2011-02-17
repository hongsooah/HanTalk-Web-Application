package app.client;

import com.google.gwt.core.client.JavaScriptObject;

public class UserInfo extends JavaScriptObject {
		
	protected UserInfo(){} 
	
	public final native String getDeptName()/*-{return this.deptName}-*/;
	public final native int getDtLastLogin_date()/*-{return this.dtLastLogin.date}-*/;
	public final native int getDtLastLogin_day()/*-{return this.dtLastLogin.day}-*/;
	public final native int getDtLastLogin_hours()/*-{return this.dtLastLogin.hours}-*/;
	public final native int getDtLastLogin_minutes()/*-{return this.dtLastLogin.minutes}-*/;
	public final native int getDtLastLogin_month()/*-{return this.dtLastLogin.month}-*/;
	
	public final native String getEmail()/*-{return this.email}-*/;
	public final native String getMobile()/*-{return this.mobile}-*/;
	public final native String getPhone()/*-{return this.phone}-*/;
	public final native String getProfileVoList_imgPath()/*-{return this.profileVoList[1].imgPath}-*/;
	public final native String getProfileVoList_userId()/*-{return this.profileVoList[0].userId}-*/;
	
	/////////////////
	//public final native String getName()/*-{return this.name}-*/;
	//public final native String getDate_month()/*-{return this.date[0].month}-*/;

}
