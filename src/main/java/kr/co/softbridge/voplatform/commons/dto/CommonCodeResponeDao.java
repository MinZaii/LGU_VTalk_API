package kr.co.softbridge.voplatform.commons.dto;

import java.util.List;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonCodeResponeDao {
	@ApiParam(value = "결과 코드", required = true, example = "000000")
	private String						resultCode;
	@ApiParam(value = "결과 메시지", required = true, example = "성공")
	private String 						resultMsg;
	@ApiParam(value = "코드 목록", required = true, example = "")
	private List<CommonCodeDto> 		codeList;
}
