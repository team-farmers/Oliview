/* 목록으로 버튼 */

const invenBtn =document.getElementById("invenBtn");

if(invenBtn != null) { // 화면에 목록으로 버튼이 있을때만 수행

    // 함수 선언(변수 대입 형식)
    const invenBtnFn = ( ) => {

      console.log("버튼 클릭");

      const paramMap = new URL(location.href).searchParams;

      const obj = {}; // 주소에 담겨있는 파라미터를 담은 객체
      

      obj.cp = paramMap.get("cp");
      obj.key = paramMap.get("key");
      obj.query = paramMap.get("query");

      const tempParams = new URLSearchParams();

      for(let key in obj){ // 객체 전용 향상된 for문
        if(obj[key] != null) tempParams.append(key,obj[key]);

      }

      // 2) 목록으로 돌아가기
      location.href = `/together/inven?${tempParams.toString()}`;


    }
    // 이벤트 리스너 추가
    invenBtn.addEventListener("click" , invenBtnFn);

}




















































// --------------------------------------------------------------
/* 게시글 삭제 */
const deleteBtn = document.getElementById("deleteBtn");

// 만약 화면에 버튼이 없으면 null 반환

if(deleteBtn != null){ // 삭제 버튼이 존재하는경우

  deleteBtn.addEventListener ("click", () => {

    // confirm : 확인 클릭 -> true / 취소 클릭 -> false반환
    if(confirm("삭제하시겠습니까?")){


        // 상세조회 페이지 주소 : /board/{boardCode}/{boardNo}
        // 상세조회 페이지 주소 : /board/{boardCode}/{boardNo}/delete(GET)

      location.href
      = location.pathname.replace("together", "editTogether") + "/delete";

    }

  });

}

// ---------------------------------------------------------

/* 수정 버튼 클릭 시 수정 화면 요청 */
const updateBtn = document.getElementById("updateBtn");

if (updateBtn != null) { // 수정 버튼 존재 시
  updateBtn.addEventListener("click", () => {
	  
    let url = `/editTogether/${boardNo}/update${location.search}`;
    location.href = url;
  });
}


//=========================================================================
/* 신고 팝업창 */
function openReportPopup(){

  const url = "../report";
  const name = "신고하기";
  const option = "width = 430, height = 620, top = 200, left = 200, location = no"

  window.open(url, name, option);
}
