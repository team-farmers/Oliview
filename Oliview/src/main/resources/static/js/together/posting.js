// 미리보기 관련 요소 모두 얻어오기

// img 
const preview = document.getElementsByClassName("preview")[0];

// input type="file"
let imageInput = document.querySelector("#img");

// x버튼
const deleteImage = document.querySelector(".delete_image");


let backupInput;


/* 이미지 선택 시 수행할 함수 */
const changeImageFn = e => {
  const uploadFile = e.target.files[0];

  // imageInput : 파일이 선택/취소된 input 태그
  // order : input 태그 순서(썸네일 0, 나머지 1~4)


  // ---------- 파일을 한 번 선택한 후 취소했을 때 ----------
  if(uploadFile == undefined){
    console.log("파일 선택이 취소됨");


    if(backupInput == undefined) return;
    // 1) backup한 order번째 요소를 복제

    const temp = backupInput.cloneNode(true);

    // 2) 화면에 원본 input을 temp로 바꾸기
    imageInput.after(temp); // 원본 다음에 temp 추가
    imageInput.remove(); // 원본을 화면에서 제거
    imageInput = temp; // temp를 imageInput 변수에 대입

    // 복제본은 이벤트가 복제 안되니까 다시 이벤트를 추가
    imageInput.addEventListener("change",
      changeImageFn);
    return;
  }


  // ---------- 선택된 파일의 크기가 지정된 크기를 초과하는 경우 ----------
  const maxSize = 1024 * 1024 * 5;

  if(uploadFile.size > maxSize){
    alert("5MB 이하의 이미지를 선택 해주세요");

      // 1) backup한 order번째 요소를 복제
      const temp = backupInput.cloneNode(true);

      // 2) 화면에 원본 input을 temp로 바꾸기
      imageInput.after(temp); // 원본 다음에 temp 추가
      imageInput.remove(); // 원본을 화면에서 제거
      imageInput = temp; // temp를 imageInput 변수에 대입

      // 복제본은 이벤트가 복제 안되니까 다시 이벤트를 추가
      imageInput.addEventListener("change", () =>{
        changeImageFn});
    return;
  }

  // ---------- 선택된 이미지 파일을 읽어와 미리 보기 만들기 ----------
  // JS에서 파일을 읽는 객체

  const reader = new FileReader();

  reader.readAsDataURL(uploadFile);


  reader.onload = (e) => {


    preview.setAttribute("src", reader.result);
    

    // 파일이 추가된 input을 backup 해두기
    backupInput = imageInput.cloneNode(true);
  };

}


/* 이미지 선택 버튼을 클릭하여 선택된 파일이 변햇을 때 함수 수행 */

imageInput.addEventListener("change", changeImageFn);

/* x버튼 클릭 시 */
deleteImage.addEventListener('click', () => {

//  document.querySelector("[for='img1']").innerHTML="<span>파일선택</span>";

  preview.removeAttribute("src")
  imageInput.value= "";

  backupInput.value = "";

  statusCheck = 0;
  
});

//--------------------------------------------------

  // //------------------- 프로필 이미지 변경 form 태그 제출 시 동작

  // const profileFrm = document.getElementById("profileFrm");

  // profileFrm.addEventListener("submit", e=> {

  //   let flag = true;
  //   // 1) 로그인한 회원의 프로필이 있음 -> 없음
  //   if(loginMemberProfileImg != null && statusCheck == 0  ) flag =false;

  //   // 2) 로그인한 회원의 프로필이 없음 -> 있음
  //   if(loginMemberProfileImg == null && statusCheck == 1  ) flag =false;
    
  //   // 3) 로그인한 회원의 프로필이 있음 -> 변경
  //   if(loginMemberProfileImg != null && statusCheck == 1  ) flag =false;

  //   if(flag){ // flag가 true인 경우 수행
  //       e.preventDefault(); // form 태그 제출 이벤트 막기 (제거)
  //       alert("이미지 변경 후 클릭 해주세요");
  //   }

  // });




  document.querySelector("#boardWriteFrm").addEventListener("submit",()=>{
    
    if(imageInput==null)  {
      imageInput.value=0;
      backupInput.value = 0;
    }
  })
