/* 댓글 목록 조회 */
const selectCommentList = () =>{

  fetch("/comment?reviewNo="+reviewNo)
  .then(response=>response.json())
  .then(cList=>{

    const commentList = document.getElementById("commentList");
    commentList.innerHTML ="";


    for(let comment of cList){
      
      // 댓글 행
      const commentRow = document.createElement("li");
      commentRow.classList.add("comment-row");

      // 답글인 경우 child-comment 클래스 추가
      if(comment.parentNo != 0) commentRow.classList.add("child-comment");

      // 삭제된 댓글이지만 자식 댓글때문에 조회된 경우
      if(comment.commentDelFl == 'Y') commentRow.innerText = "삭제된 댓글입니다";

      // 삭제되지 않은 댓글인 경우(댓글, 버튼 생성)
      else{

        // 프로필
        const commentWriter = document.createElement("p");
        commentWriter.classList.add("comment-writer");

        // 프로필 이미지
        const memberProfile = document.createElement("img");

        if(comment.memberProfile != null){ // 프로필 이미지 o
          memberProfile.setAttribute("src", comment.memberProfile);
        }
        else{ // 프로필 이미지 x
          memberProfile.setAttribute("src", userDefaultImage);
        }

        // 작성자 닉네임
        const memberNickname = document.createElement("span");
        memberNickname.innerText = comment.memberNickname;

        // 작성일
        const commentWriteDate = document.createElement("span");
        commentWriteDate.classList.add("comment-date");
        commentWriteDate.innerText =   comment.commentWriteDate;

        // 작성자 영역(p)에 프로필,닉네임,작성일 마지막 자식으로(append) 추가
        commentWriter.append(memberProfile , memberNickname , commentWriteDate);

        // 댓글 내용
        const commentContent = document.createElement("p");
        commentContent.classList.add("comment-content");
        commentContent.innerHTML = comment.commentContent;

        // 행에 작성자, 내용 추가
        commentRow.append(commentWriter, commentContent);

    
        // 로그인이 되어있는 경우 답글 버튼 추가
        if(loginCheck){
            // 버튼 영역
            const commentBtnArea = document.createElement("div");
            commentBtnArea.classList.add("comment-btn-area");

            // 답글 버튼
            const childCommentBtn = document.createElement("button");
            childCommentBtn.setAttribute("onclick", "showInsertComment("+comment.commentNo+", this)");
            
            childCommentBtn.innerText = "답글";

            // 버튼 영역에 답글 버튼 추가
            commentBtnArea.append(childCommentBtn);

            // 로그인한 회원번호와 댓글 작성자의 회원번호가 같을 때만 버튼 추가
            if(loginMemberNo == comment.memberNo){

                // 수정 버튼
                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";

                // 수정 버튼에 onclick 이벤트 속성 추가
                updateBtn.setAttribute("onclick", "showUpdateComment("+comment.commentNo+", this)");                        


                // 삭제 버튼
                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제";
                // 삭제 버튼에 onclick 이벤트 속성 추가
                deleteBtn.setAttribute("onclick", "deleteComment("+comment.commentNo+")");                       


                // 버튼 영역 마지막 자식으로 수정/삭제 버튼 추가
                commentBtnArea.append(updateBtn, deleteBtn);

            } // if 끝
            

            // 행에 버튼영역 추가
            commentRow.append(commentBtnArea); 
        }

      }

      // 댓글 목록(ul)에 행(li)추가
      commentList.append(commentRow);

    }
  })
  .catch(err => console.log(err));
}