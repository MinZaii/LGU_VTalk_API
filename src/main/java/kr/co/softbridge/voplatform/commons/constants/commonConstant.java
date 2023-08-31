package kr.co.softbridge.voplatform.commons.constants;
public class commonConstant {
	// 사용자 권한
	public static String SERVICE_ADMIN				= "A";			// 관리자
	public static String SERVICE_MANAGER			= "M";			// 방송매니저
	public static String SERVICE_USER				= "U";			// 사용자
	
	public static String SERVICE_NAME				= "가상오피스SVC";
	
	// 회의 접속 사용자 LEVEL
	public static String LEVEL_MANAGER				= "1001";		// 매니저
	public static String LEVEL_USER					= "2001";		// 사용자
	
	/* 회의리스트 코드상수 */
	// 방송타입
	public static String ROOM_TYPE_DEFAULT			= "01";			// 영상회의
	public static String ROOM_TYPE_M_TO_N			= "02";			// M:N
	public static String ROOM_TYPE_ONE_TO_N			= "03";			// 1:N
	
	// 방송상태
	public static String ROOM_STATUS_WAITING		= "01";			// 대기
	public static String ROOM_STATUS_RUNNING		= "02";			// 진행중
	public static String ROOM_STATUS_CLOSE			= "03";			// 종료
	
	// 방송 품질
	public static String QUALITY_DEFAULT			= "00";			// 참여자수
	public static String QUALITY_128				= "01";			// 128kb
	public static String QUALITY_256				= "02";			// 256kb
	public static String QUALITY_512				= "03";			// 512kb
	public static String QUALITY_1024				= "04";			// 1024kb
	public static String QUALITY_2048				= "05";			// 2048kb
	public static String QUALITY_4096				= "06";			// 4096kb
	public static String QUALITY_8192				= "07";			// 8192kb
	
	// 녹화타입
	public static String REC_TYPE_PRESENTER_ONLY	= "01";			// 발표자만
	public static String REC_TYPE_PARTICIPANTS		= "02";			// 참여자포함
	
	// 저장타입
	public static String SAVE_CRATE					 = "C";			// 등록
	public static String SAVE_UPDATE				 = "U";			// 수정
	public static String SAVE_DELETE				 = "D";			// 삭제
	
	/*
	 * 에러메세지 처리
	 */
	public static String M_000000					= "성공";
	public static String M_000001					= "필수 정보 누락 되었습니다.";
	public static String M_000002					= "올바른 입력 값이 아닙니다.";
	public static String M_000003					= "SQLException이 발생하였습니다.";
	public static String M_000004					= "서비스 토큰 생성을 실패 하였습니다.";
	
	public static String M_001001					= "회의 정보가 없습니다.";
	public static String M_001002					= "회의 생성을 실패 하였습니다.";
	public static String M_001003					= "회의 수정을 실패 하였습니다.";
	public static String M_001004					= "회의 삭제를 실패 하였습니다.";
	public static String M_001005					= "회의 수정 권한이 없습니다.";
	public static String M_001006					= "회의 삭제 권한이 없습니다.";
	
	public static String M_002001					= "파일이 존재하지 않습니다.";
	public static String M_002002					= "파일 등록을 실패 하였습니다.";
	public static String M_002003					= "파일 삭제를 실패 하였습니다.";
	public static String M_002004					= "파일 삭제 권한이 없습니다.";
	public static String M_002005					= "회의 접속 토큰 생성을 실패 하였습니다.";
	public static String M_002006					= "50MB 이상 파일을 등록할 수 없습니다.";
	
	
	public static String M_003001					= "포스트잇 등록을 실패 하였습니다.";
	public static String M_003002					= "포스트잇 수정을 실패 하였습니다.";
	public static String M_003003					= "포스트잇 삭제를 실패 하였습니다.";
	
	public static String M_004001					= "화이트보드 이미지 정보 등록을 실패 하였습니다.";
	public static String M_004002					= "화이트보드 이미지 정보 수정을 실패 하였습니다.";
	public static String M_004003					= "화이트보드 이미지 정보 삭제를 실패 하였습니다.";
	
	public static String M_999999					= "시스템에 오류가 발생하였습니다.";
	

	
	
}
