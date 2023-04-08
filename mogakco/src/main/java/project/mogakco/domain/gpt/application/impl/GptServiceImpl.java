package project.mogakco.domain.gpt.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.mogakco.domain.gpt.config.InitializeConfig;
import project.mogakco.domain.gpt.config.request.GptChatRequestConfig;
import project.mogakco.domain.gpt.config.request.GptChatRequestDTO;
import project.mogakco.domain.gpt.config.request.Messages;
import project.mogakco.domain.gpt.config.response.GptChatResponseConfig;
import project.mogakco.domain.gpt.application.service.GptService;
import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {
	private static RestTemplate restTemplate = new RestTemplate();


	@Override
	public GptChatResponseConfig chatContent(QuestionRequestDto questionRequestDto) {
		Messages messages = new Messages("user",questionRequestDto.getContent());
		Messages[] messagesArray= new Messages[]{messages};

		return this.getResponse(
				this.buildHttpEntity(
						new GptChatRequestDTO("gpt-3.5-turbo", messagesArray)
				)
		);
	}

	@Override
	public void initializeRoleGpt(){
		Messages messages = new Messages("user", InitializeConfig.content);
		Messages[] messagesArray= new Messages[]{messages};
		this.getResponse(
				this.buildHttpEntity(
						new GptChatRequestDTO("gpt-3.5-turbo", messagesArray)
				)
		);
	}

	public HttpEntity<GptChatRequestDTO> buildHttpEntity(GptChatRequestDTO requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(GptChatRequestConfig.MEDIA_TYPE));
		headers.add(GptChatRequestConfig.AUTHORIZATION, GptChatRequestConfig.BEARER + GptChatRequestConfig.API_KEY);
		return new HttpEntity<>(requestDto, headers);
	}

	public GptChatResponseConfig getResponse(HttpEntity<GptChatRequestDTO> gptChatRequestConfigHttpEntity) {
		String responseObject = restTemplate.postForObject(
				GptChatRequestConfig.URL,
				gptChatRequestConfigHttpEntity,
				String.class);
		System.out.println("response="+responseObject);
//		return responseEntity.getBody();
		return null;
	}

}
