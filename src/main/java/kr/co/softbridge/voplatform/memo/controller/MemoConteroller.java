package kr.co.softbridge.voplatform.memo.controller;

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
import kr.co.softbridge.voplatform.memo.dto.MemoCreateDto;
import kr.co.softbridge.voplatform.memo.dto.MemoDeleteDto;
import kr.co.softbridge.voplatform.memo.dto.MemoSelectDto;
import kr.co.softbridge.voplatform.memo.dto.MemoUpdateDto;
import kr.co.softbridge.voplatform.memo.service.MemoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/snotes")
@RestController
public class MemoConteroller {
	//
	private final MemoService memoService;
	//
	@Autowired
    private TloLogService tloLogService;
	//
	/**
     * <pre>
     * @Method Name : memoCreate
     * 1. 개요 : 포스트잇 생성
     * 2. 처리내용 : 포스트잇 생성
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 08.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: MemoCreateDto
     */
	@ApiOperation(value = "메모 생성", notes = "포스트잇 메모를 생성")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoCreateDto> memoCreate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	// memoCreate Service 호출
    	ResponseEntity<MemoCreateDto> result = memoService.memoCreate(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010501", result.getBody().getResultCode(), paramMap);
    	//
		return result;
		//
	}
	//
	/**
     * <pre>
     * @Method Name : memoSelect
     * 1. 개요 : 포스트잇 조회
     * 2. 처리내용 : 포스트잇 조회
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 09.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: MemoSelectDto
     */
	@ApiOperation(value = "메모 조회", notes = "포스트잇 메모를 조회")
    @PostMapping(value = "/select", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoSelectDto> memoSelect(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	// memoSelect Service 호출
    	ResponseEntity<MemoSelectDto> result = memoService.memoSelect(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010503", result.getBody().getResultCode(), paramMap);
    	//
    	return result;
	}
	//
	/**
     * <pre>
     * @Method Name : memoUpdate
     * 1. 개요 : 포스트잇 수정
     * 2. 처리내용 : 포스트잇 수정
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 09.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: MemoUpdateDto
     */
	@ApiOperation(value = "메모 수정", notes = "포스트잇 메모를 수정")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoUpdateDto> memoUpdate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	// memoUpdate Service 호출
    	ResponseEntity<MemoUpdateDto> result = memoService.memoUpdate(request, paramMap);
		//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010502", result.getBody().getResultCode(), paramMap);
    	//
		return result;
		//
	}
	//
	/**
     * <pre>
     * @Method Name : memoDelete
     * 1. 개요 : 포스트잇 삭제
     * 2. 처리내용 : 포스트잇 삭제
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 09.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: MemoDeleteDto
     */
	@ApiOperation(value = "메모 삭제", notes = "포스트잇 메모를 삭제")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoDeleteDto> memoDelete(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
		// memoDelete Service 호출
    	ResponseEntity<MemoDeleteDto> result = memoService.memoDelete(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010504", result.getBody().getResultCode(), paramMap);
    	//
		return result;
		//
	}
	//
	
}
