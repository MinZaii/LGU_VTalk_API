package kr.co.softbridge.voplatform.memo.dto;

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
public class MemoCreateDto {
	//
	@ApiParam(value = "응답 코드", required = true, example = "000000")
    private String resultCode;
	//
	@ApiParam(value = "응답 메시지", required = true, example = "성공")
    private String resultMsg;
	//
	@ApiParam(value = "메모 ID", required = true, example = "01")
	private int memoId;
	//
	@ApiParam(value = "등록자 ID", required = true, example = "streamer1")
	private String userId;
	//
	@ApiParam(value = "등록자 이름", required = true, example = "김모씨")
	private String userNm;
	//
}
