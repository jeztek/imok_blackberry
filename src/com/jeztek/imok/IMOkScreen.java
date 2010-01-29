package com.jeztek.imok;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.AutoTextEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class IMOkScreen extends MainScreen {

	private static final int MAX_SMS_LEN = 140;
	private static final String SMS_ADDR = "sms://6507931323";
	
	private static final String IMOK_MSG = "I'm okay.";
	private static final String HELP_MSG = "I need help!";
	
	private AutoTextEditField mMessageEdit;
	private LabelField mCharCountLabel;
	private HorizontalFieldManager mOKButtonsManager;
	
	public IMOkScreen() {
		super();
		setTitle(new LabelField("IMOk", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));
		
		// I'm OK button
		ButtonField okButton = new ButtonField("I'm OK", ButtonField.CONSUME_CLICK) {
			/*
			public int getPreferredHeight() {
				return 30;
			}
			*/
			public int getPreferredWidth() {
				return 120;
			}
		};
		okButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				mMessageEdit.setText(IMOK_MSG);
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
		ButtonField helpButton = new ButtonField("I Need Help", ButtonField.CONSUME_CLICK) {
			/*
			public int getPreferredHeight() {
				return 30;
			}
			*/
			public int getPreferredWidth() {
				return 120;
			}
		};
		helpButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				mMessageEdit.setText(HELP_MSG);	
			}
		});
		//helpButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));
		
		mOKButtonsManager = new HorizontalFieldManager(Field.FIELD_HCENTER);
		mOKButtonsManager.add(okButton);
		mOKButtonsManager.add(btwnButtons);
		mOKButtonsManager.add(helpButton);
		
		
		mCharCountLabel = new LabelField(Integer.toString(MAX_SMS_LEN));
		mMessageEdit = new AutoTextEditField("", "", MAX_SMS_LEN, Field.FIELD_HCENTER) {
			/*
			public int getPreferredWidth() {
				return 200;
			}
			public int getPreferredHeight() {
				return 50;
			}
			*/
            public void paint(Graphics g) {
                super.paint(g);
                g.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }

			protected void layout(int width, int height)
			{
				// adjust width of layout
				super.layout((int)(width * .8), height);
			}

		};
		mMessageEdit.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				int messageLen = MAX_SMS_LEN - mMessageEdit.getTextLength();
				mCharCountLabel.setText(Integer.toString(messageLen));
			}
		});

		// Send button
		ButtonField sendButton = new ButtonField("Send Message", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER) {
			/*
			public int getPreferredHeight() {
				return 30;
			}
			*/
			public int getPreferredWidth() {
				return 200;
			}
		};
		sendButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				IMOkSMS sms = new IMOkSMS(SMS_ADDR, mMessageEdit.getText());
				sms.send();
			}
		});
		sendButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));
		
		// MainScreen vertical manager		
		add(new LabelField());
		add(mOKButtonsManager);
		add(new LabelField());

		add(mMessageEdit);
		add(mCharCountLabel);
		add(sendButton);
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
