package project.mogakco.domain.gpt.config.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ChatChoice implements Serializable {
	private Long index;

	public static class message{
		private String role;
		private String content;
	}

	private String finish_reason;
}
