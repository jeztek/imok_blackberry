package com.jeztek.imok;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class IMOkSMS {
	
	public boolean sendSMS(String address, String message) {
		
		return true;
		/*
		MessageConnection smsConnection = null;
		try {
			smsConnection = (MessageConnection)Connector.open(address);
			
			TextMessage textMessage = (TextMessage)smsConnection.newMessage(MessageConnection.TEXT_MESSAGE);
			textMessage.setAddress(address);
			textMessage.setPayloadText(message);

			smsConnection.send(textMessage);
			smsConnection.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}	
		*/	
	}
}
