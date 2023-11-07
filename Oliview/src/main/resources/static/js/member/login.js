/* 아이디 저장 기능 쿠키 */

const getCookie = (key) => {

  const cookies = document.cookie;

  const list = cookies.split('; ').map(entry => entry.split('='));

  console.log(list);
  const obj = {};

  for(let i=0; i<list.length; i++){
    obj[ list[i][0] ] = list[i][1];
  }

  return obj[key];
}

const memberId = document.querySelector("input[name='memberId']");
const saveId = document.querySelector("input[name='saveId']");

if(memberId != null && saveId != null){
  const id = getCookie("saveId");

  if(id != undefined){
    memberId.value = id;
    saveId.checked = true;
  }
}


