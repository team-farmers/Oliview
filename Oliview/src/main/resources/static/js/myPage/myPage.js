/* 프로필 이미지 미리보기, 제거 */
const profileImg = document.getElementById("profileImg"); // img태그
let imageInput = document.getElementById("imageInput"); // input 태그
const deleteImageBtn = document.getElementById("deleteImageBtn"); // 삭제 버튼

// 프로필 이미지가 
// -1 : 변경되지 않았을 때
//  0 : 있었는데 없어짐 == x 버튼 클릭
//  1 : 새 이미지 선택 (없음 -> 있음, 있음 -> 다른 이미지)
let statusCheck = -1;

// 백업 변수
let backupInput; 

if(imageInput != null){ // #imageInput 존재할 때

  /* 프로필 이미지 변경(선택) 시 수행할 함수 */
  const changeImageFn = e =>{

    const uploadFile = e.target.files[0];

    // ---------------- 파일을 한 번 선택한 후 취소했을 때 ------------------
    if(uploadFile == undefined){

      // 1. 백업한 요소를 한번 더 복제
      const temp = backupInput.clonNode(true);

      // 2. 화면에 원본 input을 temp로 바꾸기
      imageInput.after(temp); // 원본 다음에 temp 추가
      imageInput.remove(); // 원본을 화면에서 제거
      imageInput = temp; // temp를 imageInput 변수에 대입

      imageInput.addEventListener("change", changeImageFn);

      return;
    }


    // ---------------- 선택된 파일의 크기가 지정된 크기를 초과하는 경우 --------------
    const maxSize =  1024 * 1024 * 5 ; // 5MB

    if(uploadFile.size > maxSize){

      alert("5MB 이하의 이미지만 업로드 가능합니다");
      
      if(statusCheck == -1){ // 이미지 변경이 없었을 때

          // 최대 크기를 초과해도 input에 value가 남기 때문에
          // 이를 제거하는 코드가 필요하다!
          imageInput.value=''; // value 삭제 
                              // 동시에 files도 삭제됨

          statusCheck = -1; // 선택 없음 상태


      } else { // 기존 이미지가 있었을 때

          // 1) backup한 요소를 한번 더 복제
          const temp = backupInput.cloneNode(true);

          // 2) 화면에 원본 input을 temp로 바꾸기
          imageInput.after(temp); // 원본 다음에 temp 추가
          imageInput.remove(); // 원본을 화면에서 제거
          imageInput = temp; // temp를 imageInput 변수에 대입

          // 복제본에 이벤트까지 복제되지 않으니 다시 이벤트를 추가함
          imageInput.addEventListener("change", changeImageFn);

          statusCheck = 1; // 값이 변경되었음
      }

      return;
    }

    // ---------------- 선택된 이미지 파일을 읽어와 미리보기 만들기 --------------

    // JS에서 파일을 읽는 객체
    // -> 파일을 읽고 클라이언트 컴퓨터에 파일을 저장할 수 있음
    const reader = new FileReader();

    // 매개변수에 작성된 파일을 읽어서
    // 파일을 나타내는 URL 형태로 변경
    // -> FileReader.result 필드에 저장되어 있음
    reader.readAsDataURL(uploadFile);

    // 파일을 다 읽었을 때
    reader.onload = e => {
      // img태그의 src 속성의 속성 값으로 
      // 읽은 파일의 URL을 대입

      profileImg.setAttribute("src", reader.result);

      statusCheck = 1; // 새 이미지 선택한 경우

      // 파일이 추가된 input을 backup 해두기
      backupInput = imageInput.cloneNode(true);
    }
  };

  /* 이미지 선택 버튼을 클릭하여 선택된 파일이 변했을 때 함수 수행 */

  // change 이벤트 : input의 이전 값과 현재 값이 다를 때 발생
  imageInput.addEventListener("change", changeImageFn)


  // ---------------- 삭제 버튼 클릭 시 기본 이미지로 변경 --------------
  // 1) 미리보기 -> 기본이미지 변경
  // 2) input태그에 value 값을 빈칸으로 변경(파일 없음)

  deleteImageBtn.addEventListener("click", ()=>{

      profileImg.setAttribute("src", deleteImageBtn);
      imageInput.value = "";

      backupInput.value = "";

      statusCheck = 0; // 있었는데 없어짐
  });


  //----------------- 프로필 이미지 변경 form 태그 제출 시 동작 -----------------

  const updateProfile = document.getElementById("updateProfile");

  updateProfile.addEventListener("submit", e => {
      let flag = true;

      // 1) 로그인한 회원의 프로필이 있음 -> 없음
      if(loginMemberProfileImg != null && statusCheck == 0) flag = false;
      
      // 2) 로그인한 회원의 프로필이 없음 -> 있음
      if(loginMemberProfileImg == null && statusCheck == 1) flag = false;
      
      // 3) 로그인한 회원의 프로필이 있음 -> 변경 
      if(loginMemberProfileImg != null && statusCheck == 1) flag = false;

      if(flag){ // flag가 true인 경우 수행
          e.preventDefault(); // 기본 이벤트 제거, form태그 제출 이벤트 막기(제거)
          alert("이미지 변경 후 클릭해주세요");
      }
  });

}




// 이미지 삭제



// 첫번째 적용버튼 누르면 닉네임 수정 스프링 호출
// document.getElementById("applyBtn").addEventListener("click", ()=>{

//   document.getElementById("updateProfile").submit();

// })

const updateProfile = document.getElementById("updateProfile");

function submitFrm(){
  updateProfile.submit();
};

