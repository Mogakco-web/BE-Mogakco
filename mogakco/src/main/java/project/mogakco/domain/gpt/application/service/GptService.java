package project.mogakco.domain.gpt.application.service;

import project.mogakco.domain.gpt.config.response.GptChatResponseConfig;
import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;

public interface GptService {
//	ChatGptResponseDto askQuestion(QuestionRequestDto questionRequestDto);
	GptChatResponseConfig chatContent(QuestionRequestDto questionRequestDto);

	void initializeRoleGpt();
}
