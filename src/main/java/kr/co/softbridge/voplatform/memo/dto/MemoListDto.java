package kr.co.softbridge.voplatform.memo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MemoListDto {
	//
	@ApiParam(value = "메모 ID", required = true, example = "01")
	private int memoId;
	//
	@ApiParam(value = "메모지 색", required = true, example = "blue")
	private String color;
	//
	@ApiParam(value = "메모내용", required = true, example = "메모지 작업 중")
	private String content;
	//
	@ApiParam(value = "메모 너비 비율", required = true, example = "99.99")
	private Float widthRatio;
	//
	@ApiParam(value = "메모 높이 비율", required = true, example = "99.99")
	private Float heightRatio;
	//
	@JsonProperty("xCoordinateRatio")
	@ApiParam(value = "메모 x좌표 비율", required = true, example = "99.99")
	private Float xCoordinateRatio;
	//
	@JsonProperty("yCoordinateRatio")
	@ApiParam(value = "메모 y좌표 비율", required = true, example = "99.99")
	private Float yCoordinateRatio;
	//
	@ApiParam(value = "메모 잠금 구분", required = true, example = "N")
	private String editingYn;
	//
	@ApiParam(value = "등록자 혹은 수정자 ID", required = true, example = "streamer2")
	private String userId;
	//
	@ApiParam(value = "등록자 혹은 수정자 이름", required = true, example = "김모씨")
	private String userNm;
	//
}
