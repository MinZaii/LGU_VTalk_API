package kr.co.softbridge.voplatform.file.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softbridge.voplatform.commons.constants.commonConstant;
import kr.co.softbridge.voplatform.commons.exception.ApiException;
import kr.co.softbridge.voplatform.commons.util.HttpsUtil;
import kr.co.softbridge.voplatform.commons.util.MultipartUtil;
import kr.co.softbridge.voplatform.commons.util.StringUtil;
import kr.co.softbridge.voplatform.file.dto.FileDeleteResponseDto;
import kr.co.softbridge.voplatform.file.dto.FileDownloadFileNmDto;
import kr.co.softbridge.voplatform.file.dto.FileDownloadReqDto;
import kr.co.softbridge.voplatform.file.dto.FileListDto;
import kr.co.softbridge.voplatform.file.dto.FileSelectDto;
import kr.co.softbridge.voplatform.file.dto.FileSeqDto;
import kr.co.softbridge.voplatform.file.dto.FileUploadReqtDto;
import kr.co.softbridge.voplatform.file.dto.FileUploadResponseDto;
import kr.co.softbridge.voplatform.file.mapper.FileMapper;
import kr.co.softbridge.voplatform.room.mapper.RoomMapper;

@Service
public class FileService {
	//
	private static final Logger logger 			= LogManager.getLogger(FileService.class);
	//
	@Value("${hcd.svcCode}")
	private String svcCode;
	//
	@Value("${hcd.svcUrl}")
	private String svcUrl;
	//
	@Value("${hcd.apiUrl}")
	private String apiUrl;
	//
	@Value("${hcd.tokenUrl}")
	private String tokenUrl;
	//
	@Value("${hcd.roomCreate}")
	private String rcUrl;
	//
	@Value("${hcd.roomUpdate}")
	private String ruUrl;
	//
	@Value("${hcd.roomTokenUrl}")
	private String rtUrl;
	//
	@Value("${hcd.fileListUrl}")
	private String fileListUrl;
	//
	@Value("${hcd.fileUploadUrl}")
	private String fileUploadUrl;
	//
	@Value("${hcd.fileDeleteUrl}")
	private String fileDeleteUrl;
	//
	@Value("${hcd.fileDownload}")
	private String fileDownload;
	//
	private HttpsUtil httpsUtil = new HttpsUtil();
	private MultipartUtil multipartUtil = new MultipartUtil();
	//
	@Autowired
    private RoomMapper roomMapper;
	//
	@Autowired
    private FileMapper fileMapper;
	//
	/*************************************************************************************************************
	* @brief  : file 목록 조회 service
	* @method : fileList
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<FileSelectDto> fileList(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		List<FileSeqDto>				getfileSeq		= null;
		List<FileListDto> 				fileArrayList 	= new ArrayList<FileListDto>();
		HashMap<String, Object> 		fileMap			= new HashMap<String, Object>();
		int 							fileTotalCnt	= 0;
		//
		try {
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo"))) 
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("page")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("pageSize")))
			){
					//
					throw new ApiException("000001" , commonConstant.M_000001);
					//
			}else {
				fileMap.put("roomNo"		, String.valueOf(paramMap.get("roomNo")));
				fileMap.put("userId"		, String.valueOf(paramMap.get("userId")));
				fileMap.put("userNm"		, String.valueOf(paramMap.get("userNm")));
			}
			// userNm 문자열 길이 체크
			String userNmLen = String.valueOf(paramMap.get("userNm"));
			//
			if(userNmLen.length() > 100) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			
			// *************************************************************************************************************
			// * 02. 파일 목록 개수 조회
			// *************************************************************************************************************
			fileTotalCnt	= fileMapper.fileCntSel(paramMap);
			//
			// *************************************************************************************************************
			// * 03. 페이징 설정
			// *************************************************************************************************************
			//
			int pageNum 	= Integer.parseInt(StringUtil.null2void(paramMap.get("page"),"1"));
			int pageSize	= Integer.parseInt(StringUtil.null2void(paramMap.get("pageSize"),"100"));
			if(pageNum == 1) {
				pageNum = pageNum -1 ;
			}else {
				pageNum = (pageNum-1) * pageSize;
			}
			fileMap.put("pageNum"		, pageNum);
			fileMap.put("pageSize"		, pageSize);
			//
			// *************************************************************************************************************
			// * 04. 페이징에 따른 fileSeq 조회
			// *************************************************************************************************************
			getfileSeq 		= fileMapper.fileSeqSel(fileMap);
			//
			int fileSeqCnt 	= getfileSeq.size();
			//
			for(int i = 0; i < fileSeqCnt; i++) {
				//
				String 				fileSeq		= getfileSeq.get(i).getFileSeq().toString();
				//
				fileMap.put("fileSeq"			, fileSeq);
				//
				FileListDto			fileList	= fileMapper.fileListSel(fileMap);
				//
				fileArrayList.add(fileList);
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[FileList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[FileList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileSelectDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileSelectDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(FileSelectDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.fileCnt(fileTotalCnt)
    					.fileList(fileArrayList)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : file 업로드 service
	* @method : fileUpload
	* @author : 이민재
	**************************************************************************************************************/
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<FileUploadResponseDto> fileUpload(HttpServletRequest request, FileUploadReqtDto fileUploadReqtDto) {
		//
		String 						fileSeq 		= null;
		String						https_Url		= null;
		HashMap<String, Object> 	roomMap			= new HashMap<String, Object>();
		HashMap<String, Object> 	fileMap			= new HashMap<String, Object>();
		//	
		try {
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(StringUtil.isEmpty(fileUploadReqtDto.getRoomNo()) 
					|| StringUtil.isEmpty(fileUploadReqtDto.getUserId())
					|| StringUtil.isEmpty(fileUploadReqtDto.getUserNm())
					|| fileUploadReqtDto.getFile().isEmpty()
				){
					throw new ApiException("000001" , commonConstant.M_000001);
				}
			//
			// userNm 문자열 길이 체크
			String userNmLen = fileUploadReqtDto.getUserNm();
			//
			if(userNmLen.length() > 100) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// 파일 용량 체크 
			// 50MB 넘으면 Exception 내버리기 ~
			long bytes = fileUploadReqtDto.getFile().getSize();
			long limitBytes = 1024 * 1024 * 50;
			if(bytes >= limitBytes) {
				throw new ApiException("002006" , commonConstant.M_002006);
			}
			// *************************************************************************************************************
			// * 02. 서비스 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			JSONObject jsonData_token = new JSONObject();
			//
			jsonData_token.put("svcCode"	, svcCode);
			jsonData_token.put("svcDomain"	, svcUrl);
			jsonData_token.put("userId"		, fileUploadReqtDto.getUserId());
			jsonData_token.put("svcNm"		, commonConstant.SERVICE_NAME);
			https_Url = apiUrl	+ tokenUrl;
			// JSON형식으로 HTTPS 통신
			JSONObject response_token = httpsUtil.postData(https_Url, jsonData_token);
			//
			String resultCode_token 	= (String)response_token.get("resultCode");
			// Https 통신이 정상이 아니면
			if(!"000000".equals(resultCode_token)) {
				//
				throw new ApiException("000004",commonConstant.M_000004);
				//
			}
			//
			// tokenInfo 필요해서 파라미터 선언
			String svcToken = (String)response_token.get("tokenInfo");
			//
			// *************************************************************************************************************
			// * 03. 룸 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			JSONObject jsonData_roomToken = new JSONObject();
			jsonData_roomToken.put("roomCode"	, fileUploadReqtDto.getRoomNo());
			jsonData_roomToken.put("svcCode"	, svcCode);
			jsonData_roomToken.put("userId"		, fileUploadReqtDto.getUserId());
			jsonData_roomToken.put("userNm"		, fileUploadReqtDto.getUserNm());
			//
			https_Url = "";
			https_Url = apiUrl + rtUrl; // api 호출 url + roomTolken 호출 url
			//
			JSONObject response_roomToken = httpsUtil.postData(https_Url, jsonData_roomToken);
			//
			String resultCode_roomToken 	= (String)response_roomToken.get("resultCode");
			if(!"000000".equals(resultCode_roomToken)) {
				//
				throw new ApiException("002005",commonConstant.M_002005);
				//
			}
			// 생성된 roomToken 가져오기
			String roomToken = (String)response_roomToken.get("roomToken");
			//
			// *************************************************************************************************************
			// * 04. 파일 업로드 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			//
			HashMap<String, Object> 	connMap			= new HashMap<String, Object>();
			//
			connMap.put("roomCode"		, fileUploadReqtDto.getRoomNo());
			connMap.put("roomToken"		, roomToken);
			connMap.put("svcTarget"		, String.valueOf("R0"));
			connMap.put("userId"		, String.valueOf(fileUploadReqtDto.getUserId()));
			connMap.put("userNm"		, fileUploadReqtDto.getUserNm());
			//
			https_Url = "";
			https_Url = apiUrl + fileUploadUrl; // api url 과 파일 업로드 url 호출
			// multipartFile -> File 변환
			File multiFile 		= multipartUtil.multiFileToFile((MultipartFile)fileUploadReqtDto.getFile());
			// 비대면플랫폼으로 업로드 요청
			JSONObject response = httpsUtil.multiFileJSON(https_Url, connMap, multiFile);
			// tmp 경로 파일 삭제
			multiFile.delete();
			//
			if("000000".equals(String.valueOf(response.get("resultCode")))) {
				//
				fileSeq = String.valueOf(response.get("fileSeq"));
				//
				// *************************************************************************************************************
				// * 05. TB_FILE 업로드 파일 정보 저장
				// *************************************************************************************************************
				// 파일정보 세팅
				fileMap.put("roomNo"	, String.valueOf(fileUploadReqtDto.getRoomNo()));
				fileMap.put("regId"		, String.valueOf(fileUploadReqtDto.getUserId()));
				fileMap.put("regNm"		, String.valueOf(fileUploadReqtDto.getUserNm()));
				fileMap.put("fileSeq"	, fileSeq);
				fileMap.put("fileUrl"	, String.valueOf(response.get("fileUrl")));
				fileMap.put("filePath"	, String.valueOf(response.get("filePath")));
				fileMap.put("fileNm"	, response.get("fileRealNm"));
				fileMap.put("fileSize"	, String.valueOf(response.get("fileSize")));
				fileMap.put("shareType"	, String.valueOf(response.get("shareType")));
		    	fileMap.put("regDt"		, String.valueOf(response.get("startDt")));
		    	//
		    	int idx =  String.valueOf(response.get("fileNm")).lastIndexOf(".");
		    	if(idx > 0) {
		    		String fileExt = String.valueOf(response.get("fileNm")).substring(idx + 1);
		    		fileMap.put("fileExt", fileExt);
		    	}
		    	// TB_FILE TABLE INSERT
				int insertFileCnt = fileMapper.insertFile(fileMap);
				//
			}else {
				//
				throw new ApiException("002002",commonConstant.M_002002);
				//
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[FileUpload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[FileUpload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileUploadResponseDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileUploadResponseDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(FileUploadResponseDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.fileSeq(fileSeq)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : file 삭제 Service
	* @method : fileDelete
	* @author : 이민재
	**************************************************************************************************************/
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<FileDeleteResponseDto> fileDelete(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		String						https_Url		= null;
		HashMap<String, Object> 	roomMap			= new HashMap<String, Object>();
		HashMap<String, Object> 	connMap			= new HashMap<String, Object>();
		try {
			//
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if (
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("fileSeq")))
			) {
	    		throw new ApiException("000001", commonConstant.M_000001);
			}
			else {
				//
				int fileSel = fileMapper.fileSel(paramMap);
				//
				if(fileSel <= 0) {
					//
					throw new ApiException("002001", commonConstant.M_002001);
					//
				}
			}
			// userNm 문자열 길이 체크
			String userNmLen = String.valueOf(paramMap.get("userNm"));
			if(userNmLen.length()>100) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			//
			// *************************************************************************************************************
			// * 02. 서비스 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			JSONObject jsonData_token = new JSONObject();
			//
			jsonData_token.put("svcCode"	, svcCode);
			jsonData_token.put("svcDomain"	, svcUrl);
			jsonData_token.put("userId"		, paramMap.get("userId"));
			jsonData_token.put("svcNm"		, commonConstant.SERVICE_NAME);
			https_Url = apiUrl	+ tokenUrl;
			// JSON형식으로 HTTPS 통신
			JSONObject response_token 	= httpsUtil.postData(https_Url, jsonData_token);
			//
			String resultCode_token 	= (String)response_token.get("resultCode");
			// Https 통신이 정상이 아니면
			if(!"000000".equals(resultCode_token)) {
				//
				throw new ApiException("000004",commonConstant.M_000004);
				//
			}
			// tokenInfo 필요해서 파라미터 선언
			String svcToken 		= (String)response_token.get("tokenInfo");
			// 
			// *************************************************************************************************************
			// * 03. 룸 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			//
			JSONObject jsonData_roomToken 		= new JSONObject();
			jsonData_roomToken.put("roomCode"	, paramMap.get("roomNo"));
			jsonData_roomToken.put("svcCode"	, svcCode);
			jsonData_roomToken.put("userId"		, paramMap.get("userId"));
			jsonData_roomToken.put("userNm"		, paramMap.get("userNm"));
			//
			https_Url = "";
			https_Url = apiUrl + rtUrl; // api 호출 url + roomTolken 호출 url
			//
			JSONObject response_roomToken 		= httpsUtil.postData(https_Url, jsonData_roomToken);
			//
			String resultCode_roomToken 		= (String)response_roomToken.get("resultCode");
			if(!"000000".equals(resultCode_roomToken)) {
				//
				throw new ApiException("002005",commonConstant.M_002005);
				//
			}
			// 생성된 roomToken 가져오기
			String roomToken 		= (String)response_roomToken.get("roomToken");
			//
			// *************************************************************************************************************
			// * 04. 파일 삭제 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			connMap.put("roomCode" 			, String.valueOf(paramMap.get("roomNo")));
			connMap.put("userId" 			, String.valueOf(paramMap.get("userId")));
			connMap.put("userNm" 			, String.valueOf(paramMap.get("userNm")));
			connMap.put("roomToken" 		, String.valueOf(roomToken));
			connMap.put("fileSeq"			, String.valueOf(paramMap.get("fileSeq")));
			//
			https_Url = "";
			https_Url = apiUrl+fileDeleteUrl; //api 호출 url + 파일 삭제 url
			//
			JSONObject response_fileDelete 		= httpsUtil.multiJSON(https_Url, connMap);
			//
			String resultCode_fileDelete 		= (String)response_fileDelete.get("resultCode");
			//
			if("000000".equals(resultCode_fileDelete)) {
				// *************************************************************************************************************
				// * 05. TB_FILE 삭제
				// *************************************************************************************************************
				fileMapper.fileDelete(paramMap);
				//
			}else if("003002".equals(resultCode_fileDelete)){
				// 삭제 권한 없음.
				throw new ApiException("002004",commonConstant.M_002004);
				//
			}else {
				// 삭제를 실패하였습니다.
				throw new ApiException("002003",commonConstant.M_002003);
				//
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[FileDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[FileDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileDeleteResponseDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(FileDeleteResponseDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(FileDeleteResponseDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : file 다운로드 Service
	* @method : FileDownload
	* @author : 이민재
	**************************************************************************************************************/
	public <Boolen>ResponseEntity<byte[]> FileDownload(HttpServletRequest request, @Valid FileDownloadReqDto fileDownloadReqDto ) {
		//
		String						https_Url		= null;
		byte[] 						bytes			= new byte[] {};
		HttpHeaders 				httpHeaders 	= new HttpHeaders();
		HashMap<String, Object> 	downMap 		= new HashMap<String, Object>();
		FileInputStream				downFileIS		= null;
		ByteArrayOutputStream		bao				= null;
		//
		try {
			// *************************************************************************************************************
			// * 02. 서비스 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			JSONObject jsonData_token = new JSONObject();
			//
			jsonData_token.put("svcCode"	, svcCode);
			jsonData_token.put("svcDomain"	, svcUrl);
			jsonData_token.put("userId"		, String.valueOf(fileDownloadReqDto.getUserId()));
			jsonData_token.put("svcNm"		, commonConstant.SERVICE_NAME);
			//
			https_Url = apiUrl	+ tokenUrl;
			// JSON형식으로 HTTPS 통신
			JSONObject response_token = httpsUtil.postData(https_Url, jsonData_token);
			//
			String resultCode_token 	= (String)response_token.get("resultCode");
			// Https 통신이 정상이 아니면
			if(!"000000".equals(resultCode_token)) {
				//
				throw new ApiException("000004",commonConstant.M_000004);
				//
			}
			// tokenInfo 필요해서 파라미터 선언
			String svcToken = (String)response_token.get("tokenInfo");
			// 
			// *************************************************************************************************************
			// * 03. 룸 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			//
			JSONObject jsonData_roomToken = new JSONObject();
			jsonData_roomToken.put("roomCode"	, String.valueOf(fileDownloadReqDto.getRoomNo()));
			jsonData_roomToken.put("svcCode"	, svcCode);
			jsonData_roomToken.put("userId"		, String.valueOf(fileDownloadReqDto.getUserId()));
			jsonData_roomToken.put("userNm"		, String.valueOf(fileDownloadReqDto.getUserNm()));
			//
			https_Url = "";
			https_Url = apiUrl + rtUrl; // api 호출 url + roomTolken 호출 url
			//
			JSONObject response_roomToken 	= httpsUtil.postData(https_Url, jsonData_roomToken);
			//
			String resultCode_roomToken 	= (String)response_roomToken.get("resultCode");
			if(!"000000".equals(resultCode_roomToken)) {
				//
				throw new ApiException("002005",commonConstant.M_002005);
				//
			}
			// 생성된 roomToken 가져오기
			String roomToken 		= (String)response_roomToken.get("roomToken");
			//
			// *************************************************************************************************************
			// * 04. 파일 다운로드 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			downMap.clear();
			downMap.put("roomToken"	, String.valueOf(roomToken));
			downMap.put("roomCode"	, String.valueOf(fileDownloadReqDto.getRoomNo()));
			downMap.put("filePath"	, String.valueOf(fileDownloadReqDto.getFilePath()));
			downMap.put("fileSeq"	, String.valueOf(fileDownloadReqDto.getFileSeq()));
			downMap.put("userId"	, String.valueOf(fileDownloadReqDto.getUserId()));
			downMap.put("userNm"	, String.valueOf(fileDownloadReqDto.getUserNm()));
			//
			https_Url = "";
			https_Url = apiUrl+fileDownload; //api 호출 url + 파일 다운로드 url
			//
			JSONObject response_fileDownload = httpsUtil.multiStr(https_Url, downMap);
			//
			downMap.put("roomNo"	, String.valueOf(fileDownloadReqDto.getRoomNo()));
			//
			String 						fileName 			= "";
			//
			FileDownloadFileNmDto		getFileNm 			= fileMapper.getFileNm(downMap);
			// Https 통신에서 파일 이름을 가져올 것인지 / DB에서 파일 이름을 가져올 것인지 선택
			//fileName				= String.valueOf(response_fileDownload.get("fileName"));	// HttpsURLConnection으로 fileNm 읽은 것
			fileName				= getFileNm.getFileNm();									// DB에서 fileNm 가져온 것
			//
			String					downFileDir			= "/home/apaadm/tmp/fileDownload/";
			File					downFile			= new File(downFileDir, fileName);
			downFileIS				= new FileInputStream(downFile);
			//
			int 					maxBufferSize 		= 1024;
			int						bytesRead			= 0;
			byte[]					buff				= new byte[maxBufferSize];
			bao						= new ByteArrayOutputStream();
			//
			while((bytesRead = downFileIS.read(buff)) > 0) {
				bao.write(buff, 0, bytesRead);
			}
			//
			bytes 		= bao.toByteArray();
			bao.flush();
			//
			fileName	= fileName.replaceAll(" ", "_");
			//
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			httpHeaders.setContentLength(bytes.length);
	        httpHeaders.setContentDispositionFormData("attachment",new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
			//
			downFile.delete();
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[FileDownload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[FileDownload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
    		return new ResponseEntity<byte[]>((HttpStatus.BAD_REQUEST));
    	}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>((HttpStatus.BAD_REQUEST));
		}finally {
			if( bao != null) {
				try {
					bao.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if( downFileIS != null) {
				try {
					downFileIS.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		//
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

}
