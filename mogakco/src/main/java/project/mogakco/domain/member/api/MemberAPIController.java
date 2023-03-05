package project.mogakco.domain.member.api;

import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.dto.MemberDTO;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.application.jwt.JwtService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Log4j2
public class MemberAPIController {

	private final JwtService jwtService;

	private final MemberServiceImpl memberService;


	@GetMapping("/userInfo/one")
	public ResponseEntity<?> getOneOfUserInfo(@RequestHeader("userInfo")String accessToken){
		System.out.println("userInfoaccess="+accessToken);
		String user_Nickname = jwtService.extractNickname(accessToken).get();
		log.info("user_Nickname+"+user_Nickname);
		MemberSocial selectInfo = memberService.getMemberInfoByNickname(user_Nickname);

		return new ResponseEntity<>(selectInfo, HttpStatus.OK);
	}

	@GetMapping("/userInfo/access")
	public void getOneOfUserInfoAccess(){
	}
}
