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
public class RoomHistDto {
	//
	@ApiParam(value = "회의실 제목", required = true, example = "20XX회 화상회의")
    private String roomTitle;
	//
	@ApiParam(value = "방송 시작날짜", required = true, example = "20210101000000")
    private String startDt;
	//
	@ApiParam(value = "방송 종료날짜", required = true, example = "20210102000000")
    private String endDt;
	//
	@ApiParam(value="룸 설명", required = true)
	private String roomTopic;
	//
	@ApiParam(value="매니저 ID", required = true)
	private String managerId;
	//
	@ApiParam(value="방 비밀번호", required = true)
	private String joinCode;
	//
	@ApiParam(value="방 상태", required = true)
	private String roomStatus;
	//
	@ApiParam(value="방 품질", required = true)
	private String quality;
	//
	@ApiParam(value="방 최대 참여자 수", required = true)
	private String maxPeople;
	//
	@ApiParam(value="방 타입", required = true)
	private String roomType;
	//
	@ApiParam(value="영상 타입", required = true)
	private String screenType;
	//
}
