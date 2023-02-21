package project.mogakco.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.member.dto.MemberDTO;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberAPIController {

	private final MemberRepository memberRepository;

	@GetMapping("/userInfo/one")
	public ResponseEntity<?> getOneOfUserInfo(@RequestBody MemberDTO.OnlyRefreshTokenDTO onlyRefreshTokenDTO){
		System.out.println("request Refresh="+onlyRefreshTokenDTO.getRefreshToken());
		Optional<MemberSocial> member = memberRepository
				.findByRefreshToken(onlyRefreshTokenDTO.getRefreshToken());
		System.out.println("****="+member.get().getNickname());
		MemberSocial selectInfo
				= memberRepository.findByRefreshToken(onlyRefreshTokenDTO.getRefreshToken()).get();
		return new ResponseEntity<>(selectInfo, HttpStatus.OK);
	}
}
