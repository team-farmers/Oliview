let selectTargetNo; // 현재 채팅 대상
let selectTargetName; // 대상의 이름
let selectTargetProfile; // 대상의 프로필


/* ============== chat.html 영역 ============== */


// 비동기로 채팅방 목록 조회
function selectRoomList(){

	fetch("/chatting/roomList")
	.then(resp => resp.json())
	.then(roomList => {

		// 채팅방 목록 출력 영역 선택
		const chattingList = document.querySelector(".chatting-list");

		// 채팅방 목록 지우기
		chattingList.innerHTML = "";

		console.log(chattingList);

		// 조회한 채팅방 목록을 화면에 추가
		for(let room of roomList){

			const li = document.createElement("li");
			li.classList.add("chatting-item");
			li.setAttribute("chat-no", room.chattingNo);

			if(room.chattingNo == selectChattingNo){
				li.classList.add("select");
			}

			// item-header 부분
			const itemHeader = document.createElement("div");
			itemHeader.classList.add("item-header");

      // 프로필 이미지 요소 생성
			const boardImg = document.createElement("img");
			boardImg.classList.add("boardImg");

			if(room.boardImg == undefined){
				boardImg.setAttribute("src", userDefaultImage);
			} else{
				boardImg.setAttribute("src", room.boardImg);
			}

			itemHeader.append(boardImg);


			// item-body 부분
			const itemBody = document.createElement("div");
			itemBody.classList.add("item-body");

      // item-body > p(1) 요소 생성
			const p = document.createElement("p");
			p.classList.add("profile"); // 추가함

      // 같이먹어요 게시글 제목(채팅방명)
			const boardTitle = document.createElement("span");
			boardTitle.classList.add("boardTitle");
			boardTitle.innerText = room.boardTitle;
			
      // 메세지 내용 요소 생성
			const recentMessage = document.createElement("p");
			recentMessage.classList.add("recent-message");

			if(room.lastMessage != undefined){
				recentMessage.innerHTML = room.lastMessage;
			}
			
			// item-body > p(1)에 닉네임, 참여인원, 메세지 내용 요소 추가
			p.append(boardTitle, recentMessage);
			

			// item-body > div(2) 요소 생성
			const div = document.createElement("div");
			div.classList.add("chat-status");

      // 보낸시간
			const recentSendTime = document.createElement("span");
			recentSendTime.classList.add("recent-send-time");
			recentSendTime.innerText = room.sendTime;

      // item-body > div(2) 요소에 메세지 보낸시간 추가
			div.append(recentSendTime);

      // item-body에 자식요소 p(1), div(2) 추가
			itemBody.append(p,div);


			// 현재 채팅방을 보고있는게 아니고 읽지 않은 개수가 0개 이상인 경우 -> 읽지 않은 메세지 개수 출력
			if(room.notReadCount > 0 && room.chattingNo != selectChattingNo ){
			// if(room.chattingNo != selectChattingNo ){
				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);

			}else{
				// 현재 채팅방을 보고있는 경우
				// 비동기로 해당 채팅방 글을 읽음으로 표시 --> 이건 내거에서 필요한가??
				fetch("/chatting/updateReadFlag",{
					method : "PUT",
					headers : {"Content-Type": "application/json"},
					body : JSON.stringify({"chattingNo" : selectChattingNo, "memberNo" : loginMemberNo})
				})
				.then(resp => resp.text())
				.then(result => console.log(result))
				.catch(err => console.log(err));
			}
			
			li.append(itemHeader, itemBody);
			chattingList.append(li);
		}

		roomListAddEvent();
	})
	.catch(err => console.log(err));
}

//=========================================================================================


// 채팅방 목록 클릭 시 채팅 페이지로 이동
function roomListAddEvent(){
	const chattingItemList = document.getElementsByClassName("chatting-item");
	
	for(let item of chattingItemList){

		// 채팅방 클릭 시
		item.addEventListener("click", e => {

			// 클릭한 채팅방의 번호 얻어오기
			//const id = item.getAttribute("id");
			//const arr = id.split("-");
			// 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
			selectChattingNo = item.getAttribute("chat-no");

			console.log(selectChattingNo);

			selectTargetProfile = item.children[0].children[0].getAttribute("src");
			selectTargetName = item.children[1].children[0].children[0].innerText;

			if(item.children[1].children[1].children[1] != undefined){
				item.children[1].children[1].children[1].remove();
			}

			// 모든 채팅방에서 select 클래스를 제거
			for(let it of chattingItemList) it.classList.remove("select")
	
			// 현재 클릭한 채팅방에 select 클래스 추가
			item.classList.add("select");
	
			// 채팅방 목록 클릭 시 채팅룸으로 이동		// 맞는지 확인!! 아마 아닐거거든!^^ 쿼리스트링~~
			location.href='/chatting/talk/' + selectChattingNo;
		});
	}
}





