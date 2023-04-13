package project.mogakco.domain.timer.application.service;

import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.entity.Timer;

import java.util.List;

public interface TimerCheckService {
	List<Timer> getTimerAllInfo();

	List<Timer> getTimerInfoListByMemberSocial(MemberSocial memberSocial);
}
