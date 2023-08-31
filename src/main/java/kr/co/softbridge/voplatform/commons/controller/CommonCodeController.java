package kr.co.softbridge.voplatform.commons.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.softbridge.voplatform.commons.dto.CommonCodeResponeDao;
import kr.co.softbridge.voplatform.commons.service.CommonCodeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/common")
@RestController
public class CommonCodeController {
	private final CommonCodeService commonCodeService;

	@ApiOperation(value = "공통코드목록", notes = "room 목록을 조회")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonCodeResponeDao> codeList(@RequestBody(required = true) Map<String, Object> paramMap) throws Exception {
    	// 서비스 호출
    	ResponseEntity<CommonCodeResponeDao> result = commonCodeService.codeList(paramMap);

    	return result;
	}
}
