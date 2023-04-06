package project.mogakco.domain.gpt.application.config.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class GptChatResponseConfig implements Serializable {
	public static final String id="";

	public static final String object = "";

	public static final Long created=0L;

	public ChatChoice chatChoice;
}
