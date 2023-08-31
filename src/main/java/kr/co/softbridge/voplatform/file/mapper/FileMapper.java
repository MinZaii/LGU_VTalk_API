package kr.co.softbridge.voplatform.file.mapper;

import java.util.HashMap;
import java.util.List;

import kr.co.softbridge.voplatform.commons.annotation.PrimaryMapper;
import kr.co.softbridge.voplatform.file.dto.FileDownloadFileNmDto;
import kr.co.softbridge.voplatform.file.dto.FileListDto;
import kr.co.softbridge.voplatform.file.dto.FileSeqDto;

@PrimaryMapper
public interface FileMapper {
	//
	/*************************************************************************************************************
	* @brief  : 파일삭제 TB_FILE DELETE mapper
	* @method : fileDelete
	* @author : 이민재
	**************************************************************************************************************/
	int fileDelete(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 파일업로드 TB_FILE INSERT mapper
	* @method : insertFile
	* @author : 이민재
	**************************************************************************************************************/
	int insertFile(HashMap<String, Object> fileMap);
	//
	/*************************************************************************************************************
	* @brief  : 파일 목록 조회
	* @method : fileListSel
	* @author : 이민재
	**************************************************************************************************************/
	FileListDto fileListSel(HashMap<String, Object> roomMap);
	//
	/*************************************************************************************************************
	* @brief  : 파일 목록 조회
	* @method : fileListSel
	* @author : 이민재
	**************************************************************************************************************/
	int fileSel(HashMap<String, Object> downMap);
	//
	/*************************************************************************************************************
	* @brief  : 파일 명 조회
	* @method : getFileNm
	* @author : 이민재
	**************************************************************************************************************/
	FileDownloadFileNmDto getFileNm(HashMap<String, Object> downMap);
	//
	/*************************************************************************************************************
	* @brief  : 파일 갯수 조회
	* @method : fileCntSel
	* @author : 이민재
	**************************************************************************************************************/
	int fileCntSel(HashMap<String, Object> paramMap);
	//
	/*************************************************************************************************************
	* @brief  : 페이징에 따른 파일 번호 리스트 조회
	* @method : fileSeqSel
	* @author : 이민재
	**************************************************************************************************************/
	List<FileSeqDto> fileSeqSel(HashMap<String, Object> fileMap);
}
