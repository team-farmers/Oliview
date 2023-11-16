//=========================================================================
/* 찜 기능  */

const reviewLike = document.getElementById("reviewLike");

reviewLike.addEventListener("click", e => {

  // 1) 로그인 여부 확인
  if (!loginCheck) { // 로그인이 되어있지 않은 경우
    alert("로그인 후 이용해주세요");
    return;
  }

  // 2) 기존 찜 상태 확인
  let check;

  // "fa-regular" : 비어있는 하트
  // "fa-solid" : 채워져 있는 하트
  // 클릭한 하트에 class 속성값으로 "fa-regular"가 있으면 true
  if (e.target.classList.contains("fa-regular")) {
    check = 0;
  }
  else {
    check = 1;
  }

  // 3) 비동기
  const dataObj = { "reviewNo": reviewNo, "check": check };

  fetch("/review/like", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dataObj)
  })
    .then(resp => resp.text())
    .then(result => {

      if (result == -1) {
        return;
      }

      // 하트 클래스 리스트 있으면 삭제 없으면 추가
      e.target.classList.toggle("fa-regular");
      e.target.classList.toggle("fa-solid");

    })

    .catch(e => console.log(e));

})

//=========================================================================

/* 게시글 삭제 */
const deleteBtn = document.getElementById("deleteBtn");

// 만약 화면에 버튼이 없으면 null 반환
if (deleteBtn != null) { // 삭제 버튼이 존재하는 경우

  deleteBtn.addEventListener("click", () => {

    // confirm : 확인 클릭 -> true , 취소 클릭 -> false 반환
    if (confirm("삭제하시겠습니까?")) {

      // 상세 조회 페이지 주소 : /board/{boardCode}/{boardNo}
      // 삭제 요청 주소 : /editBoard/{boardCode}/{boardNo}/delete (GET)

      location.href = location.pathname.replace("review", "editReview") + "/delete";

    }

  });

}


//=========================================================================
/* 게시글 수정 버튼 클릭 시 수정 화면 요청 */

const updateBtn = document.getElementById("updateBtn");

if (updateBtn != null) { // 수정 버튼 존재 시
  updateBtn.addEventListener("click", () => {

    // 현재 : /board/{boardCode}/{boardNo}?cp=1
    // 바꾸고싶음 : /editBoard/{boardCode}/{boardNo}/update?cp=1


    let url = `/editReview/${reviewNo}/update${location.search}`;

    location.href = url;
  });
}






//=========================================================================
/* 신고 팝업창 */
function openReportPopup() {

  if (!loginCheck) {
    alert("로그인 후 이용해주세요");
    window.close();
    return;
  }

  const url = `/reviewReport?reviewNo=${reviewNo}`;
  const name = "신고하기";
  const option = "width = 430, height = 620, top = 200, left = 200, location = no"

  window.open(url, name, option);      
}



//=========================================================================
/* 목록으로 */

const prevBtn = document.getElementById("prevBtn");


prevBtn.addEventListener("click", () => {

  const goToPrevParam = new URL(location.href).searchParams;

  const obj = {};

  obj.searchInput = goToPrevParam.get("searchInput");
  obj.cp = goToPrevParam.get("cp");
  obj.cp2 = goToPrevParam.get("cp2")
  obj.sort = goToPrevParam.get("sort");
  obj.store = goToPrevParam.get("store");

  const tempParams = new URLSearchParams();

  for (let k in obj) {
    if (obj[k] != null) tempParams.append(k, obj[k]);
  }

  console.log(tempParams.toString());

  if (obj.store) {
    console.log("존재");
    location.href = `/review/store/${obj.store}?cp=${obj.cp2}`

  }
  else {
    location.href = `/review/searchReview?${tempParams.toString()}`;
  }


});

