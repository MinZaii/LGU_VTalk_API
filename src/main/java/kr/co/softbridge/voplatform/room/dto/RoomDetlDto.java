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
public class RoomDetlDto {
	//
	@ApiParam(value = "회의실 번호", required = true, example = "1")
    private String roomNo;
	//
	@ApiParam(value = "회의실 제목", required = true, example = "20XX회 화상회의")
    private String title;
	//
	@ApiParam(value = "회의 설명", required = true, example = "XX주제 회의")
    private String objective;
	//
	@ApiParam(value = "방송 시작날짜", required = true, example = "20210101000000")
    private String startDt;
	//
	@ApiParam(value = "방송 종료날짜", required = true, example = "20210102000000")
    private String endDt;
	//
	@ApiParam(value = "노출 여부", required = true, example = "Y")
    private String viewYn;
	//
	@ApiParam(value = "회의 품질", required = true, example = "01")
    private String quality;
	//
	@ApiParam(value = "최대 참여자 수", required = true, example = "10")
    private int maxPeople;
	//
	@ApiParam(value = "회의 타입", required = true, example = "01")
    private String roomType;
	//
	@ApiParam(value = "화면공유 유형", required = true, example = "01")
    private String screenType;
	//
	@ApiParam(value = "등록 일시", required = true, example = "20210102000000")
    private String regDt;
	//
	@ApiParam(value = "수정 일시", required = true, example = "20210102000000")
    private String udtDt;
	//
	@ApiParam(value = "관리자 ID", required = true, example = "20210102000000")
    private String managerId;
	//
	
}
