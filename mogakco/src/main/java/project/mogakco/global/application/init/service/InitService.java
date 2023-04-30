package project.mogakco.global.application.init.service;

import project.mogakco.global.dto.init.InitDTO;

import java.io.IOException;

public interface InitService {

	void basicSetting(InitDTO.BasicSetting basicSetting) throws IOException;
}
