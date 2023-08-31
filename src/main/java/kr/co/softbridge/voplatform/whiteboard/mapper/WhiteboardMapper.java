package kr.co.softbridge.voplatform.whiteboard.mapper;

import java.util.HashMap;
import java.util.List;

import kr.co.softbridge.voplatform.commons.annotation.PrimaryMapper;
import kr.co.softbridge.voplatform.whiteboard.dto.WhiteboardImgDto;

@PrimaryMapper
public interface WhiteboardMapper {
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 저장 mapper
	* @method : insImg
	* @author : 이민재
	**************************************************************************************************************/
	int insImg(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 ID 조회
	* @method : getImgId
	* @author : 이민재
	**************************************************************************************************************/
	int getImgId(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 수정 mapper
	* @method : uptImg
	* @author : 이민재
	**************************************************************************************************************/
	int uptImg(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 삭제 mapper
	* @method : delImg
	* @author : 이민재
	**************************************************************************************************************/
	int delImg(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 화이트보드 이미지 정보 조회 mapper
	* @method : imgSelect
	* @author : 이민재
	**************************************************************************************************************/
	List<WhiteboardImgDto> imgSelect(HashMap<String, Object> paramMap);
		
	

}
