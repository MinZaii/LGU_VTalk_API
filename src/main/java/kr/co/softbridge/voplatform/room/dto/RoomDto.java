package kr.co.softbridge.voplatform.room.dto;

import java.util.List;

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
public class RoomDto {
	//
	@ApiParam(value = "회의실 번호", required = true, example = "1")
    private String roomNo;
	//
	@ApiParam(value = "회의실 제목", required = true, example = "20XX회 화상회의")
    private String title;
	//
	@ApiParam(value="룸 설명", required = true)
	private String objective;
	//
	@ApiParam(value="노출 여부", required = true)
	private String viewYn;
	//
	@ApiParam(value="회의 품질", required = true)
	private String quality;
	//
	@ApiParam(value="최대 참여자 수", required = true)
	private int maxPeople;
	//
	@ApiParam(value="회의 타입", required = true)
	private String roomType;
	//
	@ApiParam(value="화면공유유형", required = true)
	private String screenType;
	//
	@ApiParam(value = "방송 시작날짜", required = true, example = "20210101000000")
    private String startDt;
	//
	@ApiParam(value = "방송 종료날짜", required = true, example = "20210102000000")
    private String endDt;
	//
	@ApiParam(value="등록 일시", required = true)
	private String regDt;
	//
	@ApiParam(value="수정 일시", required = true)
	private String udtDt;
	//
	@ApiParam(value="매니저 ID", required = true)
	private String managerId;
	//
	// 빼야할 것
	@ApiParam(value="비밀번호", required = true)
	private String joinCode;
	// 빼야할 것
	@ApiParam(value="회의 상태", required = true)
	private String roomStatus;
	//
	@ApiParam(value = "참여자 목록", required = true, example = "1")
    private List<UserListDto> userList;
	
}
