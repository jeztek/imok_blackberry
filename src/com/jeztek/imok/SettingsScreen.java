package com.jeztek.imok;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

public class SettingsScreen extends MainScreen {

	public static final String SMS_ADDR = "6509241975";
	public static final long PERSIST_KEY = 0xBEEFDEAD;

	public static final String TITLE_TEXT = "Settings";
	public static PersistentObject persist;

	private BasicEditField mMessageEdit;
	
	public SettingsScreen() {
		super();
		setTitle(new LabelField(IMOk.APP_NAME + " - " + TITLE_TEXT, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));

		persist = PersistentStore.getPersistentObject(PERSIST_KEY);
		String smsAddr = (String)persist.getContents();
		if (smsAddr == null) {
			smsAddr = SMS_ADDR;
			persist.setContents(smsAddr);
			persist.commit();
		}
				
		mMessageEdit = new BasicEditField("SMS gateway: ", smsAddr, 20, Field.EDITABLE);
		add(mMessageEdit);
	}
	
	protected void makeMenu(Menu menu, int instance) {
		MenuItem saveMenuItem = new MenuItem("Save", 10, 20) {
			public void run() {
				persist.setContents(mMessageEdit.getText());
				persist.commit();
				exitScreen();
			}
		};
		menu.add(saveMenuItem);
		super.makeMenu(menu, instance);
	}
	
	public void exitScreen() {
		UiApplication.getUiApplication().popScreen(this);
	}
}
