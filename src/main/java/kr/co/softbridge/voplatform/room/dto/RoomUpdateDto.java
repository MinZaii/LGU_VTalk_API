package kr.co.softbridge.voplatform.room.dto;

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
public class RoomUpdateDto {
	//
	@ApiParam(value = "응답 코드", required = true, example = "000000")
    private String resultCode;
	//
	@ApiParam(value = "응답 메시지", required = true, example = "성공")
    private String resultMsg;
	//
	@ApiParam(value = "방 번호", required = true, example = "01")
	private String roomNo;
	//
}
