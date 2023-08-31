package kr.co.softbridge.voplatform.room.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.softbridge.voplatform.commons.constants.commonConstant;
import kr.co.softbridge.voplatform.commons.exception.ApiException;
import kr.co.softbridge.voplatform.commons.util.HttpsUtil;
import kr.co.softbridge.voplatform.commons.util.StringUtil;
import kr.co.softbridge.voplatform.commons.util.Util;
import kr.co.softbridge.voplatform.file.mapper.FileMapper;
import kr.co.softbridge.voplatform.memo.mapper.MemoMapper;
import kr.co.softbridge.voplatform.room.dto.GetRoomNoDto;
import kr.co.softbridge.voplatform.room.dto.RoomDeleteDto;
import kr.co.softbridge.voplatform.room.dto.RoomDetlDto;
import kr.co.softbridge.voplatform.room.dto.RoomDto;
import kr.co.softbridge.voplatform.room.dto.RoomHistDto;
import kr.co.softbridge.voplatform.room.dto.RoomListDto;
import kr.co.softbridge.voplatform.room.dto.RoomResponeDto;
import kr.co.softbridge.voplatform.room.dto.RoomUpdateDto;
import kr.co.softbridge.voplatform.room.dto.RoomViewDto;
import kr.co.softbridge.voplatform.room.dto.UserListDto;
import kr.co.softbridge.voplatform.room.mapper.RoomMapper;
import kr.co.softbridge.voplatform.security.AES256Util;
import kr.co.softbridge.voplatform.whiteboard.mapper.WhiteboardMapper;

@Service
public class RoomService {
	//
	private static final Logger logger 			= LogManager.getLogger(RoomService.class);
	//
	@Value("${hcd.svcCode}")
	private String svcCode;
	//
	@Value("${hcd.svcUrl}")
	private String svcUrl;
	//
	@Value("${hcd.apiUrl}")
	private String apiUrl;
	//
	@Value("${hcd.tokenUrl}")
	private String tokenUrl;
	//
	@Value("${hcd.roomCreate}")
	private String rcUrl;
	//
	@Value("${hcd.roomUpdate}")
	private String ruUrl;
	//
	@Value("${hcd.roomDelete}")
	private String rdUrl;
	//
	@Autowired
	private RoomMapper roomMapper;	
	
	@Autowired
	private MemoMapper memoMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private WhiteboardMapper whiteboardMapper;
	
