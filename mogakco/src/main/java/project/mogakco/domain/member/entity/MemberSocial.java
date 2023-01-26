package project.mogakco.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MemberSocial {

	@Id
	@GeneratedValue
	private Long member_seq;

	private String member_social_email;

	private String member_imgUrl;

	private String member_social_id;

	@Enumerated
	private MemberRole role;

	@Enumerated
	private SocialType socialType;
}
