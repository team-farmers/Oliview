// 도로명 주소
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            const location = document.getElementById("locatonContent");
            location.value = addr;
            location.focus();

        }
    }).open();
}

// ------------------------------------------------------------------
// 별점
const ratingStars1 = [...document.querySelectorAll(".fa-regular.fa-star.fa-2xl.star1")];
const ratingStars2 = [...document.querySelectorAll(".fa-regular.fa-star.fa-2xl.star2")];
const ratingStars3 = [...document.querySelectorAll(".fa-regular.fa-star.fa-2xl.star3")];

function executeRating(stars) {
    const starClassActive = "fa-sharp fa-solid fa-star fa-2xl star1";
    const starClassInactive = "fa-regular fa-star fa-2xl star1";

    stars.forEach((star) => {
        star.onclick = () => {
            const starIndex = stars.indexOf(star);

            for (let i = 0; i <= starIndex; i++) {
                stars[i].className = starClassActive;
            }

            for (let i = starIndex + 1; i < stars.length; i++) {
                stars[i].className = starClassInactive;
            }
        };
    });
}

executeRating(ratingStars1);
executeRating(ratingStars2);
executeRating(ratingStars3);


// ------------------------------------------------------------------
// 별점 DB에서 가지고 오기

const tasteRating = document.getElementById('tasteRating');
const quantityRating = document.getElementById('quantityRating');
const cleanlinessRating = document.getElementById('cleanlinessyRating');


const taste = document.querySelectorAll(".star1");
const amount = document.querySelectorAll(".star2");
const clean = document.querySelectorAll(".star3");

for (let i = 0; i < tasteRating.value; i++) taste[i].classList.add("fa-sharp", "fa-solid");
for (let i = 0; i < quantityRating.value; i++) amount[i].classList.add("fa-sharp", "fa-solid");
for (let i = 0; i < cleanlinessRating.value; i++) clean[i].classList.add("fa-sharp", "fa-solid");



// ------------------------------------------------------------------
// 이미지
// 미리보기 관련 요소 얻어오기
const preview = document.querySelector(".preview");
const inputImage = document.querySelector(".inputImage");
const deleteImage = document.querySelector(".delete-image");

let backupInput;


if (inputImage != null) {

    const changeImageFn = e => {

        const uploadFile = e.target.files[0];

        // --------------파일을 한 번 선택한 후 취소했을 때 -----
        if (uploadFile == undefined) {
            console.log("파일 선택이 취소됨");

            if (backupInput == undefined) return;

            // 1) backup한 요소를 복제
            const temp = backupInput.cloneNode(true);

            // 2) 화면에 원본 input을 temp로 바꾸기
            inputImage.after(temp);
            inputImage.remove();


            inputImage = temp;
            inputImage.addEventListener("change", changeImageFn);
            return;
        }


        // -------------- 선택된 파일의 크기가 지정된 크기를 초과하는 경우 ---------------
        const maxSize = 1024 * 1024 * 5; // 5MB  (byte 단위)

        if (uploadFile.size > maxSize) {
            alert("5MB 이하의 이미지만 업로드 가능합니다");

            const temp = backupInput.cloneNode(true);

            inputImage.after(temp);
            inputImage.remove();
            inputImage = temp;
            inputImage.addEventListener("change", changeImageFn);

            return;
        }



        // --------------- 선택된 이미지 파일을 읽어와 미리보기 만들기 --------------------
        const reader = new FileReader();

        reader.readAsDataURL(uploadFile)

        // 파일을 다 읽은 후
        reader.onload = e => {
            console.log(reader.result);                // 읽은 파일의 URL

            preview.setAttribute("src", reader.result);
            backupInput = inputImage.cloneNode(true);
        }

    }

    /* 이미지 선택 버튼을 클릭하여 선택된 파일이 변햇을 때 함수 수행 */
    inputImage.addEventListener("change", changeImageFn);


    //-------------------------- x버튼 클릭 시 기본 이미지로 변경 ---------
    deleteImage.addEventListener('click', () => {

        document.querySelector("[for='img1']").innerHTML = "<span>파일 선택</span>";

        preview.removeAttribute("src");
        inputImage.value = "";

        backupInput.value = "";

    });

}






// ------------------------------------------------------------------
// 유효성 검사

const reviewWriteFrm = document.querySelector("#reviewWriteFrm");
const title = document.getElementById("input-title");
const locaton = document.getElementById("locatonContent");
const menu = document.getElementById("input-menu");
const writeReview = document.getElementById("writeReview");


reviewWriteFrm.addEventListener("submit", e => {

    const tasteStars = document.querySelectorAll(".rating-option:first-of-type i.fa-sharp");
    const quantityStars = document.querySelectorAll(".rating-option:nth-of-type(2) i.fa-sharp");
    const cleanlinessStars = document.querySelectorAll(".rating-option:last-of-type i.fa-sharp");

    tasteRating.value = tasteStars.length;
    quantityRating.value = quantityStars.length;
    cleanlinessRating.value = cleanlinessStars.length;

    if (title.value.trim().length == 0) {
        e.preventDefault();
        alert("제목을 입력해주세요");
        title.focus();
        return;
    }

    if (locaton.value.trim().length == 0) {
        e.preventDefault();
        alert("위치를 입력해주세요");
        locaton.focus();
        return;
    }

    if (tasteStars.length == 0) {
        e.preventDefault();
        alert("맛을 평가해주세요");
        locaton.focus();
        return;
    }

    if (quantityStars.length == 0) {
        e.preventDefault();
        alert("양을 평가해주세요");
        locaton.focus();
        return;
    }

    if (cleanlinessStars.length == 0) {
        e.preventDefault();
        alert("청결도를 평가해주세요");
        locaton.focus();
        return;
    }

    if (menu.value.trim().length == 0) {
        e.preventDefault();
        alert("내가 먹은 메뉴를 입력해주세요");
        menu.focus();
        return;

    }

    if (inputImage.files.length == 0) {
        e.preventDefault();
        alert("이미지를 첨부해주세요");
        return;
    }

    if (writeReview.value.trim().length == 0) {
        e.preventDefault();
        alert("한줄평을 입력해주세요");
        writeReview.focus();
        return;

    }

});