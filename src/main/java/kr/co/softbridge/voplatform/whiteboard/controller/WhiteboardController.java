package kr.co.softbridge.voplatform.whiteboard.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.softbridge.voplatform.commons.service.TloLogService;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgDeleteDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgSelectDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgUpdateDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgUploadDto;
import kr.co.softbridge.voplatform.whiteboard.service.WhiteboardService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/wImg")
@RestController
public class WhiteboardController {
	//
	private final WhiteboardService whiteboardService;
	//
	@Autowired
    private TloLogService tloLogService;
	//
	/**
     * <pre>
     * @Method Name : wimgUpload
     * 1. 개요 : 화이트보드 이미지 정보 저장 (wimgUpload)
     * 2. 처리내용 : 화이트보드 이미지 정보 저장 (wimgUpload)
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 12. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	:
     */
	@ApiOperation(value = "화이트보드 이미지 정보 저장")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImgUploadDto> wimgUpload(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	ResponseEntity<ImgUploadDto> result = whiteboardService.wimgUpload(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010601", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
	/**
     * <pre>
     * @Method Name : wimgUpdate
     * 1. 개요 : 화이트보드 이미지 정보 수정 (wimgUpdate)
     * 2. 처리내용 : 화이트보드 이미지 정보 수정 (wimgUpdate)
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 12. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	:
     */
	@ApiOperation(value = "화이트보드 이미지 정보 수정")
    @PostMapping(value = "/move", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImgUpdateDto> wimgUpdate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	ResponseEntity<ImgUpdateDto> result = whiteboardService.wimgUpdate(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010602", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
	/**
     * <pre>
     * @Method Name : wimgDelete
     * 1. 개요 : 화이트보드 이미지 정보 삭제 (wimgDelete)
     * 2. 처리내용 : 화이트보드 이미지 정보 삭제 (wimgDelete)
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 12. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	:
     */
	@ApiOperation(value = "화이트보드 이미지 정보 삭제")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImgDeleteDto> wimgDelete(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	ResponseEntity<ImgDeleteDto> result = whiteboardService.wimgDelete(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010604", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
	/**
     * <pre>
     * @Method Name : wimgSelect
     * 1. 개요 : 화이트보드 이미지 정보 조회 (wimgSelect)
     * 2. 처리내용 : 화이트보드 이미지 정보 조회 (wimgSelect)
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 12. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	:
     */
	@ApiOperation(value = "화이트보드 이미지 정보 조회")
    @PostMapping(value = "/select", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImgSelectDto> wimgSelect(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	ResponseEntity<ImgSelectDto> result = whiteboardService.wimgSelect(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010603", result.getBody().getResultCode(), paramMap);
    	//
		return result;
	}
	//
}
