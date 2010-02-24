package com.jeztek.imok;

import net.rim.device.api.ui.UiApplication;

public class IMOk extends UiApplication {

	public static final String APP_NAME = "IMOk";
	
	// NOTE: Update version string before release
	public static final String ABOUT_TEXT = "IMOk for Blackberry\nVersion 1.0.4";

	public static void main(String[] args) {
		IMOk theApp = new IMOk();
		theApp.enterEventDispatcher();
	}
	
	public IMOk() {
		pushScreen(new IMOkScreen());
	}
}
