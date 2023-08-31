package kr.co.softbridge.voplatform.room.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.softbridge.voplatform.commons.service.TloLogService;
import kr.co.softbridge.voplatform.room.dto.RoomDeleteDto;
import kr.co.softbridge.voplatform.room.dto.RoomListDto;
import kr.co.softbridge.voplatform.room.dto.RoomResponeDto;
import kr.co.softbridge.voplatform.room.dto.RoomUpdateDto;
import kr.co.softbridge.voplatform.room.dto.RoomViewDto;
import kr.co.softbridge.voplatform.room.service.RoomService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
	//
	private static final Logger logger 			= LogManager.getLogger(RoomController.class);
	
	private final RoomService roomService;
	//
	@Autowired
    private TloLogService tloLogService;
	//
	/**
     * <pre>
     * @Method Name : roomList
     * 1. 개요 : 회의 목록 조회
     * 2. 처리내용 : 회의 목록 조회
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 10. 29.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: ResponseEntity<RoomListDto>
     */
	@ApiOperation(value = "room 목록", notes = "room 목록 조회")
	@PostMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomListDto> roomList(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
    	//
		// room 정보 조회 서비스 호출
		ResponseEntity<RoomListDto> result = roomService.RoomList(request, paramMap);
		//
		/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010301", result.getBody().getResultCode(), paramMap);
		//
		return result;
		//
	}
	//
	/**
     * <pre>
     * @Method Name : roomView
     * 1. 개요 : 회의 상세 조회
     * 2. 처리내용 : 회의 상세 조회
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 10. 29.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: ResponseEntity<RoomViewDto>
     */
	@ApiOperation(value = "room 상세", notes = "room 상세 조회")
	@PostMapping(value="/view", produces = MediaType.APPLICATION_JSON_VALUE)
	//
	public ResponseEntity<RoomViewDto> roomView(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		//
		logger.info("==============API 호출 시 Parameter Log START=====================");
		logger.info(">>>>> || Method - view , request parameter - paramMap = " + paramMap.toString() );
		logger.info("==============API 호출 시 Parameter Log END=====================");
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
    	//
		// room 상세 조회

    	ResponseEntity<RoomViewDto> result = roomService.roomView(request, paramMap);
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010302", result.getBody().getResultCode(), paramMap);
    	//
    	logger.info("==============API 리턴 시 Parameter Log START=====================");
		logger.info(">>>>> || Method - view() , request parameter - ResponseEntity<RoomViewDto> result = " + result.toString() );
		logger.info("==============API 리턴 시 Parameter Log END=====================");
    	//
    	return result;
	}
	//
	//
	/**
     * <pre>
     * @Method Name : roomcreate
     * 1. 개요 : 회의 room 생성
     * 2. 처리내용 : 회의 room 생성
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 01.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: ResponseEntity<RoomResponeDto>
     */
	@ApiOperation(value = "room 생성", notes = "room을 생성")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomResponeDto> roomCreate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		logger.info("==============API 호출 시 Parameter Log START=====================");
		logger.info(">>>>> || Method - create , request parameter - paramMap = " + paramMap.toString() );
		logger.info("==============API 호출 시 Parameter Log END=====================");
		
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
    	//
    	// room create 서비스 호출
    	ResponseEntity<RoomResponeDto> result = roomService.roomCreate(request, paramMap);
    	//
		/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010201", result.getBody().getResultCode(), paramMap);
    	//
    	logger.info("==============API 리턴 시 Parameter Log START=====================");
		logger.info(">>>>> || Method - view() , request parameter - ResponseEntity<RoomResponeDto> result = " + result.toString() );
		logger.info("==============API 리턴 시 Parameter Log END=====================");
    	//
    	return result;
	}
	
	/**
     * <pre>
     * @Method Name : roomUpdate
     * 1. 개요 : 회의 정보 수정
     * 2. 처리내용 : 회의 정보 수정
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 04.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: 
     */
	@ApiOperation(value = "회의 정보 수정", notes = "회의 정보 수정")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomUpdateDto> roomUpdate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		//
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
		//
    	// roomUpdate Service 호출
    	ResponseEntity<RoomUpdateDto> result = roomService.roomUpdate(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010303", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
	/**
     * <pre>
     * @Method Name : roomRecon
     * 1. 개요 : 회의 재소집 예약
     * 2. 처리내용 : 회의 재소집 예약
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 18.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: 
     */
	@ApiOperation(value = "회의 재소집 예약", notes = "회의 재소집 예약")
    @PostMapping(value = "/recon", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomUpdateDto> roomRecon(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		//
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
    	// 재소집인지 , 수정인지 판별하여 비대면플랫폼과 HTTPS통신에서 roomStatus값 변경
    	// 수정일 땐 null / 재소집일 때 paramMap에 넣어줌.
    	paramMap.put("statusGb", "A");
		//
    	// roomUpdate Service 호출
    	ResponseEntity<RoomUpdateDto> result = roomService.roomUpdate(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010202", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
	/**
     * <pre>
     * @Method Name : roomDelete
     * 1. 개요 : 회의 방 삭제
     * 2. 처리내용 : 회의 방 삭제
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 17.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: RoomDeleteDto
     */
	@ApiOperation(value = "회의 방 삭제", notes = "회의 방 삭제")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomDeleteDto> roomDelete(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
		//
		/*
		 * 로그 찍기위해
		 * Start Date Pattern 생성
		 */
		String pattern = "yyyy-MM-dd HH:mm:ss";
    	String pattern2 = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    	String startServerTime = simpleDateFormat.format(new Date());
    	String startServerTime2 = simpleDateFormat2.format(new Date());
    	paramMap.put("nowTime", startServerTime);
		//
    	ResponseEntity<RoomDeleteDto> result = roomService.roomDelete(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010304", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
}
