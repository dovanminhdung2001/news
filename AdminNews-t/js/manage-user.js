var userTbl = document.getElementById('usertbl');

var prevBtn = document.getElementById('prev')
var nextBtn = document.getElementById('next')
var pageNumInput = document.getElementById('pageNumber');
var maxPageTxt = document.getElementById('maxPage') 

var size = 10;
var page = 0;

window.addEventListener('DOMContentLoaded', (event) => { 
    requestApi(0)    
}) ;

function requestApi(page) {
    getAPIBody('get', `${ROOT}/admin/user/page?page=${page}&size=${size}`)
    .then(responseData => {
        var html = ``; 

        responseData.content.forEach(user => {
            console.log(user);
            html += `
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="flex-shrink-0">
                                <img class="rounded-circle" src="${user.avatar == null ? 'img/defaultavt.jpg' : user.avatar}" alt="Avtar image" width="31">
                            </div>
                            <div class="flex-grow-1 ms-2">
                                ${user.name}
                            </div>
                        </div>
                    </td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>
                        <a href="#" onclick="deleted(${user.id}, ${user.isActive});" class="font-18 text-${user.isActive !== false ? 'danger' : 'dark'}" data-bs-toggle="tooltip" data-bs-placement="top" title="${user.isActive !== false ? 'Delete' : 'Restore'}"><i class="uil uil-${user.isActive !== false ? 'trash' : 'redo'}"></i></a>
                    </td>
                </tr> `; 
        });

        userTbl.innerHTML += html;

        maxPageTxt.innerText = `/ ${responseData.totalPages}`
        pageNumInput.value = 1 + parseInt(`${responseData.number}`)
        if (responseData.number === 0) {
            prevBtn.removeEventListener("click", prevPage);
            prevBtn.style.backgroundColor = '#565656';
        } else {
            prevBtn.addEventListener("click", prevPage);
            prevBtn.style.backgroundColor = '#007bff';
        }

        if (responseData.number + 1 === responseData.totalPages) {
            nextBtn.removeEventListener("click", nextPage);
            nextBtn.style.backgroundColor = '#565656';
        } else {
            nextBtn.addEventListener("click", nextPage);
            nextBtn.style.backgroundColor = '#007bff';
        }
    })
}

function deleted(id, isActive) {  
    getAPIBody('delete', `${ROOT}/admin/user/delete?id=${id}&isActive=${isActive}`)
    .then(response => {
        window.location.href = '';
    });
}

function changePageNum() {
    userTbl.innerHTML = '';
    if (pageNumInput.value < 0) 
        pageNumInput.value = 0 ;
    else {
        var maxPage = maxPageTxt.innerHTML.match(/\d+/)[0];
        if (pageNumInput.value > maxPage)
            pageNumInput.value = maxPage;
    }
  
    requestApi(pageNumInput.value-1);
} 

function nextPage() {
    userTbl.innerHTML = '';
    requestApi(pageNumInput.value) 
}

function prevPage() {
    userTbl.innerHTML = '';
    requestApi(pageNumInput.value-2)
}

function logout() { 
    window.location.href = 'logout.html';
}