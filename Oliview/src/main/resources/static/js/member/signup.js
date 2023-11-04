/****** 약관동의 체크 ******/
const agreeForm = document.getElementById("agreeForm"); // 폼
const agreeAll = document.getElementById("agreeAll"); // 전체동의 체크박스
const agreeCheckBoxs = document.querySelectorAll(".agree-check-boxs")// 전체동의를 제외한 3개의 체크박스
const nextBtn = document.querySelector(".next-btn"); // 다음 버튼


const agreements = {
    agreeSite : false, // 필수_사이트이용약관
    agreePersonal : false, // 필수_개인정보 이용약관
    agreeChoice : false // 선택_개인정보 수집
}


agreeAll.addEventListener("click", e=>{

    const checked = e.target.checked;

    if(checked){
        agreeCheckBoxs.forEach((item) => {
            item.checked = true;
            agreements[item.id] = true;
            item.parentNode.classList.add('active');
        });

    } else {
        agreeCheckBoxs.forEach((item)=>{
            item.checked = false;
            agreements[item.id] = false;
            item.parentNode.classList.remove('active');
        });
    }
    
    toggleNextBtn();
})


agreeCheckBoxs.forEach((item) => item.addEventListener("input", toggleAgreeCheckBoxs));


function agreeAllStatus(){
    const {agreeSite, agreePersonal, agreeChoice } = agreements;
    if(agreeSite && agreePersonal && agreeChoice) {
        agreeAll.checked = true;
    } else{
        agreeAll.checked = false;
    }
}


function toggleAgreeCheckBoxs(e){
    const { checked, id } = e.target;
    agreements[id] = checked;
    this.parentNode.classList.toggle('active');
    agreeAllStatus();
    toggleNextBtn();
}


