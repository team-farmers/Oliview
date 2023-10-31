/****** 회원가입 유효성 검사 ******/
// .confirm : #66B97E / .error : 빨간색 / 아무것도 없음 : 검은색

/* 모든 입력이 유효성 검사가 진행되었는지 체크할 객체를 생성 */
const checkObj = {
  "memberId" : false,
  "memberPw" : false,
  "memberPwConfirm" : false,
  "memberEmail" : false,
  "authKey" : false,
  "memberName" : false,
  "memberNickname" : false
};



/* 이메일 유효성 검사 */

// 1) 이메일 유효성 검사에 사용할 요소 모두 얻어오기
const memberEmail = document.getElementById("memberEmail");
const authKeyMessage = document.getElementById("authKeyMessage");

// 2) 이메일이 입력(input) 될 때 마다 유효성 검사 실행
memberEmail.addEventListener("input", ()=>{

  // 3) 이메일 정규식 검사
  const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;

  // 입력 받은 이메일이 정규식과 일치하는 경우
  if( regEx.test(memberEmail.value) ){

      /* ============== 이메일 중복 검사(비동기) ============== */
      fetch("/member/checkEmail?email=" + memberEmail.value)
      .then(response => response.text())
      .then(result => { 

          // 중복되는 이메일이 없음
          if(result == 0){ 
              authKeyMessage.innerText = "사용 가능한 이메일입니다.";
              authKeyMessage.classList.add("confirm"); // 초록색 글씨
              authKeyMessage.classList.remove("error"); // 빨간글씨 제거
              checkObj.memberEmail = true; // 유효한 상태임을 기록
          } else{ // 중복 O
              authKeyMessage.innerText = "이미 사용중인 이메일입니다.";
              authKeyMessage.classList.add("error"); // 초록색 글씨
              authKeyMessage.classList.remove("confirm"); // 빨간글씨 제거
              checkObj.memberEmail = false;
          }
       })
      .catch(e => console.log(e))

      /* ====================================================== */
  }

  // 입력 받은 이메일이 정규식과 일치하지 않은 경우
  else {
    authKeyMessage.innerText = "알맞은 이메일 형식으로 작성해주세요.";
    authKeyMessage.classList.add("error"); // 빨간글씨 글씨
    authKeyMessage.classList.remove("confirm"); // 초록색 제거
    checkObj.memberEmail = false; // 유효하지 않은 상태임을 기록
  }
});



//============================= 이메일 인증 ============================

/* 인증번호 메일로 보내기 + DB INSERT */
const sendAuthKeyBtn = document.getElementById("sendAuthKeyBtn")

// 인증번호 보내기 버튼을 클릭 하면
// authKeyMessage에 5분 타이머를 출력

let authTimer;
let authMin = 4;
let authSec = 59;

// 인증번호를 보낸 이메일을 저장할 변수
let tempEmail;

// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", function(){
    authMin = 4;
    authSec = 59;

    checkObj.authKey = false;

    if(checkObj.memberEmail){ // 중복이 아닌 이메일인 경우


        /* fetch() API - POST방식, 단일 데이터 요청 */
        fetch("/email/signup", {
            method : "POST",
            headers : {"Content-Type" : "application/text"},
            body : memberEmail.value // 전달되는 데이터가 한 개
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                console.log("인증 번호가 발송되었습니다.")
                tempEmail = memberEmail.value;
            }else{
                console.log("인증번호 발송 실패")
            }
        })
        .catch(err => {
            console.log("이메일 발송 중 에러 발생");
            console.log(err);
        });
        

        alert("인증번호가 발송 되었습니다.");

        
        authKeyMessage.innerText = "05:00";
        authKeyMessage.classList.remove("confirm");

        authTimer = window.setInterval(()=>{

            authKeyMessage.innerText = "0" + authMin + ":" + (authSec<10 ? "0" + authSec : authSec);
            
            // 남은 시간이 0분 0초인 경우
            if(authMin == 0 && authSec == 0){
                checkObj.authKey = false;
                clearInterval(authTimer);
                return;
            }

            // 0초인 경우
            if(authSec == 0){
                authSec = 60;
                authMin--;
            }

            authSec--; // 1초 감소

        }, 1000)

    } else{
        alert("중복되지 않은 이메일을 작성해주세요.");
        memberEmail.focus();
    }

});


/* 인증번호 확인 */
const authKey = document.getElementById("authKey");
const checkAuthKeyBtn = document.getElementById("checkAuthKeyBtn");

checkAuthKeyBtn.addEventListener("click", function(){

    if(authMin > 0 || authSec > 0){ // 시간 제한이 지나지 않은 경우에만 인증번호 검사 진행
        /* fetch API */
        const obj = {"inputKey":authKey.value, "email":tempEmail}

        fetch("/email/checkAuthKey",  {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                clearInterval(authTimer);
                authKeyMessage.innerText = "인증되었습니다.";
                authKeyMessage.classList.add("confirm");
                checkObj.authKey = true;

                authKey.disabled = true; // 비활성화
            } else{
                alert("인증번호가 일치하지 않습니다.")
                checkObj.authKey = false;
            }
        })
        .catch(err => console.log(err));


    } else{
        alert("인증 시간이 만료되었습니다. 다시 시도해주세요.")
    }

});


//======================================================================