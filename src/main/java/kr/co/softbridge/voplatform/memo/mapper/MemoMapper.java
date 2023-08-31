package kr.co.softbridge.voplatform.memo.mapper;

import java.util.HashMap;
import java.util.List;

import kr.co.softbridge.voplatform.commons.annotation.PrimaryMapper;
import kr.co.softbridge.voplatform.memo.dto.MemoListDto;

@PrimaryMapper
public interface MemoMapper {
	//
	/***********************************
	* @brief  : 신규 memoId 생성 Mapper
	* @method : getMemoId
	* @author : 이민재
	***********************************/
	public int getMemoId(HashMap<String, Object> paramMap);
	//
	/***********************************
	* @brief  : memo insert Mapper
	* @method : insertMemo
	* @author : 이민재
	***********************************/
	public int insertMemo(HashMap<String, Object> paramMap);
	//
	/***********************************
	* @brief  : memo Select mapper
	* @method : memoSelect
	* @author : 이민재
	***********************************/
	List<MemoListDto> memoSelect(HashMap<String, Object> paramMap);
	//
	/***********************************
	* @brief  : memo Update mapper
	* @method : memoUpdate
	* @author : 이민재
	***********************************/
	public int memoUpdate(HashMap<String, Object> paramMap);
	//
	/***********************************
	* @brief  : memo Delete mapper
	* @method : memoDelete
	* @author : 이민재
	***********************************/
	public int memoDelete(HashMap<String, Object> paramMap);
	//
}
