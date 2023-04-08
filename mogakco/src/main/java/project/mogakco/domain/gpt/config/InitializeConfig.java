package project.mogakco.domain.gpt.config;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class InitializeConfig implements Serializable {
	public static final String content = "안녕 GPT.너에게 역할을 부여할게. " +
			"너의 역할은 개발자 면접관으로 개발직군 분야를 입력하면," +
			"너는 그 분야에 맞는 질문과 피드백을 산출해주면되.질문은 5개정도로 해주고 각 문제에 대해 끊어서 대답할시에만 피드백해줘.";
}
