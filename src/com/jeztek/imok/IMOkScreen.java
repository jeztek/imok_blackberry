package com.jeztek.imok;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.AutoTextEditField;
import net.rim.device.api.ui.component.BasicEditField;
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
	
	private static boolean mIsOkay;
	private static boolean mIsMessageEdited = false;
	
	private BasicEditField mMessageEdit;
	private LabelField mCharCountLabel;
	private HorizontalFieldManager mOKButtonsManager;
	private LabelField mOKStatusField;
	
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
				mOKStatusField.setText("I'm OK!");

				if (!mIsMessageEdited || mMessageEdit.getText() == IMOK_MSG || mMessageEdit.getText() == HELP_MSG) {
					mMessageEdit.setText(IMOK_MSG);
				}
				mIsOkay = true;
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
				mOKStatusField.setText("I Need Help!");
				if (!mIsMessageEdited || mMessageEdit.getText() == IMOK_MSG || mMessageEdit.getText() == HELP_MSG) {
					mMessageEdit.setText(HELP_MSG);
				}
				mIsOkay = false;
			}
		});
		//helpButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));
		
		mOKButtonsManager = new HorizontalFieldManager(Field.FIELD_HCENTER);
		mOKButtonsManager.add(okButton);
		mOKButtonsManager.add(btwnButtons);
		mOKButtonsManager.add(helpButton);
		
		mOKStatusField = new LabelField("", Field.FIELD_HCENTER);

		HorizontalFieldManager charCountManager = new HorizontalFieldManager(Field.FIELD_HCENTER) {
			protected void sublayout(int width, int height) {  
				if (getFieldCount() > 0) {  
					Field centeredField = getField(0);  // get the first (and only) field  
					layoutChild(centeredField, width, height); // set the field's width and height  
					setPositionChild(centeredField, (int)(width * .82), 0);  // center the field horizontally  
					setExtent(width, centeredField.getHeight());  // set the size of this manager to use the entire screen width  
				}  
			} 
		};
		mCharCountLabel = new LabelField(Integer.toString(MAX_SMS_LEN), Field.FIELD_RIGHT);
		charCountManager.add(mCharCountLabel);
		
		
		mMessageEdit = new BasicEditField("", "", MAX_SMS_LEN, Field.FIELD_HCENTER) {
			/*
			public int getPreferredWidth() {
				return 200;
			}
			public int getPreferredHeight() {
				return 30;
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
				mIsMessageEdited = true;
				int messageLen = MAX_SMS_LEN - mMessageEdit.getTextLength();
				if (mIsOkay) {
					messageLen = messageLen - 6;
				}
				else {
					messageLen = messageLen - 10;
				}
				mCharCountLabel.setText(Integer.toString(messageLen));
				mMessageEdit.setMaxSize(messageLen);
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
				String messageText = mMessageEdit.getText();
				if (mIsOkay) {
					messageText.concat(" #imok");
				}
				else {
					messageText.concat(" #needhelp");
				}
				IMOkSMS sms = new IMOkSMS(SMS_ADDR, messageText);
				sms.send();
			}
		});
		sendButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));
		
		// MainScreen vertical manager		
		add(new LabelField());
		add(mOKButtonsManager);
		add(new LabelField());
		add(mOKStatusField);

		add(mMessageEdit);
		add(charCountManager);
		//add(mCharCountLabel);
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
