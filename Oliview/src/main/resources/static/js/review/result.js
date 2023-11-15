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




/* ===================================================================== */
/* 인기순 정렬 (기본) */

const sortPopular = document.getElementById("sort-popular");
const sortLatest = document.getElementById("sort-latest");
const sortRating = document.getElementById("sort-rating");


const sortPopularFn = (cp) => {


  const params = new URL(location.href).searchParams;
  const searchInput1 = params.get("searchInput")!= null ? params.get("searchInput") : "" ; // 검색어
  
  let param = {"searchInput" : searchInput1, "cp":cp};
  let query = Object.keys(param).map(k => encodeURIComponent(k) + '=' + encodeURIComponent(param[k])).join('&');

  fetch("/review/sortPopular?" + query)

  .then(response => response.json())
  .then((map) => {

    console.log(map);
    history.pushState(null, null, 'searchReview?searchInput='+searchInput1+'&cp='+cp+'&sort=1');

    /* 리뷰, 페이지네이션 지우기 */
    const reviewFull = document.getElementById("reviewFull");
    reviewFull.innerHTML = "";
    const pagination = document.querySelector(".pagination");
    pagination.innerHTML = "";
    
    /* 리뷰 리스트 가져오기 */
    let i = 0;
    let reviewRow;
    for(let review of map.reviewList){
      
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
      aImg.href="/review/" + review.reviewNo + "?searchInput=" + searchInput1 + "&cp=" + cp + "&sort=1";
      Img.src=review.reviewImg;

      /* 가게명 생성 */
      const divTitle = document.createElement("div");
      const aTitle = document.createElement("a");
      divTitle.classList.add("reviewTitle");
      aTitle.href="/review/" + review.reviewNo + "?searchInput=" + searchInput1 + "&cp=" + cp + "&sort=1";
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

    // 페이지네이션
    const pg = map.pagination;

    // 조회 결과 없을 경우 페이지네이션 생성 X
    if(map.reviewList.length == 0) return;


    // 맨 앞(1페이지)
    const li1 = document.createElement("li");
    const a1 = document.createElement("a");
    // a1.setAttribute("onclick", "sortLatestFn(1)");
    a1.addEventListener("click", () => {
      sortLatestFn(1);
    });
    a1.innerHTML = "&lt;&lt;";
    li1.append(a1);


    // 이전 페이지 목록
    const li2 = document.createElement("li");
    const a2 = document.createElement("a");
    a2.innerHTML = "&lt;";
    a2.addEventListener("click", () => {
      sortLatestFn(pg.prevPage);
    });
    li2.append(a2);
    
    pagination.append(li1, li2);


    for(let i = pg.startPage; i<=pg.endPage; i++){
      const li = document.createElement("li");
      const a = document.createElement("a");
      a.addEventListener("click", () => {
        sortPopularFn(i);
      });
      a.innerHTML = i;

      if(i == pg.currentPage){
        a.classList.add("current");
      }

      li.append(a);
      pagination.append(li);
    }

    // 다음 페이지 목록
    const li3 = document.createElement("li");
    const a3 = document.createElement("a");
    a3.addEventListener("click", () => {
      sortPopularFn(pg.nextPage);
    });
    a3.innerHTML = "&gt;";
    li3.append(a3);


    // 맨 뒤(마지막 페이지)
    const li4 = document.createElement("li");
    const a4 = document.createElement("a");
    // a1.setAttribute("onclick", "sortPopularFn(1)");
    a4.addEventListener("click", () => {
      sortPopularFn(pg.maxPage)
    });
    a4.innerHTML = "&gt;&gt;";
    li4.append(a4);

    pagination.append(li3, li4);


  })
  .catch(e => console.log(e));

}

sortPopular.addEventListener("click", () => {

  sortPopular.classList.add("clicked");
  sortLatest.classList.remove("clicked");
  sortRating.classList.remove("clicked");

  sortPopularFn(1);




});




/* ===================================================================== */
/* 최신순 정렬 */


const sortLatestFn = (cp) => {
  const params = new URL(location.href).searchParams;
  const searchInput2 = params.get("searchInput")!= null ? params.get("searchInput") : "" ; // 검색어
  
  let param = {"searchInput" : searchInput2, "cp":cp};
  let query = Object.keys(param).map(k => encodeURIComponent(k) + '=' + encodeURIComponent(param[k])).join('&');

  fetch("/review/sortLatest?" + query)

  .then(response => response.json())
  .then((map) => {

    console.log(map);
    history.pushState(null, null, 'searchReview?searchInput='+searchInput2+'&cp='+cp+'&sort=2');

    /* 리뷰, 페이지네이션 지우기 */
    const reviewFull = document.getElementById("reviewFull");
    reviewFull.innerHTML = "";
    const pagination = document.querySelector(".pagination");
    pagination.innerHTML = "";
    
    /* 리뷰 리스트 가져오기 */
    let i = 0;
    let reviewRow;
    for(let review of map.reviewList){
      
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
      aImg.href="/review/" + review.reviewNo + "?searchInput=" + searchInput2 + "&cp=" + cp + "&sort=2";
      Img.src=review.reviewImg;

      /* 가게명 생성 */
      const divTitle = document.createElement("div");
      const aTitle = document.createElement("a");
      divTitle.classList.add("reviewTitle");
      aTitle.href="/review/" + review.reviewNo + "?searchInput=" + searchInput2 + "&cp=" + cp + "&sort=2";
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

    // 페이지네이션
    const pg = map.pagination;

    // 조회 결과 없을 경우 페이지네이션 생성 X
    if(map.reviewList.length == 0) return;


    // 맨 앞(1페이지)
    const li1 = document.createElement("li");
    const a1 = document.createElement("a");
    // a1.setAttribute("onclick", "sortLatestFn(1)");
    a1.addEventListener("click", () => {
      sortLatestFn(1);
    });
    a1.innerHTML = "&lt;&lt;";
    li1.append(a1);


    // 이전 페이지 목록
    const li2 = document.createElement("li");
    const a2 = document.createElement("a");
    a2.innerHTML = "&lt;";
    a2.addEventListener("click", () => {
      sortLatestFn(pg.prevPage);
    });
    li2.append(a2);
    
    pagination.append(li1, li2);


    for(let i = pg.startPage; i<=pg.endPage; i++){
      const li = document.createElement("li");
      const a = document.createElement("a");
      a.addEventListener("click", () => {
        sortLatestFn(i);
      });
      a.innerHTML = i;

      if(i == pg.currentPage){
        a.classList.add("current");
      }

      li.append(a);
      pagination.append(li);
    }

    // 다음 페이지 목록
    const li3 = document.createElement("li");
    const a3 = document.createElement("a");
    a3.addEventListener("click", () => {
      sortLatestFn(pg.nextPage);
    });
    a3.innerHTML = "&gt;";
    li3.append(a3);


    // 맨 뒤(마지막 페이지)
    const li4 = document.createElement("li");
    const a4 = document.createElement("a");
    // a1.setAttribute("onclick", "sortLatestFn(1)");
    a4.addEventListener("click", () => {
      sortLatestFn(pg.maxPage);
    });
    a4.innerHTML = "&gt;&gt;";
    li4.append(a4);

    pagination.append(li3, li4);

  })
  .catch(e => console.log(e));

}


sortLatest.addEventListener("click", () => {

  sortPopular.classList.remove("clicked");
  sortLatest.classList.add("clicked");
  sortRating.classList.remove("clicked");

  sortLatestFn(1);




});


/* ===================================================================== */
/* 평점순 정렬 */

const sortRatingFn = (cp) => {
  const params = new URL(location.href).searchParams;
  const searchInput3 = params.get("searchInput") != null ? params.get("searchInput") : "" ; // 검색어
  let rank = 8*cp - 7;


  let param = {"searchInput" : searchInput3, "cp":cp};
  let query = Object.keys(param).map(k => encodeURIComponent(k) + '=' + encodeURIComponent(param[k])).join('&');

  fetch("/review/sortRating?"+query)
  
  .then(response => response.json())
  .then((map) => {
    console.log(map);
    history.pushState(null, null, 'searchReview?searchInput='+searchInput3+'&cp='+cp+'&sort=3');
  
    /* 리뷰, 페이지네이션 지우기 */
    const reviewFull = document.getElementById("reviewFull");
    reviewFull.innerHTML = "";
    const pagination = document.querySelector(".pagination");
    pagination.innerHTML = "";
  
    
    /* 리뷰 리스트 가져오기 */
    let i = 0;
    let reviewRow;
  
    for(let review of map.reviewList){
  
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
      const spanRankNo = document.createElement("span");
      const sectionRankNo = document.createElement("section");
      const sectionTitle = document.createElement("section");
      sectionRankNo.classList.add("rankNo");
      sectionTitle.classList.add("reviewTitle");
      spanRankNo.innerHTML="#";
      sectionRankNo.innerHTML=rank;
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
      divRank.append(spanRankNo,sectionRankNo, sectionTitle);
      aImg.append(Img);
      reviewArticle.append(aImg, divRank, divStar);
      reviewRow.append(reviewArticle);
  
      console.log("확인");
  
      i++;
  
    }

    // 페이지네이션
    const pg = map.pagination;

    // 조회 결과 없을 경우 페이지네이션 생성 X
    if(map.reviewList.length == 0) return;


    // 맨 앞(1페이지)
    const li1 = document.createElement("li");
    const a1 = document.createElement("a");
    // a1.setAttribute("onclick", "sortLatestFn(1)");
    a1.addEventListener("click", () => {
      sortRatingFn(1);
    });
    a1.innerHTML = "&lt;&lt;";
    li1.append(a1);


    // 이전 페이지 목록
    const li2 = document.createElement("li");
    const a2 = document.createElement("a");
    a2.innerHTML = "&lt;";
    a2.addEventListener("click", () => {
      sortRatingFn(pg.prevPage);
    });
    li2.append(a2);
    
    pagination.append(li1, li2);


    for(let i = pg.startPage; i<=pg.endPage; i++){
      const li = document.createElement("li");
      const a = document.createElement("a");
      a.addEventListener("click", () => {
        sortRatingFn(i);


      });
      a.innerHTML = i;

      if(i == pg.currentPage){
        a.classList.add("current");
      }

      li.append(a);
      pagination.append(li);
    }

    // 다음 페이지 목록
    const li3 = document.createElement("li");
    const a3 = document.createElement("a");
    a3.addEventListener("click", () => {
      sortRatingFn(pg.nextPage);
    });
    a3.innerHTML = "&gt;";
    li3.append(a3);


    // 맨 뒤(마지막 페이지)
    const li4 = document.createElement("li");
    const a4 = document.createElement("a");
    // a1.setAttribute("onclick", "sortLatestFn(1)");
    a4.addEventListener("click", () => {
      sortRatingFn(pg.maxPage)
    });
    a4.innerHTML = "&gt;&gt;";
    li4.append(a4);

    pagination.append(li3, li4);
  
  
  
  
  })
  .catch(e => console.log(e));
}



sortRating.addEventListener("click", () => {

  sortPopular.classList.remove("clicked");
  sortLatest.classList.remove("clicked");
  sortRating.classList.add("clicked");

  sortRatingFn(1);

});

