package app.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Data extends JavaScriptObject {
		
	protected Data(){}
	
	public final native String getDeptName()/*-{return this.result[0].deptName}-*/;
	public final native int getDtLastLogin_date()/*-{return this.result[0].dtLastLogin.date}-*/;
	public final native int getDtLastLogin_day()/*-{return this.result[0].dtLastLogin.day}-*/;
	public final native int getDtLastLogin_hours()/*-{return this.result[0].dtLastLogin.hours}-*/;
	public final native int getDtLastLogin_minutes()/*-{return this.result[0].dtLastLogin.minutes}-*/;
	public final native int getDtLastLogin_month()/*-{return this.result[0].dtLastLogin.month}-*/;
	public final native String getEmail()/*-{return this.result[0].email}-*/;
	public final native String getMobile()/*-{return this.result[0].mobile}-*/;
	public final native String getPhone()/*-{return this.result[0].phone}-*/;
	public final native String getProfileVoList_imgPath()/*-{return this.result[0].profileVoList[1].imgPath}-*/;
	public final native String getProfileVoList_userId()/*-{return this.result[0].profileVoList[0].userId}-*/;
	
	/////////////////
	//public final native String getName()/*-{return this.result[0].name}-*/;
	//public final native String getDate_month()/*-{return this.result[0].date[0].month}-*/;

}
