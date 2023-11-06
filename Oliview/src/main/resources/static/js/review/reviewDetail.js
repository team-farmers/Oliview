//=========================================================================
/* 찜 기능  */

const reviewLike = document.getElementById("reviewLike");

reviewLike.addEventListener("click", e => {

  // 1) 로그인 여부 확인
  if(!loginCheck){ // 로그인이 되어있지 않은 경우
    alert("로그인 후 이용해주세요");
    return;
  }

  // 2) 기존 찜 상태 확인
  let check;

  // "fa-regular" : 비어있는 하트
  // "fa-solid" : 채워져 있는 하트
  // 클릭한 하트에 class 속성값으로 "fa-regular"가 있으면 true
  if(e.target.classList.contains("fa-regular")){
    check = 0;
  }
  else{
    check = 1;
  }

  // 3) 비동기
  const dataObj= {"reviewNo" : reviewNo, "check":check};

  fetch("/review/like",{
    method : "POST",
    headers : {"Content-Type":"application/json"},
    body : JSON.stringify(dataObj)
  })
  .then(resp => resp.text())
  .then(result =>{

    if(result == -1){
      return;
    }

    // 하트 클래스 리스트 있으면 삭제 없으면 추가
    e.target.classList.toggle("fa-regular");
    e.target.classList.toggle("fa-solid");

  })

  .catch(e => console.log(e));

})




//=========================================================================
/* 신고 팝업창 */
// function openReportPopup(){

//   const url = "report.html";
//   const name = "신고하기";
//   const option = ""

//   window.open
// }