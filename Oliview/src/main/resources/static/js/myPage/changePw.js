const memberPw = document.getElementById("memberPw"); // 현재 비밀번호
const newPw = document.getElementById("newPw"); // 새 비밀번호
const newPwCheck = document.getElementById("newPw-check"); // 새 비밀번호 확인
const pwExplain = document.getElementById("pw-explain2"); // 비밀번호 유효성검사 안내문

/* 모든 입력이 유효성 검사가 진행되었는지 체크할 객체를 생성 */
const checkObj = {
  "newPw" : false,
  "newPwCheck" : false,
};

/* 새 비밀번호 유효성 검사 */

// 비밀번호 입력 시 유효성 검사
newPw.addEventListener("input", ()=>{

    // 비밀번호가 입력되지 않은 경우
    if(newPw.value.trim().length == 0){
      newPw.value = ""; //띄어쓰기 못넣게 하기

      pwExplain.innerText = "· 비밀번호는 영어,숫자,특수문자(!,@,#,-,_) 8~20글자 사이로 입력해주세요.";
      pwExplain.classList.remove("confirm", "error"); // 검정 글씨

      checkObj.newPw = false; // 빈칸 == 유효 X
      return;
    }

    
    // 정규 표현식을 이용한 비밀번호 유효성 검사

    // 영어,숫자,특수문자(!,@,#,-,_) 8~20글자 사이
    const regEx = /^[a-zA-Z0-9\!\@\#\-\_]{8,20}$/;

    // 입력한 비밀번호가 유효한 경우
    if(regEx.test(newPw.value)){
      checkObj.newPw = true; 
      
      // 비밀번호가 유효하게 작성된 상태에서
      // 비밀번호 확인이 입력되지 않았을 때
      if(newPwCheck.value.trim().length == 0){
        pwExplain.innerText = "· 유효한 비밀번호 형식입니다";
        pwExplain.classList.add("confirm");
        pwExplain.classList.remove("error");
      }
      
      // 비밀번호가 유효하게 작성된 상태에서
      // 비밀번호 확인이 입력되어 있을 때
      else{
        // 비밀번호 == 비밀번호 확인  (같을 경우)
        if(newPw.value == newPwCheck.value){
            pwExplain.innerText = "· 비밀번호가 일치합니다";
            pwExplain.classList.add("confirm");
            pwExplain.classList.remove("error");
            checkObj.newPwCheck = true;
            
        } else{ // 다를 경우
            pwExplain.innerText = "· 비밀번호가 일치하지 않습니다";
            pwExplain.classList.add("error");
            pwExplain.classList.remove("confirm");
            checkObj.newPwCheck = false;
        }
      }

        
    } else{ // 유효하지 않은 경우
        
      pwExplain.innerText = "· 비밀번호 형식이 유효하지 않습니다";
      pwExplain.classList.add("error");
      pwExplain.classList.remove("confirm");
      checkObj.newPw = false; 
    }
})


// 비밀번호 확인 유효성 검사
newPwCheck.addEventListener('input', ()=>{

  if(checkObj.newPw){ // 비밀번호가 유효하게 작성된 경우에

    // 비밀번호 == 비밀번호 확인  (같을 경우)
    if(newPw.value == newPwCheck.value){
        pwExplain.innerText = "· 비밀번호가 일치합니다";
        pwExplain.classList.add("confirm");
        pwExplain.classList.remove("error");
        checkObj.newPwCheck = true;
        
    } else{ // 다를 경우
        pwExplain.innerText = "· 비밀번호가 일치하지 않습니다";
        pwExplain.classList.add("error");
        pwExplain.classList.remove("confirm");
        checkObj.newPwCheck = false;
    }

  } else { // 비밀번호가 유효하지 않은 경우
      checkObj.newPwCheck = false;
  }
});


/* 비밀번호 변경 버튼이 클릭 되었을 때 */
document.getElementById("changePwForm").addEventListener("submit", e => {

  /* checkObj의 모든 값을 검사해서
     하나라도 false이면 가입 시도 X */

  for(let key in checkObj){

    // 객체에서 얻어온 값이 false인 경우
    // (유효하지 않은 것이 있을 경우)
    if( !checkObj[key] ){

      alert("비밀번호를 정확하게 입력해주세요.");

      // key == input id 속성 값
      // 유효하지 않은 input 태그로 focus 맞춤
      document.getElementById(key).focus();

      e.preventDefault(); // form 제출 X
      return;
    }
  }
})

// 취소버튼 누르면 작성취소
const resetBtn = document.getElementById("reset-btn");

resetBtn.addEventListener("click", ()=>{
  memberPw.value ='';
  newPw.value ='';
  newPwCheck.value ='';
})
