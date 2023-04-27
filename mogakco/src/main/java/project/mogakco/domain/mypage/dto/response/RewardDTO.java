package project.mogakco.domain.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RewardDTO {

	@Getter
	@Builder
	public static class listReward{
		private Long rewardSeq;
		private String name;
		private String description;
	}

	@Getter
	@Builder
	public static class checkReward{
		private Long rewardSeq;
		private String name;
		private String description;
	}
}
