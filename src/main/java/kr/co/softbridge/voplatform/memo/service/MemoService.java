package kr.co.softbridge.voplatform.memo.service;

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
import kr.co.softbridge.voplatform.memo.dto.MemoCreateDto;
import kr.co.softbridge.voplatform.memo.dto.MemoDeleteDto;
import kr.co.softbridge.voplatform.memo.dto.MemoListDto;
import kr.co.softbridge.voplatform.memo.dto.MemoSelectDto;
import kr.co.softbridge.voplatform.memo.dto.MemoUpdateDto;
import kr.co.softbridge.voplatform.memo.mapper.MemoMapper;
import kr.co.softbridge.voplatform.room.service.RoomService;

@Service
public class MemoService {
	//
	@Autowired
	private MemoMapper memoMapper;
	//
	private static final Logger logger 			= LogManager.getLogger(MemoService.class);
	//
	/*************************************************************************************************************
	* @brief  : memo 생성 service
	* @method : memoCreate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<MemoCreateDto> memoCreate(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		int memoId = 0;
		//
		try {
			//
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			
			if(StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("color")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("widthRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("heightRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("xCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("yCoordinateRatio")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("editingYn")))
					) {
					throw new ApiException("000001", commonConstant.M_000001);
			}
			// content (포스트잇 내용이 없으면) null값 넣기
			if(StringUtil.isEmpty(String.valueOf(paramMap.get("content")))) {
				paramMap.put("content", "");
			}
			//
			// *************************************************************************************************
			// * 02. 메모 저장
			// *************************************************************************************************
			int insMemoCnt = memoMapper.insertMemo(paramMap);
			//
			if (insMemoCnt < 0) {
				throw new ApiException("003001" ,commonConstant.M_003001);
			}
			// *************************************************************************************************
			// * 03. 생성된 메모 ID 조회
			// *************************************************************************************************
			memoId = memoMapper.getMemoId(paramMap); 
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[MemoCreate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[MemoCreate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoCreateDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoCreateDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(MemoCreateDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.memoId(memoId)
    					.userId(String.valueOf(paramMap.get("userId")))
    					.userNm(String.valueOf(paramMap.get("userNm")))
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : memo 조회 service
	* @method : memoSelect
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<MemoSelectDto> memoSelect(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		List<MemoListDto> memoList = null;
		//
		try {
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					||StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					||StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
				){
				throw new ApiException("000001", commonConstant.M_000001);
			}
			// *************************************************************************************************
			// * 02. 메모 조회
			// *************************************************************************************************
			memoList = memoMapper.memoSelect(paramMap);
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[MemoSelect] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[MemoSelect] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoSelectDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoSelectDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(MemoSelectDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.memoList(memoList)
    					.build());
	}
	/*************************************************************************************************************
	* @brief  : memo 수정 service
	* @method : memoUpdate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<MemoUpdateDto> memoUpdate(HttpServletRequest request, HashMap<String, Object> paramMap) {
		
		//
		try {
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(StringUtil.isEmpty(paramMap.get("roomNo"))
				|| StringUtil.isEmpty(paramMap.get("memoId"))
				|| StringUtil.isEmpty(paramMap.get("color"))
				|| StringUtil.isEmpty(paramMap.get("widthRatio"))
				|| StringUtil.isEmpty(paramMap.get("heightRatio"))
				|| StringUtil.isEmpty(paramMap.get("xCoordinateRatio"))
				|| StringUtil.isEmpty(paramMap.get("yCoordinateRatio"))
				|| StringUtil.isEmpty(paramMap.get("userId"))
				|| StringUtil.isEmpty(paramMap.get("userNm"))
				|| StringUtil.isEmpty(paramMap.get("editingYn"))
					) {
				throw new ApiException("000001", commonConstant.M_000001);
				}
			
			// 내용이 없으면 object에 null로 넣어 주기
			if(StringUtil.isEmpty(paramMap.get("content"))) {
				paramMap.put("content", "");
			}
			// *************************************************************************************************
			// * 02. 메모 수정
			// *************************************************************************************************
			int memoUpdateCnt = memoMapper.memoUpdate(paramMap);
			//
			if(memoUpdateCnt < 0) {
				throw new ApiException("003002" ,commonConstant.M_003002);
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[MemoUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[MemoUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoUpdateDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoUpdateDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(MemoUpdateDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.memoId(Integer.parseInt(String.valueOf(paramMap.get("memoId"))))
    					.userId(String.valueOf(paramMap.get("userId")))
    					.userNm(String.valueOf(paramMap.get("userNm")))
    					.build());
	}
	/*************************************************************************************************************
	* @brief  : memo 삭제 service
	* @method : memoDelete
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<MemoDeleteDto> memoDelete(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		try {
			// *************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************
			if(StringUtil.isEmpty(paramMap.get("roomNo"))
					|| StringUtil.isEmpty(paramMap.get("userId"))
					|| StringUtil.isEmpty(paramMap.get("userNm"))
					){
				throw new ApiException("000001", commonConstant.M_000001);
			}
			// *************************************************************************************************
			// * 02. 메모 삭제
			// *************************************************************************************************
			//
			int memoDeleteCnt = memoMapper.memoDelete(paramMap);
			//
			if(memoDeleteCnt < 0) {
				throw new ApiException("003003" ,commonConstant.M_003003);
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[memoDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[memoDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoDeleteDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(MemoDeleteDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(MemoDeleteDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.roomNo(String.valueOf(paramMap.get("roomNo")))
    					.userId(String.valueOf(paramMap.get("userId")))
    					.userNm(String.valueOf(paramMap.get("userNm")))
    					.build());
	}
	//
	
}
