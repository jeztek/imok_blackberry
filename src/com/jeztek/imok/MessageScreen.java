package com.jeztek.imok;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class MessageScreen extends MainScreen {

	private static final int MAX_SMS_LEN = 140;
	private static final String SMS_ADDR = "sms://6504171034";

	public static final String OKAY_HASH = " #imok";
	public static final String HELP_HASH = " #needhelp";
	
	public static final String OKAY_TEXT = "I'm okay";
	public static final String HELP_TEXT = "I need help";
	public static final String SENT_TEXT = "Message sent!";

	public static final String OKAY_PROMPT = "Glad you're okay!  Add a message and tell us where you are by adding \"#loc <location>\".";
	public static final String HELP_PROMPT = "Add a message describing your needs and tell us where you are by adding \"#loc <location>\".";
	
	private boolean mIsOkay;
	
	private BasicEditField mMessageEdit;
	private LabelField mCharCountLabel;
	
	private LabelField mStatusLabel = new LabelField();

	public MessageScreen(boolean isOkay) {
		super();
		mIsOkay = isOkay;

		String title = mIsOkay ? OKAY_TEXT : HELP_TEXT;
		setTitle(new LabelField(IMOk.APP_NAME + " - " + title, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));
		
		LabelField promptSpacerLabel = new LabelField() {
			public int getPreferredHeight() {
				return 50;
			}
		};
				
		String prompt = mIsOkay ? OKAY_PROMPT : HELP_PROMPT;
		LabelField promptLabel = new LabelField(prompt, LabelField.FIELD_HCENTER) {
			protected void layout(int width, int height)
			{
				// adjust width of layout
				super.layout((int)(width * .8), height);
			}
		};
		
		LabelField messageSpacerLabel = new LabelField() {
			public int getPreferredHeight() {
				return 50;
			}
		};

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
		mCharCountLabel = new LabelField(Integer.toString(getMessageLen()), Field.FIELD_RIGHT);
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
				int messageLen = getMessageLen();
				mCharCountLabel.setText(Integer.toString(messageLen));
			}
		});
		if (mIsOkay) 
			mMessageEdit.setMaxSize(MAX_SMS_LEN - OKAY_HASH.length());
		else
			mMessageEdit.setMaxSize(MAX_SMS_LEN - HELP_HASH.length());

		
		// Send button
		ButtonField sendButton = new ButtonField("Send message", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER) {
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
					messageText += OKAY_HASH;
				}
				else {
					messageText += HELP_HASH;
				}
				IMOkSMS sms = new IMOkSMS(SMS_ADDR, messageText);
				sms.setCompleteRunnable(new Runnable() {
					public void run() {
						Dialog.alert(SENT_TEXT);
						System.exit(0);
					}
				});
				sms.send();
				mStatusLabel.setText(messageText);
			}
		});
		//sendButton.setFont(helpButton.getFont().derive(Font.PLAIN, 20));

		add(promptSpacerLabel);
		add(promptLabel);
		add(messageSpacerLabel);
		add(mMessageEdit);
		add(charCountManager);
		add(sendButton);
		//add(mStatusLabel);
	}
	
	private int getMessageLen() {
		int textLen;
		if (mMessageEdit == null)
			textLen = 0;
		else
			textLen = mMessageEdit.getTextLength();
		
		if (mIsOkay) {
			return MAX_SMS_LEN - OKAY_HASH.length() - textLen;
		}
		return MAX_SMS_LEN - HELP_HASH.length() - textLen;
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
