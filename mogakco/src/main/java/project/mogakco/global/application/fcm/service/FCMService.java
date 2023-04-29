package project.mogakco.global.application.fcm.service;


public interface FCMService{
	void sendNotificationReward(String title, String contents,String fcmToken);
}
