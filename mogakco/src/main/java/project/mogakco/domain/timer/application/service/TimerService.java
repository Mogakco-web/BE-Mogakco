package project.mogakco.domain.timer.application.service;

import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

public interface TimerService {
	void recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);
}
