package project.mogakco.global.application.fcm.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;
import project.mogakco.global.application.fcm.service.FCMService;

@Service
public class FCMServiceImpl implements FCMService {

	@Override
	public void sendNotificationReward(String title, String contents,String fcmToken) {
		Message message = Message.builder()
				.putData("title", title)
				.putData("content", contents)
				.setToken(fcmToken)
				.build();

		send(message);
	}

	public void send(Message message) {
		FirebaseMessaging.getInstance().sendAsync(message);
	}
}
