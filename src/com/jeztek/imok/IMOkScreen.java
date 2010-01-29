package com.jeztek.imok;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class IMOkScreen extends MainScreen {

	public static final int MAX_SMS_LEN = 140;
	
	private LabelField mStatusLabel;
	private BasicEditField mMessageEdit;
	private LabelField mCharCountLabel;
	
	public IMOkScreen() {
		super();
		setTitle(new LabelField("IMOk", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));
		
		mStatusLabel = new LabelField();
		ButtonField okButton = new ButtonField("I'm Ok");
		okButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				IMOkSMS sms = new IMOkSMS("sms://6507931323", "Test!");
				sms.send();
				mStatusLabel.setText("Reported");
			}
		});

		ButtonField cancelButton = new ButtonField("Cancel");
		cancelButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				mStatusLabel.setText("Cancelled");
			}
		});
		
		mCharCountLabel = new LabelField(Integer.toString(MAX_SMS_LEN));
		mMessageEdit = new BasicEditField();
		mMessageEdit.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				int messageLen = MAX_SMS_LEN - mMessageEdit.getTextLength();
				mCharCountLabel.setText(Integer.toString(messageLen));
			}
		});
		
		add(new RichTextField("Are you okay?"));
		add(mStatusLabel);
		add(new SeparatorField());
		add(okButton);
		add(cancelButton);
		add(new SeparatorField());
		add(mMessageEdit);
		add(mCharCountLabel);
	}
	
	protected void makeMenu(Menu menu, int instance) {
		MenuItem aboutMenuItem = new MenuItem("About", 10, 20) {
			public void run() {
				Dialog.alert("IMOk for Blackberry");
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

