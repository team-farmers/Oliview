let selectChattingNo; // 선택한 채팅방 번호
let selectTargetNo; // 현재 채팅 대상
let selectTargetName; // 대상의 이름
let selectTargetProfile; // 대상의 프로필


/* ================= 채팅방 입장 또는 선택 함수 =================*/

function chattingEnter(e){

	// 채팅방 입장 (동기식!)

	fetch("/chatting/enter?selectChattingNo="+selectChattingNo)
	.then(resp => resp.text())
	.then(chattingNo => {
		
		setTimeout(()=>{ 
			// 만약 채팅방 목록 중 이미 존재하는 채팅방이 있으면 클릭해서 입장

			const itemList = document.querySelectorAll(".chatting-item")
			for(let item of itemList) {		
				if(item.getAttribute("chat-no") == chattingNo){
					item.focus();
					item.click();
					addTargetPopupLayer.classList.toggle("popup-layer-close"); // 검색창 열려있으면 닫음
					targetInput.value = "";
					resultArea.innerHTML = "";
					return;
				}
			}
		}, 200);
	})
	.catch(err => console.log(err));
}



/* ============== talk.html 영역 ============== */

// 채팅 메세지 영역
const display = document.getElementsByClassName("display-chatting")[0];

// 비동기로 메세지 목록을 조회하는 함수 -> 동기로 ㄱㄱ!!!!
function selectChattingFn() {

	fetch("/chatting/selectMessage?"+`chattingNo=${selectChattingNo}&memberNo=${loginMemberNo}`)
	.then(resp => resp.json())
	.then(messageList => {
		console.log(messageList);

		const ul = document.querySelector(".display-chatting");

		ul.innerHTML = ""; // 이전 내용 지우기

		// 메세지 만들어서 출력하기
		for(let msg of messageList){
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
				
			}else{ // 다른사람이 작성한 메세지인 경우
				li.classList.add("target-chat");

				// 상대 프로필 요소 추가
				const img = document.createElement("img");
				img.setAttribute("src", msg.senderProfile);

				const div = document.createElement("div");

				// 상대방 닉네임
				const b = document.createElement("b");
        selectTargetName = msg.senderNickname;
				b.innerText = selectTargetName; // 전역변수

				const br = document.createElement("br");

				div.append(b, br, p, span);
				li.append(img,div);
			}

			ul.append(li);
			display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
		}

	})
	.catch(err => console.log(err));
}

//============================================================처리중

// sockjs를 이용한 WebSocket 구현

// 로그인이 되어 있을 경우에만
// /chattingSock 이라는 요청 주소로 통신할 수 있는  WebSocket 객체 생성
let chattingSock;

if(loginMemberNo != ""){
	chattingSock = new SockJS("/chattingSock");
}


// 채팅 입력
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
		};
		console.log(obj)

		// JSON.stringify() : 자바스크립트 객체를 JSON 문자열로 변환
		chattingSock.send(JSON.stringify(obj));

		inputChatting.value = "";
	}
}

// 인풋메세지가 존재한다면, 엔터 누를시 sendMessage 호출
if(inputChatting != null){

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

	// 현재 채팅방을 보고있는 경우
	if(selectChattingNo == msg.chattingNo){

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
			img.setAttribute("src", selectTargetProfile);
			
			const div = document.createElement("div");
	
			// 상대 이름
			const b = document.createElement("b");
			b.innerText = selectTargetName;
	
			const br = document.createElement("br");
	
			div.append(b, br, p, span);
			li.append(img,div);
	
		}
	
		ul.append(li)
		display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
	}



	selectRoomList();
}



//============================================================처리중


/* 문서 로딩 완료 후 수행할 기능 */
document.addEventListener("DOMContentLoaded", ()=>{
	
  // 채팅방 입장 시 작동
  chattingEnter();

  // 채팅창에 대화 불러오기
  selectChattingFn();

  // 채팅방 목록에 클릭 이벤트 추가
	// roomListAddEvent(); 

	// 보내기 버튼에 이벤트 추가
	// send.addEventListener("click", sendMessage);
});


