package nextstep.subway.utils;

import static nextstep.subway.utils.StationFixture.논현역_이름;
import static nextstep.subway.utils.StationFixture.신사역_이름;
import static nextstep.subway.utils.StationFixture.이매역_이름;
import static nextstep.subway.utils.StationFixture.지하철역_리스폰_변환;
import static nextstep.subway.utils.StationFixture.지하철역_생성_요청;
import static nextstep.subway.utils.StationFixture.판교역_이름;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.dto.LineCreateRequest;
import nextstep.subway.dto.LineResponse;
import nextstep.subway.dto.LineUpdateRequest;
import nextstep.subway.dto.SectionAddRequest;
import nextstep.subway.dto.StationResponse;

public class LineFixture {

	public static final String 신분당선_이름 = "신분당선";
	public static final String 신분당선_색상 = "red";
	public static final String 경강선_이름 = "경강선";
	public static final String 경강선_색상 = "blue";
	public static final String 수인분당선_이름 = "수인분당선";
	public static final String 수인분당선_색상 = "yellow";

	public static ExtractableResponse<Response> 지하철_노선_생성_요청(String name, String color,
		Long upStationId, Long downStationId, Integer distance) {
		return RestAssured.given().log().all()
			.body(new LineCreateRequest(name, color, upStationId, downStationId, distance))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/lines")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/lines")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선_조회_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/lines/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선_수정_요청(Long id, String name, String color) {
		return RestAssured.given().log().all()
			.body(new LineUpdateRequest(name, color))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().put("/lines/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선_삭제_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/lines/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선의_구간_추가_요청(Long id, Long upStationId,
		Long downStationId, Integer distance) {
		return RestAssured.given().log().all()
			.body(new SectionAddRequest(upStationId, downStationId, distance))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/lines/{id}/sections", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 지하철_노선의_구간_삭제_요청(Long id, Long stationId) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.param("stationId", stationId)
			.when().delete("/lines/{id}/sections", id)
			.then().log().all()
			.extract();
	}

	public static LineResponse 지하철_신분당선_노선_생성() {
		StationResponse upStation = 지하철역_리스폰_변환(지하철역_생성_요청(신사역_이름));
		StationResponse downStation = 지하철역_리스폰_변환(지하철역_생성_요청(논현역_이름));
		return 지하철_노선_리스폰_변환(지하철_노선_생성_요청(신분당선_이름, 신분당선_색상, upStation.getId(), downStation.getId(), 10));
	}

	public static LineResponse 지하철_경강선_노선_생성() {
		StationResponse upStation = 지하철역_리스폰_변환(지하철역_생성_요청(판교역_이름));
		StationResponse downStation = 지하철역_리스폰_변환(지하철역_생성_요청(이매역_이름));
		return 지하철_노선_리스폰_변환(지하철_노선_생성_요청(경강선_이름, 경강선_색상, upStation.getId(), downStation.getId(), 10));
	}

	public static LineResponse 지하철_노선_생성(String name, String color, Long upStationId, Long downStationId,
		Integer distance) {
		return 지하철_노선_리스폰_변환(지하철_노선_생성_요청(name, color, upStationId, downStationId, distance));
	}

	public static LineResponse 지하철_노선_리스폰_변환(ExtractableResponse<Response> response) {
		return response.as(LineResponse.class);
	}
}
