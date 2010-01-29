package com.jeztek.imok;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class IMOkSMS {
	
	private String mAddress;
	private String mMessage;
	
	private Thread mSMSThread;
	private Runnable mSMSTask = new Runnable() {
		public void run() {
			MessageConnection smsConnection = null;
			try {
				smsConnection = (MessageConnection)Connector.open(mAddress);
				
				TextMessage textMessage = (TextMessage)smsConnection.newMessage(MessageConnection.TEXT_MESSAGE);
				textMessage.setAddress(mAddress);
				textMessage.setPayloadText(mMessage);

				smsConnection.send(textMessage);
				smsConnection.close();
			}
			catch (IOException e) {
			}
		}
	};
	
	public IMOkSMS(String address, String message) {
		mAddress = address;
		mMessage = message;
	}

	public void send() {
		mSMSThread = new Thread(mSMSTask);
		mSMSThread.start();
	}
}
