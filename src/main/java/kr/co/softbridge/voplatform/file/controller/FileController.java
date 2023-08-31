package kr.co.softbridge.voplatform.file.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.softbridge.voplatform.commons.constants.commonConstant;
import kr.co.softbridge.voplatform.commons.exception.ApiException;
import kr.co.softbridge.voplatform.commons.service.TloLogService;
import kr.co.softbridge.voplatform.commons.util.StringUtil;
import kr.co.softbridge.voplatform.file.dto.FileDeleteResponseDto;
import kr.co.softbridge.voplatform.file.dto.FileDownloadReqDto;
import kr.co.softbridge.voplatform.file.dto.FileSelectDto;
import kr.co.softbridge.voplatform.file.dto.FileUploadReqtDto;
import kr.co.softbridge.voplatform.file.dto.FileUploadResponseDto;
import kr.co.softbridge.voplatform.file.mapper.FileMapper;
import kr.co.softbridge.voplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {
	//
	private final FileService fileService;
	//
	private final FileMapper fileMapper;
	//
	@Autowired
    private TloLogService tloLogService;
	//
	/**
     * <pre>
     * @Method Name : fileList
     * 1. 개요 : 파일 목록 조회
     * 2. 처리내용 : 파일 목록 조회
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 11.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: ResponseEntity<FileSelectDto>
     */
	@ApiOperation(value = "파일 목록", notes = "파일 목록 조회")
	@PostMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileSelectDto> fileList(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	// fileList Service 호출
    	ResponseEntity<FileSelectDto> result = fileService.fileList(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010402", result.getBody().getResultCode(), paramMap);
    	//
    	return result;
    	//
	}
	//
	/**
     * <pre>
     * @Method Name : fileUpload
     * 1. 개요 : 파일 업로드
     * 2. 처리내용 : 파일 업로드
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: null
     */
	@ApiOperation(value = "파일 업로드", notes = "파일 업로드")
	@PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileUploadResponseDto> fileUpload(HttpServletRequest request, @Valid FileUploadReqtDto fileUploadRequestDto) throws Exception {
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
    	// fileUpload Service 호출
    	ResponseEntity<FileUploadResponseDto> result = fileService.fileUpload(request, fileUploadRequestDto);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("roomNo", fileUploadRequestDto.getRoomNo());
    	//
    	if(!String.valueOf(fileUploadRequestDto.getUserId()).equals("")) {
    		paramMap.put("userId", String.valueOf(fileUploadRequestDto.getUserId()));
    	}
    	//
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010401", result.getBody().getResultCode(), paramMap);
    	//
    	return result;
    	//
	}
	//
	/**
     * <pre>
     * @Method Name : fileDelete
     * 1. 개요 : 파일 삭제
     * 2. 처리내용 : 파일 삭제
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 15.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: null
     */
	@PostMapping(value="/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileDeleteResponseDto> fileDelete(HttpServletRequest request,  @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
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
    	ResponseEntity<FileDeleteResponseDto> result = fileService.fileDelete(request, paramMap);
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010404", result.getBody().getResultCode(), paramMap);
    	//
    	return result;
    	//
	}
	//
	/**
     * <pre>
     * @Method Name : fileDownload
     * 1. 개요 : 파일 다운로드
     * 2. 처리내용 : 파일 다운로드
     * 3. 작성자	: 이민재
     * 4. 작성일	: 2021. 11. 29.
     * </pre>
     * 
     * @Parameter	: JSON Map
     * @ReturnType	: null
     */
	@PostMapping(value="/download")
	public Object fileDownload(HttpServletRequest request, @Valid FileDownloadReqDto fileDownloadReqDto) throws Exception {
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
    	//
    	/*
		 * 로그 찍기위해
		 * End Date Pattern 생성
		 */
		String endServerTime = simpleDateFormat.format(new Date());
    	String endServerTime2 = simpleDateFormat2.format(new Date());
    	//
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", fileDownloadReqDto.getUserId());
    	paramMap.put("roomNo", fileDownloadReqDto.getRoomNo());
    	//
    	String resultCode = "000000";
    	//
		// * 01. 넘어온 Parameter 검증
		if(
				StringUtil.isEmpty(fileDownloadReqDto.getFilePath())
				|| StringUtil.isEmpty(fileDownloadReqDto.getFileSeq())
				|| StringUtil.isEmpty(fileDownloadReqDto.getRoomNo())
				|| StringUtil.isEmpty(fileDownloadReqDto.getUserId())
				|| StringUtil.isEmpty(fileDownloadReqDto.getUserNm())
			) {
			//
			resultCode = "000001";
			//
			tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010403", resultCode, paramMap);
			//
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileDeleteResponseDto
	    					.builder()
	    					.resultCode("000001")				// 필수정보 누락 Code
	    					.resultMsg(commonConstant.M_000001)	// 필수정보 누락 Msg
	    					.build());
		}
		//
    	HashMap<String, Object> 	fileMap 	= new HashMap<String, Object>();
    	fileMap.put("roomNo"		, fileDownloadReqDto.getRoomNo());
    	fileMap.put("fileSeq"		, fileDownloadReqDto.getFileSeq());
    	//
    	// 파일 유무 체크
    	int fileSel = fileMapper.fileSel(fileMap);
    	//
    	if (fileSel <= 0) {
    		//
    		resultCode = "002001";
    		//
    		tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010403", resultCode, paramMap);
    		//
    		return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileDeleteResponseDto
	    					.builder()
	    					.resultCode("002001")				// 파일이 없습니다 Code
	    					.resultMsg(commonConstant.M_002001) // 파일이 없습니다 Msg
	    					.build());
    	}
    	// userNm 문자열 길이 체크
		String userNmLen = fileDownloadReqDto.getUserNm();
		//
		if(userNmLen.length() > 100) {
			resultCode = "000002";
    		//
    		tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010403", resultCode, paramMap);
    		//
    		return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileDeleteResponseDto
	    					.builder()
	    					.resultCode("000002")				// 올바른 입력값이 아닙니다. Code
	    					.resultMsg(commonConstant.M_000002) // 올바른 입력값이 아닙니다. Msg
	    					.build());
		}
    	// 파일 다운로드 Service 호출
    	ResponseEntity<byte[]> result = fileService.FileDownload(request, fileDownloadReqDto);
    	//
    	if(!HttpStatus.OK.equals(result.getStatusCode())) {
    		//
    		resultCode = "002001";
    		//
    	}
    	//
    	tloLogService.tloLog(request, startServerTime2, endServerTime2, "S010403", resultCode, paramMap);
    	//
		return result;
		//
	}
}
