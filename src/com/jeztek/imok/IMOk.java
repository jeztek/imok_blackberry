package com.jeztek.imok;

import net.rim.device.api.ui.UiApplication;

public class IMOk extends UiApplication {

	public static final String APP_NAME = "IMOk";
	public static final String ABOUT_TEXT = "IMOk for Blackberry";

	public static void main(String[] args) {
		IMOk theApp = new IMOk();
		theApp.enterEventDispatcher();
	}
	
	public IMOk() {
		pushScreen(new IMOkScreen());
	}
}
