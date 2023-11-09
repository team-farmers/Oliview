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
          const location= document.getElementById("locatonContent");
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
const reviewWriteFrm = document.getElementById("reviewWriteFrm");

reviewWriteFrm.addEventListener("submit", e => {

  const title = document.querySelector("[name='title']");
  const location = document.querySelector("[name='location']");
  const image = document.querySelector("[name='image']");
  const writeReview = document.querySelector("[name='writeReview']");

  
  if (title.value.trim().length == 0) {
    alert("제목을 입력 해주세요");

    e.preventDefault();
    return;
  }
  
});