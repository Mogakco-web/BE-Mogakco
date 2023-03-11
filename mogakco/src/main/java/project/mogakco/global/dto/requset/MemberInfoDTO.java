package project.mogakco.global.dto.requset;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoDTO {

	@Getter
	public static class selectInfoByoauthIdDTO{
		private String oauthId;
	}
}
