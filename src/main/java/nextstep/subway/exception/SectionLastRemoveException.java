package nextstep.subway.exception;

import org.springframework.http.HttpStatus;

public class SectionLastRemoveException extends ApiException {

	public static final String MESSAGE = "지하철 노선의 구간이 1개인 경우 삭제할 수 없습니다.";

	public SectionLastRemoveException() {
		super(HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
