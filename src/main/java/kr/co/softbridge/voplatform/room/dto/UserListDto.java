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
public class UserListDto {
	//
	@ApiParam(value = "참여자 ID", required = true, example = "user1")
    private String userId;
	//
	@ApiParam(value = "참여자 이름", required = true, example = "홍길동")
    private String userNm;
	//
	@ApiParam(value = "참여자 구분", required = true, example = "1001")
    private String userGb;
}
