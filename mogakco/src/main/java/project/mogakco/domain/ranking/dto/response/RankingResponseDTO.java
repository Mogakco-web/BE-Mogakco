package project.mogakco.domain.ranking.dto.response;

import lombok.Builder;
import lombok.Getter;
import project.mogakco.domain.member.dto.MemberResponseDTO;

@Getter
@Builder
public class RankingResponseDTO {
	private Long rankingSeq;

	private int rank;

	private long score;

	private MemberResponseDTO memberResponseDTO;
}
