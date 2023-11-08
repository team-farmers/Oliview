/* 최신순 정렬 */
const sortLatest = document.getElementById("sort-latest");
const sortPopular = document.getElementById("sort-popular");
const sortRating = document.getElementById("sort-rating");

sortLatest.addEventListener("click", () => {

  sortLatest.style.color = "#66b97e";
  sortPopular.style.color = "#d9d9d9";
  sortRating.style.color = "#d9d9d9";

  fetch("/review/searchLatest")

  .then(resp => resp.json())
  .then((reviewList) => {

    for(let review of reviewList){


      const article = document.createElement("article");

      const a = document.createElement("a");
      const img = document.createElement("img");

      const div1 = document.createElement("div");
      const a2 = document.createElement("a");

      const div2 = document.createElement("div");
      const a3 = document.createElement("a");
      const section = document.createElement("section");
      const section2 = document.createElement("section");


      a.classList.add('review-img');

      div1.classList.add('reviewTitle')
      a2.innerText = review.reviewTitle;

      div2.classList.add('nickname-star-date');
      a3.classList.add('nickname');
      section.classList.add('star');
      section.innerText = review.starTaste;
      section2.classList.add('writeDate')
      section2.innerText = review.writeDate;


      a.appendChild(img);
      div1.appendChild(a2);
      div2.appendChild(a,section,section2);

      article.append(a,div1,div2);



    }


  })
  .catch(e => console.log(e));
});