/****** 약관동의 체크 ******/
const agreeForm = document.getElementById("agreeForm"); // 폼
const agreeAll = document.getElementById("agreeAll"); // 전체동의 체크박스
const agreeCheckBoxs = document.querySelectorAll(".agree-check-boxs")// 전체동의를 제외한 3개의 체크박스
const nextBtn = document.querySelector(".next-btn"); // 다음 버튼

const agreeSite = document.getElementById("agreeSite");// 필수_사이트이용약관
const agreePersonal = document.getElementById("agreePersonal"); // 필수_개인정보 이용약관

const agreements = {
    agreeSite : false, // 필수_사이트이용약관
    agreePersonal : false, // 필수_개인정보 이용약관
    agreeChoice : false // 선택_개인정보 수집
}


// 전체동의 클릭 시
agreeAll.addEventListener("change", ()=>{

    // 전체동의가 선택되어있다면 _ 유효성검사, 체크박스 표시
    if(agreeAll.checked){
        agreements.agreeSite = true;
        agreements.agreePersonal = true;
        agreements.agreeChoice = true;

        agreeSite.checked = true;
        agreePersonal.checked = true;
        agreeChoice.checked = true;

    // 전체동의가 해제되어있다면_ 전체 체크박스 해제
    } else {
        agreements.agreeSite = false;
        agreements.agreePersonal = false;
        agreements.agreeChoice = false;

        agreeSite.checked = false;
        agreePersonal.checked = false;
        agreeChoice.checked = false;
    }
})


// 사이트 이용약관 체크 확인
agreeSite.addEventListener("change", ()=>{

    // 사이트 이용약관 동의 체크시
    if(agreeSite.checked){
        agreements.agreeSite = true;
    } else {
        agreements.agreeSite = false;
        agreeAll.checked = false;
    }
})

// 개인정보 이용약관 체크 확인
agreePersonal.addEventListener("change", ()=>{

    // 개인정보 이용약관 동의 체크시
    if(agreePersonal.checked){
        agreements.agreePersonal = true;
    } else {
        agreements.agreePersonal = false;
        agreeAll.checked = false;
    }
})


// 제출버튼 유효성검사
agreeForm.addEventListener("submit", e=> {
    
    // 필수 동의사항이 체크되지 않으면 새로고침(submit) 되는 것 막음
    if(agreements.agreeSite == false || agreements.agreePersonal == false){
        alert("약관에 동의해주세요.");
        e.preventDefault();
        return;
    }
}); 



// agreeAll.addEventListener("click", e=>{

//     const checked = e.target.checked;

//     if(checked){
//         agreeCheckBoxs.forEach((item) => {
//             item.checked = true;
//             agreements[item.id] = true;
//             item.parentNode.classList.add('active');
//         });

//     } else {
//         agreeCheckBoxs.forEach((item)=>{
//             item.checked = false;
//             agreements[item.id] = false;
//             item.parentNode.classList.remove('active');
//         });
//     }
    
// })


// // 동의사항 중 하나라도 빠지면 체크아웃됨
// agreeCheckBoxs.forEach((item) => item.addEventListener("input", toggleAgreeCheckBoxs));


// function agreeAllStatus(){
//     const {agreeSite, agreePersonal, agreeChoice } = agreements;
//     if(agreeSite && agreePersonal && agreeChoice) { // 모두 선택되면 전체동의
//         agreeAll.checked = true;
//     } else if(agreeSite && agreePersonal){
//         agree
//     }
    
//     else{
//         agreeAll.checked = false;
//     }
// }


// function toggleAgreeCheckBoxs(e){
//     const { checked, id } = e.target;
//     agreements[id] = checked;
//     this.parentNode.classList.toggle('active');
//     agreeAllStatus();
// }





