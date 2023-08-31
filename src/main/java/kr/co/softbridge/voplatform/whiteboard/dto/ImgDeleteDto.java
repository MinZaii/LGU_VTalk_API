package kr.co.softbridge.voplatform.whiteboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ImgDeleteDto {
	//
	@ApiParam(value = "응답 코드", required = true, example = "000000")
    private String resultCode;
	//
	@ApiParam(value = "응답 메시지", required = true, example = "성공")
    private String resultMsg;
	//
	@ApiParam(value = "방 번호", required = true, example = "1")
    private String roomNo;
	//
	@ApiParam(value = "삭제자 ID", required = true, example = "streamer1")
    private String userId;
	//
	@ApiParam(value = "삭제자 이름", required = true, example = "관리자")
    private String userNm;
	//
}
