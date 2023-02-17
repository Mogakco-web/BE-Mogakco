package project.mogakco.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.member.dto.MemberDTO;
import project.mogakco.domain.member.entity.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberAPIController {

	private final MemberRepository memberRepository;

	@GetMapping("/userInfo/one")
	public ResponseEntity<?> getOneOfUserInfo(@RequestBody MemberDTO.OnlyRefreshTokenDTO onlyRefreshTokenDTO){
		MemberSocial selectInfo
				= memberRepository.findByRefreshToken(onlyRefreshTokenDTO.getRefreshToken()).get();
		return new ResponseEntity<>(selectInfo, HttpStatus.OK);
	}
}
