/*
*  어드민포털에서 발급받은 SVC_CODE로 서비스토큰 생성하는 function
*  ajax를 활용하여 서비스토큰 생성 처리를 한다.
*  function 이름은 임의로 정함.
*/
function svcToken(){
	// 서비스토큰 생성을 위한 Parameter
	let svcCode		= "d92kg9sf8dswc8sa810";	// 어드민포털에서 발급받은 서비스코드
	let svcDomain	= "https://localhost:8080";	// 해당 등록된 서비스의 도메인
	let svcNm		= "LGU+ 화상회의";				// 해당 등록된 서비스의 이름
	let userId		= "streamer1";				// 관리자 ID
	let param = {
		svcCode: svcCode
		, svcDomain: svcDomain
		, svcNm: svcNm
		, userId: userId
	}
	$.ajax({
		// API DEV서버 	[ https://api.dev.ulsp.uplus.co.kr ]
		// API QA서버 	[ https://api.qa.ulsp.uplus.co.kr ]
		// API 운영서버 	[ https://api.ulsp.uplus.co.kr ]
		// 방 생성 url = 	[ DEV서버 : /sobro-dev-login/auth , QA서버 : /sobro-qa-login/auth , 운영서버 : /sobro-login/auth ] 
		url: 'https://api.qa.ulsp.uplus.co.kr/sobro-qa-login/auth'
		, data: JSON.stringify(param)
		, type: 'POST'						// 현재 API는 PostMapping으로 되어 있음.
		, contentType: 'application/json'	// json형태로 통신 함.
		, dataType: 'json'
		, success: function(data){
			if(data.resultCode == '000000'){
				alert('서비스토큰 생성 성공 !');
			}else{
				alert('서비스토큰 생성 실패!');
			}
		}, error: function(error){
			alert('서비스토큰 생성 실패!');
		}
	});
}
//------------------------------------------------------------------------------------------------------------------------------------------------
/*
*  화상회의 방 생성 JavaScript에서의 플랫폼API 호출 function
*  ajax를 활용하여 화상회의 방 생성 처리를 한다.
*  function 이름은 임의로 정함.
*/
function roomCreate(){
	// 방 생성을 위한 Parameter, 화면에서 값이 넘어와야 하지만 아래 소스는 임의로 정함.
	// 연동규격서를 참고하여 필수 값 세팅
	// let, const 등 변수 타입은 상관없음.
	let title 		= "화상회의 방 생성";
	let startDt 	= "20220407090000".format('YYYY-MM-DD HH:mm:ss');	// 개설하는 화상회의의 '시작' 시간이 22년 04월 07일 09시
	let endDt 		= "20220407120000".format('YYYY-MM-DD HH:mm:ss');	// 개설하는 화상회의의 '종료' 시간이 22년 04월 07일 12시
	let userId 		= "streamer1";	// 관리자 로그인 했을 때의 ID 
	let userNm 		= "관리자";		// 관리자 이름
	let roomPw 		= "1234";		// 화상회의 입장 시 방의 비밀번호
	let quality 	= "01";			// 화상회의 영상 품질 [ 01 : 낮음 (2Mbps)  / 02 : 보통 (4Mbps) / 03 : 좋음 (6Mbps)  / 04 : 아주좋음 (8Mbps) ]
	let maxPeople 	= "20";			// 최대 참여자 수 20명
	let viewYn		= "Y";			// 노출 여부
	let screenType	= "30";			// 화면공유 유형 [ 10 : 내화면 / 20 : 영상 / 30 : 문서 ] 화면 공유 했을 때의 품질이 달라짐.
	let roomType	= "01";			// 방송 타입 ( 01 : 화상회의 / 02 : M:N방송 / 03 : 1:N방송 )
	let svcToken	= "";			// 서비스토큰 ( 관리자 로그인 했을 때 생성했었던 svcToken ) 모든 API기능의 행위는 svcToken을 가지고 인증을 하므로 필수 !
	// API호출 할 때 배열에 세팅
	// 플랫폼 API에서는 Map으로 받음.
	let param = {
		title: title
		, userId: userId
		, userNm: userNm
		, startDt: startDt
		, endDt: endDt
		, roomPw: roomPw
		, quality: quality
		, maxPeople: maxPeople
		, viewYn: viewYn
		, screenType: screenType
		, roomType: roomType
		, svcToken: svcToken
	}
	$.ajax({
		// API DEV서버 	[ https://api.dev.ulsp.uplus.co.kr ]
		// API QA서버 	[ https://api.qa.ulsp.uplus.co.kr ]
		// API 운영서버 	[ https://api.ulsp.uplus.co.kr ]
		// 방 생성 url = 	[ DEV서버 : /sobro-dev-room/create , QA서버 : /sobro-qa-room/create , 운영서버 : /sobro-room/create ] 
		url: 'https://api.qa.ulsp.uplus.co.kr/sobro-qa-room/create'
		, data: JSON.stringify(param)
		, type: 'POST'						// 현재 API는 PostMapping으로 되어 있음.
		, contentType: 'application/json'	// json형태로 통신 함.
		, dataType: 'json'
		, success: function(data){
			// 성공했을 때의 코드 (000000)
			// 결과코드표는 연동규격서 마지막페이지 참고
			if(data.resultCode == '000000'){
				alert('화상회의 방 생성 성공 !');
			}else{
				// 성공 이외일 경우 다른 결과코드표에 있는 메세지를 넘겨준다.
				alert(data.resultMsg);
			}
		}, error: function(error){
			alert('화상회의 방 생성 실패 !');
		}
	});
}
//------------------------------------------------------------------------------------------------------------------------------------------------
