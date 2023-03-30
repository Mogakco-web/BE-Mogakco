package project.mogakco.domain.ranking.entity.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import project.mogakco.domain.member.dto.MemberResponseDTO;

import java.time.LocalDate;


@Getter
@RedisHash(value = "rankingRedis",timeToLive = 86400)
@Builder
public class RankingRedis {

	@Id
	private String userNickname;

	private MemberResponseDTO.RankingDTO rankingMember;

	private Long score;

	private int rank;

	private LocalDate createDate;

	public void changeRankInfo(int changeInfoRank){
		this.rank=changeInfoRank;
	}
}
