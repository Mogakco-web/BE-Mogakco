package project.mogakco.domain.gpt.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDto implements Serializable{
	private String content;
}