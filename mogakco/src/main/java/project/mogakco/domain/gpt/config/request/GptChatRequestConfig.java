package project.mogakco.domain.gpt.config.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class GptChatRequestConfig implements Serializable {

	public static final String URL="https://api.openai.com/v1/chat/completions";

	public static final String AUTHORIZATION = "Authorization";

	public static final String BEARER = "Bearer ";

	public static final String MEDIA_TYPE = "application/json; charset=UTF-8";

	public static final String API_KEY = "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";

}