	private HttpsUtil httpsUtil = new HttpsUtil();
	private Util util = new Util();
	//
	/*************************************************************************************************************
	* @brief  : 회의 정보(참여자) 등록
	* @method : insJoinPeople
	* @author : 이민재
	**************************************************************************************************************/
	public int insJoinPeople(HashMap<String, Object> roomInsMap) {
		return roomMapper.insJoinPeople(roomInsMap);
	}
	//
	/*************************************************************************************************************
	* @brief  : 회의 이력 정보 등록
	* @method : insRoomHist
	* @author : 이민재
	**************************************************************************************************************/
	public int insRoomHist(HashMap<String, Object> roomInsMap) {
		return roomMapper.insRoomHist(roomInsMap);
	}
	//
	/*************************************************************************************************************
	* @brief  : room 목록 조회 service
	* @method : RoomList
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<RoomListDto> RoomList(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception{
		//
		List<GetRoomNoDto>			RoomNoget 		= null;
		List<RoomDto>				roomArrayList 	= new ArrayList<RoomDto>();
		HashMap<String, Object> 	roomMap			= 	new HashMap<String, Object>();
		//
		int totalCount = 0;
		//
		try {
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userType")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("page")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("pageSize")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("startDt")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("endDt")))
			){
				throw new ApiException("000001" , commonConstant.M_000001);
			}
			// userId 문자열 길이 체크
			String userIdLen = String.valueOf(paramMap.get("userId"));
			//
			if(userIdLen.length() > 32) {
				//
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// 날짜 패턴 체크
			boolean startDtCh 	= util.checkDateStr(String.valueOf(paramMap.get("startDt")));
			boolean endDtCh 	= util.checkDateStr(String.valueOf(paramMap.get("endDt")));
			//
			if(startDtCh != true || endDtCh != true) {
				//
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// userType 01, 02 체크
			if(!"01".equals(paramMap.get("userType")) && !"02".equals(paramMap.get("userType"))){
					throw new ApiException("000002", commonConstant.M_000002);		
			}
			
			// 
			// *************************************************************************************************************
			// * 02. 회의 목록 개수 조회
			// *************************************************************************************************************
			totalCount = roomMapper.getRoomTotalCount(paramMap);
			//
//			if(totalCount == 0) {
//				throw new ApiException("001001" , commonConstant.M_001001);
//			}
			//
			// *************************************************************************************************************
			// * 03. 페이징 설정
			// *************************************************************************************************************
			int pageNum 	= Integer.parseInt(StringUtil.null2void(paramMap.get("page"),"1"));
			int pageSize	= Integer.parseInt(StringUtil.null2void(paramMap.get("pageSize"),"10"));
			if(pageNum ==1) {
				pageNum = pageNum -1 ;
			}else {
				pageNum = (pageNum-1) * pageSize;
			}
			paramMap.put("pageNum"	, pageNum);
			paramMap.put("pageSize"	, pageSize);
			// *************************************************************************************************************
			// * 04. 회의 정보 조회
			// *************************************************************************************************************
			RoomNoget = roomMapper.RoomNoSel(paramMap);
			//
			int roomNoCnt = RoomNoget.size();
			for(int i=0; i<roomNoCnt; i++) {
				//
				String roomNo = RoomNoget.get(i).getRoomNo().toString();
				//
				roomMap.put("roomNo"		, roomNo);
				roomMap.put("userId"		, paramMap.get("userId"));
				roomMap.put("startDt"		, paramMap.get("startDt"));
				roomMap.put("endDt"			, paramMap.get("endDt"));
				//
				List<UserListDto>			userList		= roomMapper.getUserList(roomMap);
				RoomDto 					roomList 		= roomMapper.getRoomList(roomMap);
				roomList.setUserList(userList);
				roomArrayList.add(roomList);
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[RoomList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[RoomList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomListDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomListDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(RoomListDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.roomCnt(totalCount)
    					.roomList(roomArrayList)
    					.build());
		//
	}
	//
	/*************************************************************************************************************
	* @brief  : room 상세 조회 service
	* @method : roomView
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<RoomViewDto> roomView(HttpServletRequest request, HashMap<String, Object> paramMap) {
		// 
		String 					roomNo 			= null;
		String 					roomTitle 		= null;
		String 					roomObjective 	= null;
		String 					roomStartDt 	= null;
		String 					roomEndDt 		= null;
		String					viewYn			= null;
		String 					quality			= null;
		int						maxPeople;
		String					roomType		= null;
		String					screenType		= null;
		String 					regDt			= null;
		String					udtDt			= null;
		String 					managerId		= null;
		List<UserListDto> 		userList 		= null;
		
		try {
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					) {
				throw new ApiException("000001", commonConstant.M_000001);
			}
			// *************************************************************************************************************
			// * 02. 룸 상세 정보 조회
			// *************************************************************************************************************
			RoomDetlDto	roomViewDetl = roomMapper.RoomViewDetl(paramMap);
			// NULL 체크
			if(roomViewDetl == null) {
				//
				throw new ApiException("001001" , commonConstant.M_001001);
				//
			}else {
				//
				roomNo 			=	roomViewDetl.getRoomNo();
				roomTitle 		=	roomViewDetl.getTitle();
				roomObjective 	=	roomViewDetl.getObjective();
				roomStartDt 	=	roomViewDetl.getStartDt();
				roomEndDt 		=	roomViewDetl.getEndDt();
				viewYn 			=	roomViewDetl.getViewYn();
				quality 		=	roomViewDetl.getQuality();
				maxPeople 		=	roomViewDetl.getMaxPeople();
				roomType 		=	roomViewDetl.getRoomType();
				screenType 		=	roomViewDetl.getScreenType();
				regDt 			=	roomViewDetl.getRegDt();
				udtDt 			=	roomViewDetl.getUdtDt();
				managerId 		=	roomViewDetl.getManagerId();
				//
			}
			//
			// *************************************************************************************************************
			// * 03. 회의 참여자 정보 조회
			// *************************************************************************************************************
			userList = roomMapper.RoomJoinUserList(paramMap);
			//
			if(userList.size()<0) {
				//
				throw new ApiException("001001", commonConstant.M_001001);
				//
			}
			//
		}catch (ApiException e) {
    		if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[RoomList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[RoomList] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomViewDto
	    					.builder()
	    					.resultCode(e.getErrorCode())
	    					.resultMsg(e.getMessage())
	    					.build());
			//
    	}
		catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomViewDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
    			.body(RoomViewDto
    					.builder()
    					.resultCode("000000")
    					.resultMsg(commonConstant.M_000000)
    					.roomNo(String.valueOf(roomNo))
    					.title(String.valueOf(roomTitle))
    					.objective(String.valueOf(roomObjective))
    					.startDt(String.valueOf(roomStartDt))
    					.endDt(String.valueOf(roomEndDt))
    					.viewYn(String.valueOf(viewYn))
    					.quality(String.valueOf(quality))
    					.maxPeople(maxPeople)
    					.roomType(String.valueOf(roomType))
    					.screenType(String.valueOf(screenType))
    					.regDt(String.valueOf(regDt))
    					.udtDt(String.valueOf(udtDt))
    					.managerId(String.valueOf(managerId))
    					.userList(userList)
    					.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : room 생성 service
	* @method : roomCreate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<RoomResponeDto> roomCreate(HttpServletRequest request, HashMap<String, Object> paramMap) {
			//
			String 					roomNo 			= 	null;
			String					https_Url		=	null;
			HashMap<String, Object> roomInsMap		= 	new HashMap<String, Object>();
			//
			try {
				// *************************************************************************************************************
				// * 01. 넘어온 Parameter 검증
				// *************************************************************************************************************
				if(
						StringUtil.isEmpty(String.valueOf(paramMap.get("title")))
						|| StringUtil.isEmpty(String.valueOf(paramMap.get("objective")))
						|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
						|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
						|| StringUtil.isEmpty(String.valueOf(paramMap.get("startDt")))
						|| StringUtil.isEmpty(String.valueOf(paramMap.get("endDt")))
						|| StringUtil.isEmpty((List) paramMap.get("userList"))
						) {
							throw new ApiException("000001", commonConstant.M_000001);
				}
				// title 문자열 길이 체크
				String titleLen = String.valueOf(paramMap.get("title"));
				//
				if(titleLen.length() > 200) {
					throw new ApiException("000002", commonConstant.M_000002);
				}
				// objective 문자열 길이 체크
				String objectiveLen = String.valueOf(paramMap.get("objective"));
				//
				if(objectiveLen.length() > 200) {
					throw new ApiException("000002", commonConstant.M_000002);
				}
				// userNm 문자열 길이 체크
				String userNmLen = String.valueOf(paramMap.get("userNm"));
				//
				if(userNmLen.length() > 64) {
					throw new ApiException("000002", commonConstant.M_000002);
				}
				// userId 문자열 길이 체크
				String userIdLen = String.valueOf(paramMap.get("userId"));
				//
				if(userIdLen.length() > 32) {
					throw new ApiException("000002", commonConstant.M_000002);
				}
				// 날짜 패턴 체크
				boolean startDtCh 	= util.checkDate(String.valueOf(paramMap.get("startDt")));
				boolean endDtCh 	= util.checkDate(String.valueOf(paramMap.get("endDt")));
				boolean startDtCh2 	= util.checkDateStr(String.valueOf(paramMap.get("startDt")));
				boolean endDtCh2 	= util.checkDateStr(String.valueOf(paramMap.get("endDt")));
				//
				if((startDtCh != true && startDtCh2 != true) || (endDtCh != true && endDtCh2 != true)) {
					//
					throw new ApiException("000002", commonConstant.M_000002);
				}
				// *************************************************************************************************************
				// * 02. 서비스 토큰 요청
				// * HttpsConnection 활용
				// *************************************************************************************************************
				JSONObject jsonData_token = new JSONObject();
				//
				jsonData_token.put("svcCode"	, svcCode);
				jsonData_token.put("svcDomain"	, svcUrl);
				jsonData_token.put("userId"		, (String)paramMap.get("userId"));
				jsonData_token.put("svcNm"		, commonConstant.SERVICE_NAME);
				https_Url = apiUrl	+ tokenUrl;
				// JSON형식으로 HTTPS 통신
				JSONObject response_token 	= httpsUtil.postData(https_Url, jsonData_token);
				//
				String resultCode_token 	= (String)response_token.get("resultCode");
				// Https 통신이 정상이 아니면
				if(!"000000".equals(resultCode_token)) {
					//
					throw new ApiException("000004" , commonConstant.M_000004);
					//
				}
				//
				// *************************************************************************************************************
				// * 03. 회의 room 등록 요청
				// * HttpsConnection 활용
				// *************************************************************************************************************
				// room 생성 시 tokenInfo 필요해서 파라미터 선언
				//
				String svcToken = (String)response_token.get("tokenInfo");
				//
				// HTTPS를 통신을 위한 roomCreate JSON 데이터 세팅
				JSONObject jsonData_RC = new JSONObject();
				jsonData_RC.put("svcToken"		,	svcToken);								// 토큰 정보
				jsonData_RC.put("title"			, 	(String)paramMap.get("title"));			// 화상 회의 제목
				jsonData_RC.put("userId"		, 	(String)paramMap.get("userId"));		// 유저 ID
				jsonData_RC.put("startDt"		, 	(String)paramMap.get("startDt"));		// 시작 일자 및 시간
				jsonData_RC.put("endDt"			, 	(String)paramMap.get("endDt"));			// 종료 일자 및 시간
				// 방송상태 값이 없으면 초기값 세팅 //초기값은 방송 중
				if(StringUtil.isEmpty((String) paramMap.get("roomStatus"))) {
					jsonData_RC.put("roomStatus"	, commonConstant.ROOM_STATUS_RUNNING);
				}
				//
				// 회의타입 값이 없으면 default값 세팅
				if(StringUtil.isEmpty((String) paramMap.get("roomType"))) {
					jsonData_RC.put("roomType"		, commonConstant.ROOM_TYPE_DEFAULT);
				}
				// 화면공유유형 값이 없으면 세팅
				if(StringUtil.isEmpty((String)paramMap.get("screenType"))) {
					jsonData_RC.put("screenType"	, "10");
				}
				//방송 품질 값이 없으면 default값 세팅
				if(StringUtil.isEmpty((String)paramMap.get("quality"))){
					jsonData_RC.put("quality"		,commonConstant.QUALITY_DEFAULT);		// 방송 품질 / 01 : 2Mbps / 02 : 4Mbps / 03 : 6Mbps / 04 : 8Mbps
				}
				//
				List<Map<String, Object>> listMap = (List) paramMap.get("userList");
				int listMapCnt 	= listMap.size();
				//
				int maxPeople	= listMapCnt + 10;
				jsonData_RC.put("maxPeople"		,	String.valueOf(maxPeople));		// userList에 + 10명
				
				//
				// 비밀번호 6자리 난수 발생
				int randomPw = (int)(Math.random() * (999999 - 10000 +1)) + 100000;
				jsonData_RC.put("roomPw"		,	String.valueOf(randomPw));	// 방 비밀번호
				jsonData_RC.put("viewYn"		,	"Y");		// 노출여부
				//
				// HTTPS 통신위한 url 세팅
				https_Url = "";
				https_Url = apiUrl	+ rcUrl;
				// Https 통신
				JSONObject response_RC = httpsUtil.postData(https_Url, jsonData_RC);
				//
				String resultCode_RC 	= (String)response_RC.get("resultCode");
				// Https 통신이 정상이 아니면
				if("000000".equals(resultCode_RC)) {
					//
					try {
						// *************************************************************************************************************
						// * 04. 회의 및 참여자 정보 DB 등록
						// *************************************************************************************************************
						// 파라미터 세팅
						roomNo = (String)response_RC.get("roomcode");
						roomInsMap.put("roomNo"			, roomNo);
						roomInsMap.put("title"			, (String)paramMap.get("title"));
						roomInsMap.put("roomTopic"		, (String)paramMap.get("objective"));
						
						// joinCode	= 방 비밀번호 암호화
						AES256Util ase256 = new AES256Util(svcCode);
						String joinCode = String.valueOf(randomPw);
						String passwordEnc = ase256.encrypt(joinCode);
						roomInsMap.put("joinCode", 		passwordEnc);
						roomInsMap.put("managerId"		, (String)paramMap.get("userId"));
						roomInsMap.put("startDt"		, (String)paramMap.get("startDt"));
						roomInsMap.put("endDt"			, (String)paramMap.get("endDt"));
						roomInsMap.put("regId"			, (String)paramMap.get("userId"));
						roomInsMap.put("regDt"			, (String)paramMap.get("nowTime"));
						roomInsMap.put("userNm"			, (String)paramMap.get("userNm"));
						roomInsMap.put("roomStatus"		, (String)jsonData_RC.get("roomStatus"));
						roomInsMap.put("maxPeople"		, (String)jsonData_RC.get("maxPeople"));
						roomInsMap.put("screenType"		, (String)jsonData_RC.get("screenType"));
						roomInsMap.put("quality"		, (String)jsonData_RC.get("quality"));
						roomInsMap.put("roomType"		, (String)jsonData_RC.get("roomType"));
						roomInsMap.put("viewYn"			, "Y");	
						// 회의 DB 등록 (TB_ROOM  TABLE)
						int insertRoomCnt = roomMapper.insertRoom(roomInsMap);
						//
						if(insertRoomCnt < 0) {
							//
							throw new ApiException("001002" , commonConstant.M_001002);
							//
						}
						// 회의 참여자 DB 등록 (TB_ROOM_USER  TABLE)
						roomInsMap.put("userId"			, (String)paramMap.get("userId"));
						roomInsMap.put("userNm"			, (String)paramMap.get("userNm"));
						roomInsMap.put("userGb"			, commonConstant.LEVEL_MANAGER);
						int insertJoinPeopleCnt = insJoinPeople(roomInsMap);
						if(insertJoinPeopleCnt < 0) {
							//
							throw new ApiException("001002" , commonConstant.M_001002);
							//
						}
						
						// 회의 참가자가 여러명 일 수 있으니 for문으로 진행
						for(int i = 0; i < listMapCnt; i++) {
							//
							String userId = listMap.get(i).get("userId").toString();
							String userNm = listMap.get(i).get("userNm").toString();
							roomInsMap.put("userId"			, userId);
							roomInsMap.put("userNm"			, userNm);
							roomInsMap.put("userGb"		, commonConstant.LEVEL_USER);
							//
							insertJoinPeopleCnt = insJoinPeople(roomInsMap);
							//
							if(insertJoinPeopleCnt < 0) {
								//
								throw new ApiException("001002" , commonConstant.M_001002);
								//
							}
							//
						}
						// 회의 이력 DB 등록 (TB_ROOM_HISTORY  TABLE)
						roomInsMap.put("action", commonConstant.SAVE_CRATE);
						int insRoomHisCnt = insRoomHist(roomInsMap);
						//
						if(insRoomHisCnt < 0) {
							//
							throw new ApiException("001002" , commonConstant.M_001002);
							//
						}
					}catch(Exception e) {
						//
						throw new ApiException("000003" , commonConstant.M_000003);
						//
					}
					//
				}else {
					//
					throw new ApiException("001002" , commonConstant.M_001002);
					//
				}
				//
			}catch(ApiException e) {
				if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
					logger.info("[createRoom] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
				}else {
					logger.info("[createRoom] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
				}
	    		return ResponseEntity.status(HttpStatus.OK)
						.body(RoomResponeDto
								.builder()
								.resultCode(e.getErrorCode())
	                            .resultMsg(e.getMessage())
								.build());
			}catch(Exception e) {
				logger.info("000003", commonConstant.M_000003, e.getMessage());
	    		return ResponseEntity.status(HttpStatus.OK)
		    			.body(RoomResponeDto
		    					.builder()
		    					.resultCode("000003")
		    					.resultMsg(commonConstant.M_000003)
		    					.build());
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(RoomResponeDto
							.builder()
							.resultCode("000000")
							.resultMsg(commonConstant.M_000000)
							.roomNo(String.valueOf(roomNo))
							.build());
							
		}
		//
	//
	/*************************************************************************************************************
	* @brief  : room 수정 service
	* @method : roomUpdate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<RoomUpdateDto> roomUpdate(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		String					https_Url		=	null;
		HashMap<String, Object> roomUptMap		= 	new HashMap<String, Object>();
		//
		try {
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userNm")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("startDt")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("endDt")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("title")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("objective")))
					|| StringUtil.isEmpty((List) paramMap.get("userList"))
					) {
						throw new ApiException("000001", commonConstant.M_000001);
				}
			else {
				//
				// 해당 화상회의 방의 유무 판단
				int roomSelect = roomMapper.roomSel(paramMap);
				//
				if(roomSelect <= 0) {
					//
					throw new ApiException("001001" , commonConstant.M_001001);
					//
				}
			}
			// title 문자열 길이 체크
			String titleLen = String.valueOf(paramMap.get("userId"));
			//
			if(titleLen.length() > 200) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// objective 문자열 길이 체크
			String objectiveLen = String.valueOf(paramMap.get("userId"));
			//
			if(objectiveLen.length() > 200) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// userNm 문자열 길이 체크
			String userNmLen = String.valueOf(paramMap.get("userNm"));
			//
			if(userNmLen.length() > 64) {
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// userId 문자열 길이 체크
			String userIdLen = String.valueOf(paramMap.get("userId"));
			//
			if(userIdLen.length() > 32) {
				//
				throw new ApiException("000002", commonConstant.M_000002);
			}
			// 날짜 패턴 체크
			boolean startDtCh 	= util.checkDate(String.valueOf(paramMap.get("startDt")));
			boolean endDtCh 	= util.checkDate(String.valueOf(paramMap.get("endDt")));
			boolean startDtCh2 	= util.checkDateStr(String.valueOf(paramMap.get("startDt")));
			boolean endDtCh2 	= util.checkDateStr(String.valueOf(paramMap.get("endDt")));
			//
			if((startDtCh != true && startDtCh2 != true) || (endDtCh != true && endDtCh2 != true)) {
				//
				throw new ApiException("000002", commonConstant.M_000002);
			}
			//
			// *************************************************************************************************************
			// * 02. 서비스 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			// HTTPS 통신위한 데이터 세팅
			JSONObject jsonData_token = new JSONObject();
			jsonData_token.put("svcCode"			, svcCode);
			jsonData_token.put("svcDomain"			, svcUrl);
			jsonData_token.put("userId"				, (String)paramMap.get("userId"));
			jsonData_token.put("svcNm"				, commonConstant.SERVICE_NAME);
			//
			// HTTPS 통신위한 url 세팅
			https_Url = apiUrl	+ tokenUrl;		
			//
			JSONObject response_token = httpsUtil.postData(https_Url, jsonData_token);
			//
			String resultCode_token 	= (String)response_token.get("resultCode");
			// Https 통신이 정상이 아니면
			if(!"000000".equals(resultCode_token)) {
				//
				throw new ApiException("000004" , commonConstant.M_000004);
				//
			}
			// *************************************************************************************************************
			// * 03. 회의 room 수정 요청 을 위한 방 비밀번호 조회
			// *************************************************************************************************************
			String roomPwSel = roomMapper.RoomPwSel(paramMap);
			//
			// *************************************************************************************************************
			// * 04. 회의 room 수정 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			String svcToken	= (String)response_token.get("tokenInfo");
			//
			// HTTPS를 통신을 위한 roomUpdate JSON 데이터 세팅
			
			JSONObject jsonData_RU = new JSONObject();
			jsonData_RU.put("svcToken"		,		svcToken);								// 토큰 정보
			jsonData_RU.put("roomCode"		, 		(String)paramMap.get("roomNo"));		// 방 번호
			jsonData_RU.put("oldRoomPw"		, 		roomPwSel);								// 방 비밀번호 (예전 값)
			jsonData_RU.put("userId" 		, 		(String)paramMap.get("userId"));
			jsonData_RU.put("startDt"		, 		(String)paramMap.get("startDt"));
			jsonData_RU.put("endDt"			, 		(String)paramMap.get("endDt"));
			jsonData_RU.put("title"			, 		(String)paramMap.get("title"));
			if(StringUtil.isEmpty((String) paramMap.get("roomStatus"))) {
				jsonData_RU.put("roomStatus"	, commonConstant.ROOM_STATUS_RUNNING);
			}
			//
			// 회의타입 값이 없으면 default값 세팅
			if(StringUtil.isEmpty((String) paramMap.get("roomType"))) {
				jsonData_RU.put("roomType"		, commonConstant.ROOM_TYPE_DEFAULT);
			}
			// 화면공유유형 값이 없으면 세팅
			if(StringUtil.isEmpty((String)paramMap.get("screenType"))) {
				jsonData_RU.put("screenType"	, "10");
			}
			//방송 품질 값이 없으면 default값 세팅
			if(StringUtil.isEmpty((String)paramMap.get("quality"))){
				jsonData_RU.put("quality"		,commonConstant.QUALITY_DEFAULT);		// 방송 품질 / 01 : 2Mbps / 02 : 4Mbps / 03 : 6Mbps / 04 : 8Mbps
			}
			
			List<Map<String, Object>> listMap = (List) paramMap.get("userList");
			int listMapCnt = listMap.size();
			//
			int maxPeople	= listMapCnt + 10;
			jsonData_RU.put("maxPeople"		,	String.valueOf(maxPeople));		// userList에 + 10명
			//
			// HTTPS 통신위한 url 세팅
			https_Url = "";
			https_Url = apiUrl	+ ruUrl;
			//
			JSONObject response_RU = httpsUtil.postData(https_Url, jsonData_RU);
			//
			String resultCode_RU 	= (String)response_RU.get("resultCode");
			// 결과 코드가 정상이라면
			if("000000".equals(resultCode_RU)) {
				//
				// *************************************************************************************************************
				// * 05. Map Parameter 세팅
				// *************************************************************************************************************
				//
				roomUptMap.put("roomNo"			,		(String)paramMap.get("roomNo"));
				roomUptMap.put("startDt"		,		(String)paramMap.get("startDt"));
				roomUptMap.put("endDt"			,		(String)paramMap.get("endDt"));
				roomUptMap.put("regId"			,		(String)paramMap.get("userId"));
				roomUptMap.put("udtId"			,		(String)paramMap.get("userId"));
				roomUptMap.put("udtDt"			,		(String)paramMap.get("nowTime"));
				roomUptMap.put("title"			,		(String)paramMap.get("title"));
				roomUptMap.put("objective"		,		(String)paramMap.get("objective"));
				roomUptMap.put("maxPeople"		, 		jsonData_RU.get("maxPeople"));
				roomUptMap.put("roomType"		,		jsonData_RU.get("roomType"));
				roomUptMap.put("viewYn"			, 		"Y");
				roomUptMap.put("quality"		,		jsonData_RU.get("quality"));
				roomUptMap.put("screenType"		,		jsonData_RU.get("screenType"));
				//
				// *************************************************************************************************************
				// * 06. TB_ROOM DB 수정
				// *************************************************************************************************************
				int tb_room_upt = roomMapper.tbRoomUpt(roomUptMap);
				//
				if(tb_room_upt < 0) {
					throw new ApiException("001003" , commonConstant.M_001003);
				}
				// *************************************************************************************************************
				// * 07. TB_ROOM_USER DB 수정
				// *************************************************************************************************************
				// 회의 기존 참가자들 삭제
				roomMapper.tbRoomUserDel(roomUptMap);
				//
				// userList로 참가자들 insert
				roomUptMap.put("userId"			, (String)paramMap.get("userId"));
				roomUptMap.put("userNm"			, (String)paramMap.get("userNm"));
				roomUptMap.put("userGb"			, commonConstant.LEVEL_MANAGER);
				roomUptMap.put("regDt"			, (String)paramMap.get("nowTime"));
				//
				int insertJoinPeopleCnt = insJoinPeople(roomUptMap);
				//
				if(insertJoinPeopleCnt < 0) {
					throw new ApiException("001003" , commonConstant.M_001003);
				}
				//
				
				//
				for(int i=0; i<listMapCnt; i++) {
					String userId = listMap.get(i).get("userId").toString();
					String userNm = listMap.get(i).get("userNm").toString();
					roomUptMap.put("userGb"			,		commonConstant.LEVEL_USER);
					roomUptMap.put("userId"			,		userId);
					roomUptMap.put("userNm"			,		userNm);
					//
					insertJoinPeopleCnt = insJoinPeople(roomUptMap);
					//
					if(insertJoinPeopleCnt < 0) {
						throw new ApiException("001003" , commonConstant.M_001003);
					}
				}
				// *************************************************************************************************************
				// * 08. TB_ROOM_HISTORY DB 수정
				// *************************************************************************************************************
				//
				// joinCode	= 방 비밀번호 암호화
				String joinCode = roomPwSel;
				//
				roomUptMap.put("joinCode"			, 		joinCode);
				roomUptMap.put("managerId"			, 		(String)paramMap.get("userId"));
				//
				roomUptMap.put("action"				,		 commonConstant.SAVE_UPDATE);
				int uptRoomHistCnt = insRoomHist(roomUptMap);
				//
				if(uptRoomHistCnt < 0) {
					throw new ApiException("001003" , commonConstant.M_001003);
				}
				//
			}else if("003001".equals(resultCode_RU)){
				// 결과 값에 따라
				// 수정권한이 없음
				throw new ApiException("001005" , commonConstant.M_001005);
				//
			}else {
				//회의 정보 업데이트에 실패
				throw new ApiException("001003" , commonConstant.M_001003);
				//
			}
			//
		}catch(ApiException e) {
			if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[roomUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
				System.out.println("[roomUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[roomUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
				System.out.println("[roomUpdate] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}
    		return ResponseEntity.status(HttpStatus.OK)
					.body(RoomUpdateDto
							.builder()
							.resultCode(e.getErrorCode())
                            .resultMsg(e.getMessage())
							.build());
		}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
    		return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomUpdateDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
				.body(RoomUpdateDto
						.builder()
						.resultCode("000000")
						.resultMsg(commonConstant.M_000000)
						.roomNo(String.valueOf(roomUptMap.get("roomNo")))
						.build());
	}
	//
	/*************************************************************************************************************
	* @brief  : room 삭제 service
	* @method : roomUpdate
	* @author : 이민재
	**************************************************************************************************************/
	public ResponseEntity<RoomDeleteDto> roomDelete(HttpServletRequest request, HashMap<String, Object> paramMap) {
		//
		String					https_Url		=	null;
		try {
			//
			// *************************************************************************************************************
			// * 01. 넘어온 Parameter 검증
			// *************************************************************************************************************
			if(
					StringUtil.isEmpty(String.valueOf(paramMap.get("roomNo")))
					|| StringUtil.isEmpty(String.valueOf(paramMap.get("userId")))
					) {
						throw new ApiException("000001", commonConstant.M_000001);
				}
			else {
				//
				// 해당 화상회의 방의 유무 판단
				int roomSelect = roomMapper.roomSel(paramMap);
				//
				if(roomSelect <= 0) {
					//
					throw new ApiException("001001" , commonConstant.M_001001);
					//
				}
			}
			// userId 문자열 길이 체크
			String userIdLen = String.valueOf(paramMap.get("userId"));
			//
			if(userIdLen.length() > 32) {
				//
				throw new ApiException("000002", commonConstant.M_000002);
			}
			//
			// *************************************************************************************************************
			// * 02. 서비스 토큰 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			// HTTPS 통신위한 데이터 세팅
			JSONObject jsonData_token = new JSONObject();
			jsonData_token.put("svcCode"			, svcCode);
			jsonData_token.put("svcDomain"			, svcUrl);
			jsonData_token.put("userId"				, (String)paramMap.get("userId"));
			jsonData_token.put("svcNm"				, commonConstant.SERVICE_NAME);
			//
			// HTTPS 통신위한 url 세팅
			https_Url = apiUrl	+ tokenUrl;		
			//
			JSONObject response_token = httpsUtil.postData(https_Url, jsonData_token);
			//
			String resultCode_token 	= (String)response_token.get("resultCode");
			// Https 통신이 정상이 아니면
			if(!"000000".equals(resultCode_token)) {
				//
				throw new ApiException("000004" , commonConstant.M_000004);
				//
			}
			String svcToken = (String)response_token.get("tokenInfo");
			//
			// *************************************************************************************************************
			// * 03. 회의 room 삭제 요청 을 위한 방 비밀번호 조회
			// *************************************************************************************************************
			String roomPwSel = roomMapper.RoomPwSel(paramMap);
			// HTTPS를 통신을 위한 roomUpdate JSON 데이터 세팅
			// *************************************************************************************************************
			// * 04. 회의 room 삭제 요청
			// * HttpsConnection 활용
			// *************************************************************************************************************
			JSONObject jsonData_RD = new JSONObject();
			jsonData_RD.put("svcToken"	, svcToken);
			jsonData_RD.put("roomCode"	, String.valueOf(paramMap.get("roomNo")));
			jsonData_RD.put("userId"	, String.valueOf(paramMap.get("userId")));
			jsonData_RD.put("roomPw"	, roomPwSel);
			//
			https_Url ="";
			https_Url = apiUrl + rdUrl;
			//
			JSONObject response_RD = httpsUtil.postData(https_Url, jsonData_RD);
			//
			String resultCode_RD 	= (String)response_RD.get("resultCode");
			// 결과 코드가 정상이라면
			if("000000".equals(resultCode_RD)) {
				//
				// *************************************************************************************************************
				// * 05. 회의 TB_ROOM 삭제
				// *************************************************************************************************************
				roomMapper.roomDelete(paramMap);
				
				// *************************************************************************************************************
				// * 06. 회의 TB_ROOM_USER 삭제
				// *************************************************************************************************************
				roomMapper.tbRoomUserDel(paramMap);
				
				// *************************************************************************************************************
				// * 07. 회의 TB_STICKY_NOTES 삭제
				// *************************************************************************************************************
				memoMapper.memoDelete(paramMap);
				
				// *************************************************************************************************************
				// * 08. 회의 TB_FILE 삭제
				// *************************************************************************************************************
				fileMapper.fileDelete(paramMap);
				
				// *************************************************************************************************************
				// * 09. 회의  TB_WHITEBOARD_IMAGE 삭제
				// *************************************************************************************************************
				whiteboardMapper.delImg(paramMap);
				
				// *************************************************************************************************************
				// * 10. 기존 TB_ROOM_HISTORY 정보 조회
				// *************************************************************************************************************
				RoomHistDto roomHistDto = roomMapper.selTbRoomHist(paramMap);
				paramMap.put("roomTitle"	, roomHistDto.getRoomTitle());
				paramMap.put("roomTopic"	, roomHistDto.getRoomTopic());
				paramMap.put("joinCode"		, roomHistDto.getJoinCode());
				paramMap.put("managerId"	, roomHistDto.getManagerId());
				paramMap.put("startDt"		, roomHistDto.getStartDt());
				paramMap.put("endDt"		, roomHistDto.getEndDt());
				paramMap.put("roomStatus"	, roomHistDto.getRoomStatus());
				paramMap.put("quality"		, roomHistDto.getQuality());
				paramMap.put("maxPeople"	, roomHistDto.getMaxPeople());
				paramMap.put("roomType"		, roomHistDto.getRoomType());
				paramMap.put("screenType"	, roomHistDto.getScreenType());
				paramMap.put("viewYn"		, "N");
				//
				// *************************************************************************************************************
				// * 11. 회의 TB_ROOM_HISTORY 추가
				// *************************************************************************************************************
				paramMap.put("action", commonConstant.SAVE_DELETE);
				int roomHistcnt = roomMapper.delRoomHistIns(paramMap);
				if(roomHistcnt < 0) {
					throw new ApiException("001004" , commonConstant.M_001004);
				}
				//
			}else if("003002".equals(resultCode_RD)){
				//
				throw new ApiException("001006" , commonConstant.M_001006);
				//
			}else {
				//
				throw new ApiException("001004" , commonConstant.M_001004);
				//
			}
			//
		}catch(ApiException e) {
			if(StringUtil.isNotEmpty(e.getErrorLogMsg())) {
				logger.info("[roomDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
				System.out.println("[roomDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}else {
				logger.info("[roomDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getMessage());
				System.out.println("[roomDelete] ERROR_CODE=" + e.getErrorCode() + ", ERROR_MSG=" + e.getErrorLogMsg());
			}
    		return ResponseEntity.status(HttpStatus.OK)
					.body(RoomDeleteDto
							.builder()
							.resultCode(e.getErrorCode())
                            .resultMsg(e.getMessage())
							.build());
		}catch(Exception e) {
			logger.info("000003", commonConstant.M_000003, e.getMessage());
    		return ResponseEntity.status(HttpStatus.OK)
	    			.body(RoomDeleteDto
	    					.builder()
	    					.resultCode("000003")
	    					.resultMsg(commonConstant.M_000003)
	    					.build());
		}
		//
		return ResponseEntity.status(HttpStatus.OK)
				.body(RoomDeleteDto
						.builder()
						.resultCode("000000")
						.resultMsg(commonConstant.M_000000)
						.build());
	}
}
