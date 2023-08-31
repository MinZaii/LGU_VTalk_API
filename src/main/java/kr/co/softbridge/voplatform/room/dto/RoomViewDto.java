package kr.co.softbridge.voplatform.room.dto;

import java.util.List;

import javax.validation.constraints.Min;

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
public class RoomViewDto {
	
	@ApiParam(value = "응답 코드", required = true, example = "000000")
    private String resultCode;
	
	@ApiParam(value = "응답 메시지", required = true, example = "성공")
    private String resultMsg;
	
	@ApiParam(value = "화상회의 번호", required = true, example = "성공")
    private String roomNo;
	
	@ApiParam(value = "화상회의 제목", required = true, example = "성공")
    private String title;
	
	@ApiParam(value = "화상회의 설명", required = true, example = "성공")
    private String objective;
	
	@ApiParam(value = "화상회의 시작일시", required = true)
    private String startDt;
	
	@ApiParam(value = "화상회의 종료일시", required = true)
    private String endDt;
	
	@ApiParam(value = "노출여부", required = true)
    private String viewYn;
	
	@ApiParam(value = "회의 품질", required = true)
    private String quality;
	
	@Min(value=1)
	@ApiParam(value = "최대 참여자 수", required = true)
    private int maxPeople;
	
	@ApiParam(value = "회의 타입", required = true)
    private String roomType;
	
	@ApiParam(value = "화면공유 유형", required = true)
    private String screenType;
	
	@ApiParam(value = "회의 등록 일시", required = true)
    private String regDt;
	
	@ApiParam(value = "회의 수정 일시", required = true)
    private String udtDt;
	
	@ApiParam(value = "관리자 ID", required = true)
    private String managerId;
	
	@ApiParam(value = "참여자 목록", required = true)
    private List<UserListDto> userList;
}
