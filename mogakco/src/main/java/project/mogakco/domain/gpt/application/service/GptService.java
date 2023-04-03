package project.mogakco.domain.gpt.application.service;

import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;
import project.mogakco.domain.gpt.dto.response.ChatGptResponseDto;

public interface GptService {
	ChatGptResponseDto askQuestion(QuestionRequestDto questionRequestDto);
}
