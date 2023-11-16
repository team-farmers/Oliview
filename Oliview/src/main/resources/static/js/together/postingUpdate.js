
// ----------------------------------------------------------------------------------

const preview = document.querySelector(".preview"); // img 태그
let imageInput = document.querySelector("#img1"); // input 태그
const deleteImage = document.querySelector(".delete_image"); // x 버튼

//input type ="file" 태그의  값이 변경 되었을 때 변경된 상태를 백업해둘 변수
// 요소.cloneNode(true/false) : 요소복제 (true이면 하위요소도 복제)
let backupInput;


if (imageInput != null) { // #imageInput 존재할 때



  /* 이미지 변경(선택) 시 수행할 함수 */
  const changeImageFn = e => {

      const uploadFile = e.target.files[0];

      // --------------파일을 한 번 선택한 후 취소했을 때 -----
      if(uploadFile == undefined){ // 취소를 눌러서 files[0]에 파일이 없을때
          console.log("파일 선택이 취소됨");

          if(backupInput == undefined) return;

          // 1) backup한 요소를 복제
          const temp = backupInput.cloneNode(true);

          // 2) 화면에 원본 input을 temp로 바꾸기
          imageInput.after(temp); 
          imageInput.remove();
          

          imageInput =temp;
          imageInput.addEventListener("change",changeImageFn);
          return;
      }


      // -------------- 선택된 파일의 크기가 지정된 크기를 초과하는 경우 ---------------
      const maxSize = 1024 * 1024 * 5; // 5MB  (byte 단위)

      if (uploadFile.size > maxSize) {
        alert("5MB 이하의 이미지만 업로드 가능합니다");

        // 1) backup한 요소를 복제
        const temp = backupInput.cloneNode(true);

        // 2) 화면에 원본 input을 temp로 바꾸기

        imageInput.after(temp); 

        imageInput.remove();
        

        imageInput =temp;
        imageInput.addEventListener("change",changeImageFn);

        return;
      }

      

      // --------------- 선택된 이미지 파일을 읽어와 미리보기 만들기 --------------------

      // JS에서 파일을 읽는 객체
      //-> 파일을 읽고 클라이언트 컴퓨터에 파일을 저장할 수 있음
      const reader = new FileReader();

      // 매개변수에 작성된 파일을 읽어서
      // 파일을 나타내는 URL 형태로 변경
      // -> FileReader.result 필드에 저장되어 있음
      reader.readAsDataURL(uploadFile)

      // 파일을 다 읽은 후
      reader.onload = e => {
          // console.log(reader.result);                // 읽은 파일의 URL

          // img 태그의 src 속성의 속성 값으로
          // 읽은 파일의 url을 대입

          preview.setAttribute("src", reader.result);

          // 파일이 추가된 input을 backup 해두기
          backupInput = imageInput.cloneNode(true);

      };

  }

  /* 이미지 선택 버튼을 클릭하여 선택된 파일이 변햇을 때 함수 수행 */

  // change 이벤트 : input의 이전 값과 현재 값이 다를 때 발생
  imageInput.addEventListener("change", changeImageFn);


  //-------------------------- x버튼 클릭 시 기본 이미지로 변경 ---------
  // 1 ) 미리보기 -> 기본이미지 변경
  // 2) input태그에 value 값을 빈칸으로 변경
  deleteImage.addEventListener('click' , () => {
    
    document.querySelector("[for='img1']").innerHTML = "<span>파일 선택</span>";

    preview.removeAttribute("src");
    imageInput.value="";

    backupInput.value = "";

    statusCheck = 0; // 있었는데 없어짐
  });

}
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
