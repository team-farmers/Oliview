/* ===================================================================== */
/* 검색창에 이전 검색 기록 남겨두기 */
const searchInput = document.getElementById("searchInput");

// 즉시 실행 함수 (해석되자마자 실행되는 함수, 속도 빠름)
(()=>{
  // 주소에 있는 파라미터(쿼리스트링)을 얻어오기
  const params = new URL(location.href).searchParams;

  const searchWord = params.get("searchInput"); // 검색어

  // 검색을 했을 경우
  if(searchWord !=null){
    searchInput.value = searchWord; // 검색어를 input에 추가

  }
})();

/* ===================================================================== */
/* 인기순 정렬 (기본) */

const sortLatest = document.getElementById("sort-latest");
const sortPopular = document.getElementById("sort-popular");
const sortRating = document.getElementById("sort-rating");



sortPopular.addEventListener("click", () => {

  sortPopular.classList.add("clicked");
  sortLatest.classList.remove("clicked");
  sortRating.classList.remove("clicked");


});


/* ===================================================================== */
/* 최신순 정렬 */


sortLatest.addEventListener("click", () => {

  sortPopular.classList.remove("clicked");
  sortLatest.classList.add("clicked");
  sortRating.classList.remove("clicked");

});


/* ===================================================================== */
/* 평점순 정렬 */

sortRating.addEventListener("click", () => {

  sortPopular.classList.remove("clicked");
  sortLatest.classList.remove("clicked");
  sortRating.classList.add("clicked");

  
  location.href="searchReview?searchInput=" + params.get("searchInput");
});
