package project.mogakco.domain.member.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
	USER("ROLE_USER");

	private final String key;
}