//============================================================ 웹소켓 영역 

// sockjs를 이용한 WebSocket 구현

// 로그인이 되어 있을 경우에만
// /chattingSock 이라는 요청 주소로 통신할 수 있는  WebSocket 객체 생성
let chattingSock;

if(loginMemberNo != ""){
	chattingSock = new SockJS("/chattingSock");
}


/* 채팅 입력 */
const send = document.getElementById("send"); // 입력버튼

const inputChatting = document.getElementById("inputChatting"); // 인풋 메세지

// 메세지 전송 데이터 가공
const sendMessage = () => {

	if (inputChatting.value.trim().length == 0) {
		alert("메세지를 입력해주세요.");
		inputChatting.value = "";

	} else {
		var obj = {
			"senderNo": loginMemberNo, // 누가
			"chattingNo": selectChattingNo, // 몇번 채팅방에서
			"messageContent": inputChatting.value, // 어떤 내용을 보냈다
			"senderProfile" : loginMember.memberProfile,
			"senderNickname" : loginMember.memberNickname
		};
		console.log(obj)

		// JSON.stringify() : 자바스크립트 객체를 JSON 문자열로 변환
		chattingSock.send(JSON.stringify(obj));

		inputChatting.value = "";
	}
}

// 인풋메세지가 존재한다면, 입력 버튼 클릭 or 엔터 누를시 sendMessage 호출
if(inputChatting != null){

	// 입력 버튼 클릭 시 메세지 전송
	send.addEventListener("click", ()=>{
		sendMessage();
	})

	// 엔터 == 제출 // 엔터 누르면 sendMessage 호출
	// 쉬프트 + 엔터 == 줄바꿈
	inputChatting.addEventListener("keyup", e => {
		if(e.key == "Enter"){ 
			if (!e.shiftKey) {
				sendMessage();
			}
		}
	})
}



// 서버가 보낸 메세지를 받는 코드
// WebSocket 객체 chattingSock이 서버로 부터 메세지를 통지 받으면 자동으로 실행될 콜백 함수
chattingSock.onmessage = function(e) {
	// 메소드를 통해 전달받은 객체값을 JSON객체로 변환해서 obj 변수에 저장. // e.date : 전달받은 메세지
	const msg = JSON.parse(e.data);
	console.log(msg);

	const ul = document.querySelector(".display-chatting");

	// 메세지 만들어서 출력하기
	const li = document.createElement("li");

	// 보낸 시간
	const span = document.createElement("span");
	span.classList.add("chatDate");
	span.innerText = msg.sendTime;

	// 메세지 내용
	const p = document.createElement("p");
	p.classList.add("chat");
	p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML

	// 내가 작성한 메세지인 경우
	if(loginMemberNo == msg.senderNo){ 
		li.classList.add("my-chat");
		
		li.append(span, p);
		
	} else{ // 상대가 작성한 메세지인 경우
		li.classList.add("target-chat");

		// 상대 프로필
		const img = document.createElement("img");

		// 상대 프로필이 없는 경우 기본이미지로 대체
		if(msg.senderProfile == null){
			img.setAttribute("src", userDefaultImage);
		} else {
			img.setAttribute("src", msg.senderProfile);
		}
		
		const div = document.createElement("div");

		// 상대 이름
		const b = document.createElement("b");
		b.innerText = msg.senderNickname;

		const br = document.createElement("br");

		div.append(b, br, p, span);
		li.append(img,div);

	}

	ul.append(li)
	display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
}




/* 문서 로딩 완료 후 수행할 기능 */
document.addEventListener("DOMContentLoaded", ()=>{
	
  // 채팅방 입장 시 작동
  // chattingEnter();

  // 채팅창에 대화 불러오기
  // selectChattingFn();

  // 채팅방 목록에 클릭 이벤트 추가
	// roomListAddEvent(); 

	// 보내기 버튼에 이벤트 추가
	// send.addEventListener("click", sendMessage);

	// 채팅목록 불러오기
	selectRoomList();
});
