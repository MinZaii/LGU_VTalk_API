package kr.co.softbridge.voplatform.sample.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.softbridge.voplatform.sample.dto.FileUploadReqDto;
import kr.co.softbridge.voplatform.sample.service.SampleService;
import kr.co.softbridge.voplatform.security.AES256Util;

@RestController
@RequestMapping("/sample")
public class SampleController {
	
	//
	@Autowired
    private SampleService sampleService;
	//
	@ApiOperation(value = "서비스 토큰 생성", notes = "JAVA에서 서비스토큰생성 플랫폼API 호출 하는 방법")
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object auth(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
    	// 서비스토큰 생성 서비스 호출, 서비스 명은 임의로 정함.
    	String result = sampleService.auth(paramMap);
    	//
    	return result;
    }
	//
	@ApiOperation(value = "화상회의 방 생성", notes = "JAVA에서 화상회의 방 생성 플랫폼API 호출 하는 방법")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object roomCreate(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws Exception {
    	// 화상회의 방 생성 서비스 호출, 서비스 명은 임의로 정함.
    	String result = sampleService.roomCreate(paramMap);
    	//
    	return result;
    }
	@ApiOperation(value = "파일업로드", notes = "JAVA를 통해 파일업로드 플랫폼API 호출 하는 방법")
    @PostMapping(value = "/fileUpload", produces = MediaType.APPLICATION_JSON_VALUE)
	public String fileUpload(HttpServletRequest request, @Valid FileUploadReqDto fileUploadReqDto) throws IOException{
		// front에서 controller로 보낼 파일은 다르게 Form형식으로 보내주어야 한다.
		String result = sampleService.fileUpload(fileUploadReqDto);
		//
		return result;
	}
	
    @PostMapping(value = "/enc", produces = MediaType.APPLICATION_JSON_VALUE)
	public String encDB(HttpServletRequest request, @RequestBody(required = true) HashMap<String, Object> paramMap) throws IOException, NoSuchAlgorithmException, GeneralSecurityException{
		//
		String driverClassName = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://rds-dev-ulsp-cluster.cluster-cy0bc7iatjqf.ap-northeast-2.rds.amazonaws.com:3306/dev_ulsp_was?characterEncoding=utf8";
		String username = "was";
		String password = "was!21";
		String key = "SDFSG#H435^34==DGE3)4G";
		//
		AES256Util ase256 = new AES256Util(key);
		//
		System.out.println("============================before Encrypt=================================");
		System.out.println("driverClassName = [ " + driverClassName + " ]");
		System.out.println("url = [ " + url + " ]");
		System.out.println("username = [ " + username + " ]");
		System.out.println("password = [ " + password + " ]");
		System.out.println("============================================================================");
		//
		System.out.println("============================after Encrypt=================================");
		System.out.println("driverClassName = [ " + ase256.encrypt(driverClassName) + " ]");
		System.out.println("url = [ " + ase256.encrypt(url) + " ]");
		System.out.println("username = [ " + ase256.encrypt(username) + " ]");
		System.out.println("password = [ " + ase256.encrypt(password) + " ]");
		System.out.println("============================================================================");
		//
		System.out.println("============================after Decrypt=================================");
		System.out.println("driverClassName = [ " + ase256.decrypt(ase256.encrypt(driverClassName)) + " ]");
		System.out.println("url = [ " + ase256.decrypt(ase256.encrypt(url)) + " ]");
		System.out.println("username = [ " + ase256.decrypt(ase256.encrypt(username)) + " ]");
		System.out.println("password = [ " + ase256.decrypt(ase256.encrypt(password)) + " ]");
		System.out.println("============================================================================");
		//
		return null;
	}
	
}
