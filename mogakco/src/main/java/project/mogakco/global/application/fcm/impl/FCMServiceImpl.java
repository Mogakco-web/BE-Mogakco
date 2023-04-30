package project.mogakco.global.application.fcm.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import project.mogakco.global.application.fcm.service.FCMService;

@Service
public class FCMServiceImpl implements FCMService {

	@Override
	public void sendNotificationReward(String title, String contents,String fcmToken) {
		System.out.println("FCM="+fcmToken);
		Notification notification = Notification.builder()
				.setTitle(title)
				.setBody(contents)
				.build();


		Message message = Message.builder()
				.setNotification(notification)
				.setToken(fcmToken)
				.build();

		send(message);
	}

	@SneakyThrows
	public void send(Message message){
		try{
			FirebaseMessaging.getInstance().send(message);
		}catch (Exception e){
			System.out.println("FCM error="+e.getMessage());
			System.out.println("FCM error typex="+e.getClass());

		}
	}
}
