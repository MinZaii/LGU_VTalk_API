package kr.co.softbridge.voplatform.whiteboard.dto;

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
public class WhiteboardImgDto {
	//
	@ApiParam(value = "이미지 ID", required = true, example = "1")
	private int imgId;
	//
	@ApiParam(value = "이미지 URL", required = true)
	private String imgUrl;
	//
	@ApiParam(value = "이미지 너비 비율", required = true, example = "99.99")
	private Float widthRatio;
	//
	@ApiParam(value = "이미지 높이 비율", required = true, example = "99.99")
	private Float heightRatio;
	//
	@JsonProperty("xCoordinateRatio")
	@ApiParam(value = "이미지 x좌표 비율", required = true, example = "99.99")
	private Float xCoordinateRatio;
	//
	@JsonProperty("yCoordinateRatio")
	@ApiParam(value = "이미지 y좌표 비율", required = true, example = "99.99")
	private Float yCoordinateRatio;
	//
	@ApiParam(value = "등록자 혹은 수정자 ID", required = true)
	private String userId;
	//
	@ApiParam(value = "등록자 혹은 수정자 NM", required = true)
	private String userNm;
	//
}
