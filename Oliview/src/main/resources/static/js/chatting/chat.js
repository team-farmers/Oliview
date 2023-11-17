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
			if(room.notReadCount > 0 ){

				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);

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
	
			// 채팅방 목록 클릭 시 채팅룸으로 이동
			location.href='/chatting/talk/' + selectChattingNo;
		});
	}
}




/* 문서 로딩 완료 후 수행할 기능 */
document.addEventListener("DOMContentLoaded", ()=>{
	
	// 채팅목록 불러오기
	selectRoomList();
});
