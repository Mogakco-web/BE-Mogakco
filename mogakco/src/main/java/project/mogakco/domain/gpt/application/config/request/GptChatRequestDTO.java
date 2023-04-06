package project.mogakco.domain.gpt.application.config.request;

import lombok.Getter;

@Getter
public class GptChatRequestDTO {
	private String model;

	private Messages[] messages;

	public GptChatRequestDTO(String model,Messages[] messages) {
		this.model=model;
		this.messages = messages;
	}
}