package project.mogakco.global.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import project.mogakco.global.application.init.service.InitService;
import project.mogakco.global.dto.init.InitDTO;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/init")
public class InitAPIController {

	private final InitService initService;

	@PostMapping("/basicSet")
	public void initUserInfoBasicSetting(@RequestBody InitDTO.BasicSetting basicSetting) throws IOException {
		initService.basicSetting(basicSetting);
	}
}
