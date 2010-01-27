package com.jeztek.imok;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class IMOk extends UiApplication {

	public static void main(String[] args) {
		IMOk theApp = new IMOk();
		theApp.enterEventDispatcher();
	}
	
	public IMOk() {
		pushScreen(new IMOkScreen());
	}
}

final class IMOkScreen extends MainScreen {
	
	public IMOkScreen() {
		super();
		setTitle(new LabelField("IMOk", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH));
		
		final LabelField labelField = new LabelField();
		ButtonField okButton = new ButtonField("I'm Ok");
		FieldChangeListener okListener = new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				labelField.setText("Reported");
			}
		};
		okButton.setChangeListener(okListener);

		ButtonField cancelButton = new ButtonField("Cancel");
		FieldChangeListener cancelListener = new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				labelField.setText("Cancelled");
			}
		};
		cancelButton.setChangeListener(cancelListener);
		
		VerticalFieldManager fieldManager = new VerticalFieldManager();
		fieldManager.add(new RichTextField("Are you okay?"));
		fieldManager.add(labelField);
		fieldManager.add(okButton);
		fieldManager.add(cancelButton);
		add(fieldManager);
	}
	
	public boolean onClose() {
		Dialog.alert("Goodbye!");
		System.exit(0);
		return true;
	}
}

