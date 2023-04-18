package project.mogakco.domain.timer.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.application.service.TimerCheckService;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

import java.util.List;


@RequiredArgsConstructor
@Log4j2
@Service
@Transactional(readOnly = true)
public class TimerCheckServiceImpl implements TimerCheckService {

	private final TimerRepository timerRepository;

	@Override
	public List<Timer> getTimerInfoListByMemberSocial(MemberSocial memberSocial) {
		return timerRepository.findByMemberSocial(memberSocial).get();
	}

	@Override
	public List<Timer> getTimerAllInfo() {
		return timerRepository.findAll();
	}

}
