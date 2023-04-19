package project.mogakco.global.application.fcm.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import project.mogakco.global.application.fcm.service.FCMService;

public class FCMServiceImpl implements FCMService {
	@Override
	public void sendNotificationReward(String title, String contents) {

	}

	public void send(Message message) {
		FirebaseMessaging.getInstance().sendAsync(message);
	}
}
