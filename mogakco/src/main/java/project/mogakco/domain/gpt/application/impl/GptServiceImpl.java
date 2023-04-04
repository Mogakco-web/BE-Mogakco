package project.mogakco.domain.gpt.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.mogakco.domain.gpt.application.config.ChatGptConfig;
import project.mogakco.domain.gpt.application.service.GptService;
import project.mogakco.domain.gpt.dto.request.ChatGptRequestDto;
import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;
import project.mogakco.domain.gpt.dto.response.ChatGptResponseDto;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {
	private static RestTemplate restTemplate = new RestTemplate();

	public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
		headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
		return new HttpEntity<>(requestDto, headers);
	}

	public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
		ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
				ChatGptConfig.URL,
				chatGptRequestDtoHttpEntity,
				ChatGptResponseDto.class);
		ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(
				ChatGptConfig.URL,
				chatGptRequestDtoHttpEntity,
				String.class);
		System.out.println("Resopnse="+stringResponseEntity);
		return responseEntity.getBody();
	}

	public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
		return this.getResponse(
				this.buildHttpEntity(
						new ChatGptRequestDto(
								ChatGptConfig.MODEL,
								requestDto.getQuestion(),
								ChatGptConfig.MAX_TOKEN,
								ChatGptConfig.TEMPERATURE,
								ChatGptConfig.TOP_P
						)
				)
		);
	}
}
