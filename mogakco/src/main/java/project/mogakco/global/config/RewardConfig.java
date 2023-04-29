package project.mogakco.global.config;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class RewardConfig {

	@Getter
	public static class Newbie{
		public static final String title="뉴비";
		public static final String description="모각코에 당도한 것을 환영하오 낯선이여";
	}
	@Getter
	public static class TimerOne{
		public static final String title="부서진 초시계(을)를 얻었다";
		public static final String description="장비를 정지합니다";
	}

	@Getter
	public static class TimerFifty{
		public static final String title="초시계(750G) 획득!";
		public static final String description="룬 : 완벽한 초시계";
	}

	@Getter
	public static class TimerHundred{
		public static final String title="존야의 모래시계";
		public static final String description="강민의 가호가 함께합니다";
	}

	@Getter
	public static class TimerFHundred{
		public static final String title="시간의 지배자";
		public static final String description="따이무 쓰또쁘";
	}

	public static LinkedList<String> getTitles() {
		return Stream.of(Newbie.title, TimerOne.title, TimerFifty.title, TimerHundred.title, TimerFHundred.title)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public static LinkedList<String> getDescriptions() {
		return Stream.of(Newbie.description, TimerOne.description, TimerFifty.description, TimerHundred.description, TimerFHundred.description)
				.collect(Collectors.toCollection(LinkedList::new));
	}
}
