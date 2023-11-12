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
const reviewWriteFrm = document.querySelector("#reviewWriteFrm");

const title = document.getElementById("input-title");
const locaton = document.getElementById("locatonContent");
const menu = document.getElementById("input-menu");
const writeReview = document.getElementById("writeReview");
const image = document.getElementById("image");


const tasteRating = document.getElementById('tasteRating');
const quantityRating = document.getElementById('quantityRating');
const cleanlinessRating = document.getElementById('cleanlinessyRating');






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
        alert("위치을 입력해주세요");
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

    if (image.files.length == 0) {
        e.preventDefault();
        alert("이미지를 첨부해주세요");
        menu.focus();
        return;
    }

    if (writeReview.value.trim().length == 0) {
        e.preventDefault();
        alert("한줄평을 입력해주세요");
        writeReview.focus();
        return;

    }

});