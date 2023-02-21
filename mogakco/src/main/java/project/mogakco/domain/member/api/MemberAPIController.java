package project.mogakco.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.member.dto.MemberDTO;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberAPIController {

	private final MemberRepository memberRepository;

	@GetMapping("/userInfo/one")
	public ResponseEntity<?> getOneOfUserInfo(@RequestHeader String refreshToken){
		System.out.println("HeaderRefresh="+refreshToken);
		Optional<MemberSocial> member = memberRepository
				.findByRefreshToken(refreshToken);
		System.out.println("****="+member.get().getNickname());
		MemberSocial selectInfo
				= memberRepository.findByRefreshToken(refreshToken).get();
		return new ResponseEntity<>(selectInfo, HttpStatus.OK);
	}
}
