package kr.co.softbridge.voplatform.commons.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.softbridge.voplatform.commons.constants.commonConstant;
import kr.co.softbridge.voplatform.commons.dto.CommonCodeDto;
import kr.co.softbridge.voplatform.commons.dto.CommonCodeResponeDao;
import kr.co.softbridge.voplatform.commons.mapper.CommonCodeMapper;
import kr.co.softbridge.voplatform.commons.util.StringUtil;

@Service
public class CommonCodeService {
	@Autowired
	private CommonCodeMapper commonCodeMapper;

	public ResponseEntity<CommonCodeResponeDao> codeList(Map<String, Object> paramMap) {
    	Boolean result = false;
    	List<CommonCodeDto> codeList = null;
    	
    	try {
	    	if(StringUtil.isEmpty(paramMap.get("grpCode"))) {
	    		return ResponseEntity.status(HttpStatus.OK)
    					.body(CommonCodeResponeDao
    							.builder()
    							.resultCode("000001")
    							.resultMsg(commonConstant.M_000001)
    							.build());
	    	}
	    	
	    	codeList = getCodeList(paramMap);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return ResponseEntity.status(HttpStatus.OK)
				.body(CommonCodeResponeDao
						.builder()
						.resultCode("000000")
						.resultMsg(commonConstant.M_000000)
						.codeList(codeList)
						.build());
	}
	
	public List<CommonCodeDto> getCodeList(Map<String, Object> paramMap) {
		return commonCodeMapper.getCodeList(paramMap);
	}
}
