package com.jeztek.imok;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class IMOkScreen extends MainScreen {

	public static final String OKAY_TEXT = "I'm okay";
	public static final String HELP_TEXT = "I need help";
	public static final String INFO_TEXT = "\nIMOk notifies friends and family in\nthe event of an emergency." 
		+ "\n\nhttp://imokapp.appspot.com";
		
	public IMOkScreen() {
		super();
		setTitle(new LabelField(IMOk.APP_NAME, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));

		BitmapField logoBitmap = new BitmapField(Bitmap.getBitmapResource("logo.png"), BitmapField.FIELD_HCENTER);

		LabelField infoLabel = new LabelField(INFO_TEXT, LabelField.FIELD_HCENTER) {
			protected void layout(int width, int height)
			{
				// adjust width of layout
				super.layout((int)(width * .8), height);
			}
		};
		
		LabelField spacerLabel = new LabelField();
		
		// I'm OK button
		ButtonField okButton = new ButtonField(OKAY_TEXT, ButtonField.CONSUME_CLICK | ButtonField.FIELD_HCENTER) {
			/*
			public int getPreferredHeight() {
				return 30;
			}
			*/
			public int getPreferredWidth() {
				return 140;
			}
		};
		okButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				nextScreen(true);
			}
		});
		//okButton.setFont(okButton.getFont().derive(Font.PLAIN, 20));

		// Spacer
		LabelField btwnButtons = new LabelField() {
			public int getPreferredWidth() {
				return 10;
			}
		};
		
		// I Need Help button
		ButtonField helpButton = new ButtonField(HELP_TEXT, ButtonField.CONSUME_CLICK | ButtonField.FIELD_HCENTER) {
			/*
			public int getPreferredHeight() {
				return 30;
			}
			*/
			public int getPreferredWidth() {
				return 140;
			}
		};
		helpButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				nextScreen(false);
			}
		});
		//helpButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));
		
		HorizontalFieldManager buttonsManager = new HorizontalFieldManager(Field.FIELD_HCENTER);
		buttonsManager.add(okButton);
		buttonsManager.add(btwnButtons);
		buttonsManager.add(helpButton);

		add(logoBitmap);
		add(new SeparatorField());
		add(infoLabel);
		add(spacerLabel);
		add(buttonsManager);
	}
	
	protected void nextScreen(boolean isOkay) {
		MessageScreen messageScreen = new MessageScreen(isOkay);
		UiApplication.getUiApplication().pushScreen(messageScreen);
	}
	
	protected void makeMenu(Menu menu, int instance) {
		MenuItem aboutMenuItem = new MenuItem("About", 10, 20) {
			public void run() {
				Dialog.alert(IMOk.ABOUT_TEXT);
			}
		};

		MenuItem closeMenuItem = new MenuItem("Close", 20, 10) {
			public void run() {
				onClose();
			}
		};
		
		menu.add(closeMenuItem);
		menu.add(aboutMenuItem);
	}
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
}
