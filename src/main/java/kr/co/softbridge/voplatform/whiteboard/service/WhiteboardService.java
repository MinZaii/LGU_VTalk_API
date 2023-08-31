package kr.co.softbridge.voplatform.whiteboard.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.softbridge.voplatform.commons.constants.commonConstant;
import kr.co.softbridge.voplatform.commons.exception.ApiException;
import kr.co.softbridge.voplatform.commons.util.StringUtil;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgDeleteDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgSelectDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgUpdateDto;
import kr.co.softbridge.voplatform.whiteboard.dto.ImgUploadDto;
import kr.co.softbridge.voplatform.whiteboard.dto.WhiteboardImgDto;
import kr.co.softbridge.voplatform.whiteboard.mapper.WhiteboardMapper;

@Service
public class WhiteboardService {
	//
	private static final Logger logger 			= LogManager.getLogger(WhiteboardService.class);
	//
	@Autowired
	private WhiteboardMapper whiteboardMapper;
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 저장 Service
	* @method : wimgUpload
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<ImgUploadDto> wimgUpload(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		int 			imgId	 		= 0;
		String			userId			= "";
		String			userNm			= "";
		//
		try {
			//
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("imgUrl")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("widthRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("heightRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("xCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("yCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					) {
					throw new ApiException("000001", commonConstant.M_000001);
			}
			//
			// *************************************************************************************************
			// * 02. IMG 정보 저장
			// *************************************************************************************************
			int 	insImgCnt 	= 	whiteboardMapper.insImg(paramMap);
			//
			if(insImgCnt < 0) {
				//
				throw new ApiException("004001" , commonConstant.M_004001);
				//
			}else {
				//
				userId		=		String.valueOf(paramMap.get("userId"));
				userNm		=		String.valueOf(paramMap.get("userNm"));
				//
			}
			//
			// *************************************************************************************************
			// * 03. 생성된 이미지 ID 조회
			// *************************************************************************************************
			imgId 		= 		whiteboardMapper.getImgId(paramMap);
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[wimgUpload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[wimgUpload] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgUploadDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgUploadDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(ImgUploadDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.imgId(imgId)
    					.userId(userId)
    					.userNm(userNm)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 수정 Service
	* @method : wimgUpdate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<ImgUpdateDto> wimgUpdate(HttpServletRequest request, HashMap<String, Object> paramMap) {
		// 
		int				imgId			= 0;
		String			userId			= "";
		String			userNm			= "";
		//
		try {
			//
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("imgId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("widthRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("heightRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("xCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("yCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					) {
					throw new ApiException("000001", commonConstant.M_000001);
			}
			//
			// *************************************************************************************************
			// * 02. IMG 정보 저장
			// *************************************************************************************************
			int uptImgCnt = whiteboardMapper.uptImg(paramMap);
			//
			if(uptImgCnt < 0) {
				//
				throw new ApiException("004002", commonConstant.M_004002);
				//
			}else {
				userId		=		String.valueOf(paramMap.get("userId"));
				userNm		=		String.valueOf(paramMap.get("userNm"));
				imgId		=		Integer.parseInt(String.valueOf(paramMap.get("imgId")));
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[wimgUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[wimgUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgUpdateDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgUpdateDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(ImgUpdateDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.imgId(imgId)
    					.userId(userId)
    					.userNm(userNm)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 삭제 Service
	* @method : wimgUpdate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<ImgDeleteDto> wimgDelete(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		String			roomNo			= "";
		String			userId			= "";
		String			userNm			= "";
		//
		try {
			//
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(		
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					) {
					throw new ApiException("000001"		,	 commonConstant.M_000001);
			}
			//
			// *************************************************************************************************
			// * 02. 화이트보드 이미지 정보 삭제
			// *************************************************************************************************
			//
			int delImgCnt = whiteboardMapper.delImg(paramMap);
			//
			if(delImgCnt < 0) {
				//
				throw new ApiException("004003"		,	commonConstant.M_004003);
				//
			}else {
				roomNo		 =		 String.valueOf(paramMap.get("roomNo"));
				userId		 =		 String.valueOf(paramMap.get("userId"));
				userNm		 = 		 String.valueOf(paramMap.get("userNm"));
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[wimgUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[wimgUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgDeleteDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgDeleteDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(ImgDeleteDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.roomNo(roomNo)
    					.userId(userId)
    					.userNm(userNm)
    					.build());
	}
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 조회 Service
	* @method : wimgSelect
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<ImgSelectDto> wimgSelect(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		List<WhiteboardImgDto> 			imgList 			= null;
		//
		try {
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(		
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					) {
					throw new ApiException("000001"		,	 commonConstant.M_000001);
			}
			//
			// *************************************************************************************************
			// * 02. 이미지 정보 조회
			// *************************************************************************************************
			imgList = whiteboardMapper.imgSelect(paramMap);
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[wimgSelect] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[wimgSelect] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgSelectDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(ImgSelectDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(ImgSelectDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.imgList(imgList)
    					.build());
	}
}
