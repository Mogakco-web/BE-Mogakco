package project.mogakco.domain.gpt.application.config.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Messages implements Serializable {
	public String role;

	public String content;

	public Messages(String role,String content) {
		this.role=role;
		this.content=content;
	}
}
