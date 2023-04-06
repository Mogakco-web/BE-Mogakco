package project.mogakco.domain.gpt.application.config.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Messages implements Serializable {
	public static final String role = "user";

	public String content;

	public Messages(String content) {
		this.content=content;
	}
}