function toggleNextBtn(){
    const { agreeSite, agreePersonal } = agreements;

    if(!(agreeSite && agreePersonal)){
        agreeForm.addEventListener("submit", e=> {
            e.preventDefault();
            alert("약관에 동의해주세요.")
        }); // 필수 동의사항이 체크되지 않으면 새로고침(submit) 되는 것 막음
    }
}




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
  "memberName" : false
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
              authKeyMessage.innerText = "· 사용 가능한 이메일입니다.";
              authKeyMessage.classList.add("confirm"); // 초록색 글씨
              authKeyMessage.classList.remove("error"); // 빨간글씨 제거
              checkObj.memberEmail = true; // 유효한 상태임을 기록
          } else{ // 중복 O
              authKeyMessage.innerText = "· 이미 사용중인 이메일입니다.";
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
    authKeyMessage.innerText = "· 알맞은 이메일 형식으로 작성해주세요.";
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
        alert("· 중복되지 않은 이메일을 작성해주세요.");
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
                authKeyMessage.innerText = "· 인증되었습니다.";
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


/* 아이디 유효성 검사 */
const memberId = document.getElementById("memberId");
const alertIdMessage = document.getElementById("alertId-message");

// 아이디 입력 시 유효성 검사
memberId.addEventListener("input", ()=>{

    // 아이디가 입력되지 않은 경우
    if(memberId.value.trim().length == 0){
        memberId.value = ""; //띄어쓰기 못넣게 하기

        alertIdMessage.innerText = "· 아이디는 영어,숫자 5~20글자 사이로 입력해주세요.";
        alertIdMessage.classList.remove("confirm", "error"); // 검정 글씨

        checkObj.memberId = false; // 빈칸 == 유효 X
        return;
    }
    
    // 정규 표현식을 이용한 아이디 유효성 검사

    // 영어,숫자 5~20글자 사이
    const regEx = /^[a-zA-Z0-9]{5,20}$/;

    // 입력한 아이디가 유효한 경우
    if(regEx.test(memberId.value)){

        /* ============== 아이디 중복 검사(비동기) ============== */

        fetch("/member/checkId?id=" + memberId.value)
        .then(response => response.text())
        .then(result => {

            if(result == 0) { // 중복 X
                alertIdMessage.innerText = "· 사용 가능한 아이디입니다";
                alertIdMessage.classList.add("confirm");
                alertIdMessage.classList.remove("error");
                checkObj.memberId = true; 
            } else { // 중복 O
                alertIdMessage.innerText = "· 이미 사용중인 아이디입니다";
                alertIdMessage.classList.add("error"); 
                alertIdMessage.classList.remove("confirm");
                checkObj.중복 = false;
            }
        })
        .catch(e => console.log(e))
        
        /* ====================================================== */


    } else{ // 유효하지 않은 경우
        alertIdMessage.innerText = "· 아이디 형식이 유효하지 않습니다";
        alertIdMessage.classList.add("error");
        alertIdMessage.classList.remove("confirm");
        checkObj.memberId = false; 
    }
})






/* 비밀번호 유효성 검사 */
/*  비밀번호/비밀번호 확인 유효성 검사 */
const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const alertPwMessage = document.getElementById("alertPw-message");


// 비밀번호 입력 시 유효성 검사
memberPw.addEventListener("input", ()=>{

    // 비밀번호가 입력되지 않은 경우
    if(memberPw.value.trim().length == 0){
        memberPw.value = ""; //띄어쓰기 못넣게 하기

        alertPwMessage.innerText = "· 비밀번호는 영어,숫자,특수문자(!,@,#,-,_) 8~20글자 사이로 입력해주세요.";
        alertPwMessage.classList.remove("confirm", "error"); // 검정 글씨

        checkObj.memberPw = false; // 빈칸 == 유효 X
        return;
    }

    
    // 정규 표현식을 이용한 비밀번호 유효성 검사

    // 영어,숫자,특수문자(!,@,#,-,_) 8~20글자 사이
    const regEx = /^[a-zA-Z0-9\!\@\#\-\_]{8,20}$/;

    // 입력한 비밀번호가 유효한 경우
    if(regEx.test(memberPw.value)){
        checkObj.memberPw = true; 
        
        // 비밀번호가 유효하게 작성된 상태에서
        // 비밀번호 확인이 입력되지 않았을 때
        if(memberPwConfirm.value.trim().length == 0){

            alertPwMessage.innerText = "· 유효한 비밀번호 형식입니다";
            alertPwMessage.classList.add("confirm");
            alertPwMessage.classList.remove("error");
        }
        
        // 비밀번호가 유효하게 작성된 상태에서
        // 비밀번호 확인이 입력되어 있을 때
        else{
            // 비밀번호 == 비밀번호 확인  (같을 경우)
            if(memberPw.value == memberPwConfirm.value){
                alertPwMessage.innerText = "· 비밀번호가 일치합니다";
                alertPwMessage.classList.add("confirm");
                alertPwMessage.classList.remove("error");
                checkObj.memberPwConfirm = true;
                
            } else{ // 다를 경우
                alertPwMessage.innerText = "· 비밀번호가 일치하지 않습니다";
                alertPwMessage.classList.add("error");
                alertPwMessage.classList.remove("confirm");
                checkObj.memberPwConfirm = false;
            }
        }

        
    } else{ // 유효하지 않은 경우
        
        alertPwMessage.innerText = "· 비밀번호 형식이 유효하지 않습니다";
        alertPwMessage.classList.add("error");
        alertPwMessage.classList.remove("confirm");
        checkObj.memberPw = false; 
    }
})


// 비밀번호 확인 유효성 검사
memberPwConfirm.addEventListener('input', ()=>{

    if(checkObj.memberPw){ // 비밀번호가 유효하게 작성된 경우에

        // 비밀번호 == 비밀번호 확인  (같을 경우)
        if(memberPw.value == memberPwConfirm.value){
            alertPwMessage.innerText = "· 비밀번호가 일치합니다";
            alertPwMessage.classList.add("confirm");
            alertPwMessage.classList.remove("error");
            checkObj.memberPwConfirm = true;
            
        } else{ // 다를 경우
            alertPwMessage.innerText = "· 비밀번호가 일치하지 않습니다";
            alertPwMessage.classList.add("error");
            alertPwMessage.classList.remove("confirm");
            checkObj.memberPwConfirm = false;
        }

    } else { // 비밀번호가 유효하지 않은 경우
        checkObj.memberPwConfirm = false;
    }
});



/* 이름 유효성 검사 */
const memberName = document.getElementById("memberName");
const alertNameMessage = document.getElementById("alertName-message");

memberName.addEventListener("input", ()=>{

    // 미입력 시
    if(memberName.value.trim().length == 0){
        memberName.value = '';
        alertNameMessage.innerText = "· 이름 한글 2~18글자를 입력해주세요.";
        checkObj.memberName = false;
        alertNameMessage.classList.remove("confirm", "error");
        return;
    }

    const regEx = /^[가-힣]{2,18}$/;

    if( regEx.test(memberName.value) ){
        alertNameMessage.innerText = "· 유효한 이름 형식입니다";
        alertNameMessage.classList.add("confirm"); 
        alertNameMessage.classList.remove("error");
        checkObj.memberName = true;
    }
    
    else{
        alertNameMessage.innerText = "· 유효하지 않은 이름 형식입니다";
        alertNameMessage.classList.add("error");
        alertNameMessage.classList.remove("confirm");
        checkObj.memberName = false;
    }
})



/* 닉네임 유효성 검사 */
const memberNickname = document.getElementById("memberNickname");
const alertNicknameMessage = document.getElementById("alertNickname-message");

memberNickname.addEventListener("input", ()=>{

    // 미입력 시
    if(memberNickname.value.trim().length == 0){
        memberNickname.value = '';
        alertNicknameMessage.innerText = "· 닉네임은 한글,영어,숫자로만 2~10글자 사이로 입력해주세요.";
        checkObj.memberNickname = false;
        alertNicknameMessage.classList.remove("confirm", "error");
        return;
    }

    const regEx = /^[가-힣\w\d]{2,10}$/;

    if( regEx.test(memberNickname.value) ){

        /* ============== 닉네임 중복 검사(비동기) ============== */

        fetch("/member/checkNickname?nickname=" + memberNickname.value)
        .then(response => response.text())
        .then(result => {

            if(result == 0) { // 중복 X
                alertNicknameMessage.innerText = "· 사용 가능한 닉네임입니다";
                alertNicknameMessage.classList.add("confirm"); 
                alertNicknameMessage.classList.remove("error");
                checkObj.memberNickname = true;

            } else { // 중복 O
                alertNicknameMessage.innerText = "· 이미 사용중인 닉네임입니다";
                alertNicknameMessage.classList.add("error"); 
                alertNicknameMessage.classList.remove("confirm");
                checkObj.memberNickname = false;
            }
        })
        .catch(e => console.log(e))
        
        /* ====================================================== */
    }
    
    else{
        alertNicknameMessage.innerText = "· 유효하지 않은 닉네임 형식입니다";
        alertNicknameMessage.classList.add("error");
        alertNicknameMessage.classList.remove("confirm");
        checkObj.memberNickname = false;
    }
})



/* 회원 가입 버튼이 클릭 되었을 때 */
document.getElementById("signupForm").addEventListener("submit", e => {

    /* checkObj의 모든 값을 검사해서
       하나라도 false이면 가입 시도 X */

    // 객체 전용 향상된 for문 (for .... in)

    for(let key in checkObj){

        // 객체에서 얻어온 값이 false인 경우
        // (유효하지 않은 것이 있을 경우)
        if( !checkObj[key] ){

            alert("가입정보를 정확하게 입력해주세요.");

            // key == input id 속성 값
            // 유효하지 않은 input 태그로 focus 맞춤
            document.getElementById(key).focus();

            e.preventDefault(); // form 제출 X
            return;
        }
    }
})

