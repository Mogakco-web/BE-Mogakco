package project.mogakco.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubResponseDTO {
	private String login;
	private String node_id;
	private String avatar_url;
	private String type;
	private String repos_url;
	private String name;
	private String created_at;
	private String updated_at;
}
