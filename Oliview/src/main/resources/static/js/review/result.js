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


  const params = new URL(location.href).searchParams;
  const searchInput1 = params.get("searchInput")!= null ? params.get("searchInput") : "" ; // 검색어

  location.href = "/review/searchReview?searchInput=" + searchInput1
  

});


/* ===================================================================== */
/* 최신순 정렬 */


sortLatest.addEventListener("click", () => {

  sortPopular.classList.add("unclicked");
  sortLatest.classList.add("clicked");
  sortRating.classList.remove("clicked");

  const params = new URL(location.href).searchParams;
  const searchInput2 = params.get("searchInput")!= null ? params.get("searchInput") : "" ; // 검색어

  fetch("/review/sortLatest?searchInput="+searchInput2 + "&cp=1")

  .then(response => response.json())
  .then((reviewList) => {
    console.log(reviewList);

    /* 리뷰, 페이지네이션 지우기 */
    const reviewFull = document.getElementById("reviewFull");
    reviewFull.innerHTML = "";
    const reviewPagination = document.getElementById("reviewPagination");
    reviewPagination.innerHTML = "";

    
    /* 리뷰 리스트 가져오기 */
    let i = 0;
    let reviewRow;
    for(let review of reviewList){
      
      /* 행 생성 */
      if(i % 4 == 0){
        reviewRow = document.createElement("section");
        reviewRow.classList.add("review-row");
        reviewFull.append(reviewRow);
      }

      /* 리뷰 아티클 생성 */
      const reviewArticle = document.createElement("article");
      reviewArticle.classList.add("review-col");

      /* 리뷰 a태그, 이미지 태그 생성 */
      const aImg = document.createElement("a");
      const Img = document.createElement("img");
      aImg.classList.add("review-img");
      aImg.href="/review/" + review.reviewNo;
      Img.src=review.reviewImg;

      /* 가게명 생성 */
      const divTitle = document.createElement("div");
      const aTitle = document.createElement("a");
      divTitle.classList.add("reviewTitle");
      aTitle.href="/review/" + review.reviewNo;
      aTitle.innerHTML=review.reviewTitle;

      /* 닉네임,평점,작성일 생성 */
      const divNSD = document.createElement("div");
      const aNickname = document.createElement("a");
      const sectionStar = document.createElement("section");
      const spanI = document.createElement("span");
      const sectionDate = document.createElement("section");

      divNSD.classList.add("nickname-star-date");
      aNickname.classList.add("nickname");
      aNickname.innerHTML=review.memberNickname;
      aNickname.href="/review/searchReview?searchInput=" + review.memberNickname;
      sectionStar.classList.add("star");
      sectionStar.innerHTML=(review.starTotal).toFixed(1);
      spanI.innerHTML=`<i class="fa-sharp fa-solid fa-star fa-ml" style="color: #f1c93b;"></i>`;
      sectionDate.classList.add("writeDate");
      sectionDate.innerHTML=review.writeDate;



      /* 정렬! */
      divNSD.append(aNickname, sectionStar, spanI, sectionDate)
      divTitle.append(aTitle);
      aImg.append(Img);
      reviewArticle.append(aImg, divTitle, divNSD);
      reviewRow.append(reviewArticle);


      console.log("확인");

      i++;
    }


  })
  .catch(e => console.log(e));

});


/* ===================================================================== */
/* 평점순 정렬 */

sortRating.addEventListener("click", () => {

  sortPopular.classList.add("unclicked");
  sortLatest.classList.remove("clicked");
  sortRating.classList.add("clicked");

  const params = new URL(location.href).searchParams;
  const searchInput3 = params.get("searchInput") != null ? params.get("searchInput") : "" ; // 검색어

  fetch("/review/sortRating?searchInput="+searchInput3)

  .then(response => response.json())
  .then((reviewList) => {
    console.log(reviewList);

    /* 리뷰, 페이지네이션 지우기 */
    const reviewFull = document.getElementById("reviewFull");
    reviewFull.innerHTML = "";
    const reviewPagination = document.getElementById("reviewPagination");
    reviewPagination.innerHTML = "";

    let rank = 1;
    
    
    /* 리뷰 리스트 가져오기 */
    let i = 0;
    let reviewRow;

    for(let review of reviewList){

      /* 행 생성 */
      if(i % 4 == 0){
        reviewRow = document.createElement("section");
        reviewRow.classList.add("review-row");

        reviewFull.append(reviewRow);
      }

      /* 리뷰 아티클 생성 */
      const reviewArticle = document.createElement("article");
      reviewArticle.classList.add("review-col");

      /* 리뷰 a태그, 이미지 태그 생성 */
      const aImg = document.createElement("a");
      const Img = document.createElement("img");
      aImg.classList.add("review-img");
      aImg.href="/review/store/" + review.reviewTitle;
      Img.src= review.reviewImg;


      /* 랭킹 생성 */
      const divRank = document.createElement("div");
      divRank.classList.add("reviewRank")
      const sectionRankNo = document.createElement("section");
      const sectionTitle = document.createElement("section");
      sectionRankNo.innerHTML="#" + rank;
      rank++;
      sectionTitle.innerHTML = review.reviewTitle;


      /* 평점, 인원 생성 */
      const divStar = document.createElement("div");
      const sectionStar = document.createElement("section");
      const spanI = document.createElement("span");
      const sectionCount = document.createElement("section")

      divStar.classList.add("star-count");
      sectionStar.classList.add("star");
      sectionStar.innerHTML=(review.storeStarTotalRate).toFixed(1);
      spanI.innerHTML=`<i class="fa-sharp fa-solid fa-star fa-ml" style="color: #f1c93b;"></i>`;
      sectionCount.classList.add("starCount");
      sectionCount.innerHTML="(" + review.storeCount + "명)";


      /* 정렬! */
      divStar.append(sectionStar, spanI, sectionCount)
      divRank.append(sectionRankNo, sectionTitle);
      aImg.append(Img);
      reviewArticle.append(aImg, divRank, divStar);
      reviewRow.append(reviewArticle);

      console.log("확인");

      i++;

    }


  })
  .catch(e => console.log(e));
});
