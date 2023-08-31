package kr.co.softbridge.voplatform.sample.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.softbridge.voplatform.sample.dto.FileUploadReqDto;

@Service
public class SampleService {
	//------------------------------------------------------------------------------------------------------------------------------------------
	// 서비스토큰생성 Service
	public String auth(HashMap<String, Object> paramMap) {
		//
		URL 					urlParam 		= null;
		HttpsURLConnection 		httpsConn 		= null;
		OutputStreamWriter		osw 			= null;
		BufferedReader 			br 				= null;
		JSONObject 				response_token	= null;
		String 					result			= null;
		try {
			// Step 01. 서비스토큰 생성 플랫폼 API 호출
			// 데이터 세팅 (API 연동규격서 참고)
			// 현재 Parameter는 임의로 설정을 했음.
			JSONObject tokenJson = new JSONObject();
			tokenJson.put("svcCode"			, "d92kg9sf8dswc8sa810"); 		// 관리자포털에서 발급받은 svcCode
			tokenJson.put("svcDomain"		, "https://localhost:8080");	// 해당 서비스 도메인
			tokenJson.put("userId"			, "streamer1");					// 관리자 ID
			tokenJson.put("svcNm"			, "LGU+ 화상회의");				// 해당 서비스 명
			//----------------------------------------
			// Step 02. 서버간 통신 httpsURLConnection 활용
			/*
			 * API DEV서버 	[ https://api.dev.ulsp.uplus.co.kr ]
			 * API QA서버 	[ https://api.qa.ulsp.uplus.co.kr ]
			 * API 운영서버 	[ https://api.ulsp.uplus.co.kr ]
			 */
			// API QA서버 서비스토큰생성 url
			String url = "https://api.qa.ulsp.uplus.co.kr/sobro-qa-login/auth";
			// 서버간 Connection 생성
			urlParam = new URL(url);
			// API서버가 SSL을 사용중이므로 https통신으로 한다.
			httpsConn = (HttpsURLConnection)urlParam.openConnection();
			httpsConn.setConnectTimeout(10000); // 대략적으로 timeout을 10초 설정
			httpsConn.setReadTimeout(10000);
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			httpsConn.setRequestMethod("POST");	// POST방식 설정
			httpsConn.setRequestProperty("Content-Type", "application/json"); // json형태로 데이터를 주고받기
			//----------------------------------------
			osw		= new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
			// 보낼 데이터
			osw.write(tokenJson.toString());
			// 제출
			osw.flush();
			// 플랫폼 API에서 리턴값 받기
			br 		= new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "UTF-8"));
			response_token = new JSONObject(br.readLine());
			// 통신 검증
			int responseCode 	= httpsConn.getResponseCode();
			//
			if(responseCode != 200) {
				throw new Exception("Https 통신 데이터 수신 실패");
			}
			result = (String)response_token.get("tokenInfo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//------------------------------------------------------------------------------------------------------------------------------------------
	// 화상회의 방 생성 Service
	public String roomCreate(HashMap<String, Object> paramMap) {
		// 
		URL 					urlParam 		= null;
		HttpsURLConnection 		httpsConn 		= null;
		OutputStreamWriter		osw 			= null;
		BufferedReader 			br 				= null;
		JSONObject 				response_token	= null;
		String 					result			= null;
		try {
			//
			// 데이터 세팅 (API 연동규격서 참고)
			// 현재 Parameter는 임의로 설정을 했음.
			JSONObject createJson = new JSONObject();
			createJson.put("svcToken"	, ""); 		
			createJson.put("title"		, "");	
			createJson.put("roomPw"		, "");					
			createJson.put("maxPeople"	, "");
			createJson.put("roomType"	, "");
			createJson.put("viewYn"		, "");
			createJson.put("quality"	, "");
			createJson.put("userId"		, "");
			createJson.put("startDt"	, "");
			createJson.put("endDt"		, "");
			createJson.put("screenType"	, "");
			//----------------------------------------
			// Step 02. 서버간 통신 httpsURLConnection 활용
			/*
			 * API DEV서버 	[ https://api.dev.ulsp.uplus.co.kr ]
			 * API QA서버 	[ https://api.qa.ulsp.uplus.co.kr ]
			 * API 운영서버 	[ https://api.ulsp.uplus.co.kr ]
			 */
			// 플랫폼 API QA서버 화상회의 방 생성 url
			String url = "https://api.qa.ulsp.uplus.co.kr/sobro-qa-room/create";
			// 서버간 Connection 생성
			urlParam = new URL(url);
			// API서버가 SSL을 사용중이므로 https통신으로 한다.
			httpsConn = (HttpsURLConnection)urlParam.openConnection();
			httpsConn.setConnectTimeout(10000); // 대략적으로 timeout을 10초 설정
			httpsConn.setReadTimeout(10000);
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			httpsConn.setRequestMethod("POST");	// POST방식 설정
			httpsConn.setRequestProperty("Content-Type", "application/json"); // json형태로 데이터를 주고받기
			//----------------------------------------
			osw		= new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
			// 보낼 데이터
			osw.write(createJson.toString());
			// 제출
			osw.flush();
			// 플랫폼 API에서 리턴값 받기
			br 		= new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "UTF-8"));
			response_token = new JSONObject(br.readLine());
			// 통신 검증
			int responseCode 	= httpsConn.getResponseCode();
			//
			if(responseCode != 200) {
				throw new Exception("Https 통신 데이터 수신 실패");
			}
			result = (String)response_token.get("tokenInfo");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//------------------------------------------------------------------------------------------------------------------------------------------
	// 파일 업로드 Service
	public String fileUpload(@Valid FileUploadReqDto fileUploadReqDto) {
		//
		return null;
	}
	
}
