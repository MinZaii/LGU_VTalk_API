package kr.co.softbridge.voplatform.commons.mapper;

import java.util.List;
import java.util.Map;

import kr.co.softbridge.voplatform.commons.annotation.PrimaryMapper;
import kr.co.softbridge.voplatform.commons.dto.CommonCodeDto;

@PrimaryMapper
public interface CommonCodeMapper {

	List<CommonCodeDto> getCodeList(Map<String, Object> paramMap);

}
