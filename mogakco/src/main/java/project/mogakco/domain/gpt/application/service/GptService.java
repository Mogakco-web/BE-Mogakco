package project.mogakco.domain.gpt.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.gpt.application.config.response.GptChatResponseConfig;
import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;
import project.mogakco.domain.gpt.dto.response.ChatGptResponseDto;

public interface GptService {
//	ChatGptResponseDto askQuestion(QuestionRequestDto questionRequestDto);
	GptChatResponseConfig chatContent(QuestionRequestDto questionRequestDto);
}
