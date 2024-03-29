package project.mogakco.domain.member.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class MemberServiceImpl {

	private final MemberRepository memberRepository;

	public MemberSocial getMemberInfoByNickname(String nickname){
		return memberRepository.findByNickname(nickname).get();
	}

	public MemberSocial getMemberInfoByAuthToken(String authToken){
		return memberRepository.findByAuthToken(authToken).get();
	}

	public MemberSocial getMemberInfoByOAuthId(String oauthId){
		log.info("FindOAUTHID="+oauthId);
		return memberRepository.findByOauthId(oauthId).orElse(null);
	}

	public String getMemberFcmTokenInfoByMemberSocial(MemberSocial memberSocial){
		return memberSocial.getFcmToken();
	}

	public MemberSocial getMemberInfoByMemberSeq(Long memberSeq){
		return memberRepository.findById(memberSeq).get();
	}
}
