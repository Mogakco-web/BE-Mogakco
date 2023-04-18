package project.mogakco.domain.gpt.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.gpt.application.service.GptService;
import project.mogakco.domain.gpt.dto.request.QuestionRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
public class GptAPIController {

	private final GptService gptService;

	/*@PostMapping
	public ResponseEntity<?> gptInit(@RequestBody QuestionRequestDto questionRequestDto){
		return new ResponseEntity<>(gptService.askQuestion(questionRequestDto), HttpStatus.OK);
	}*/

	@PostMapping
	public ResponseEntity<?> gptChat(@RequestBody QuestionRequestDto questionRequestDto){
		return new ResponseEntity<>(gptService.chatContent(questionRequestDto),HttpStatus.OK);
	}
}
