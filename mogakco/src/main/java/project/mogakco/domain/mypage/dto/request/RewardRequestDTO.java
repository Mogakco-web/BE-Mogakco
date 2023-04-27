package project.mogakco.domain.mypage.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RewardRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class OnlyUseOauthId{
		private String oauthId;
	}
}
