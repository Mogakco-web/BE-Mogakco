package project.mogakco.domain.member.entity.member;

import lombok.*;
import project.mogakco.domain.member.dto.MemberDTO;
import project.mogakco.domain.member.dto.MemberResponseDTO;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.domain.todo.entity.ToDo;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class MemberSocial extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_seq;

	private String email;

	private String nickname;

	private String member_imgUrl;

	private String oauthId;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	private String authToken;

	private String refreshToken;

	private String password;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "memberSocial")
	private List<ToDo> toDoList;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "memberSocial")
	private List<Timer> timer;
	public void updateRefreshToken(String updateRefreshToken){
		this.refreshToken=updateRefreshToken;
	}

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "memberSocial")
	public List<Category> categories;

	public MemberSocial updateOAuthInfo(MemberDTO.UpdateOAuthUser updateOAuthUser){
		this.authToken=updateOAuthUser.getAuthToken();
		this.member_imgUrl=updateOAuthUser.getImgUrl();
		this.nickname=updateOAuthUser.getNickname();
		return this;
	}

	public void updateAuthToken(String authToken){
		this.authToken=authToken;
	}

	public void updateInfoByLogout(String refreshToken,String authToken){
		updateRefreshToken(refreshToken);
		updateAuthToken(authToken);
	}

	public MemberResponseDTO toDTO(){
		return MemberResponseDTO
				.builder()
				.member_seq(member_seq)
				.authToken(authToken)
				.member_imgUrl(member_imgUrl)
				.email(email)
				.nickname(nickname)
				.oauthId(oauthId)
				.socialType(socialType)
				.refreshToken(refreshToken)
				.role(role)
				.build();
	}
}
