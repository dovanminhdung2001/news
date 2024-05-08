var newsTbl = document.getElementById('newsTbl');

var prevBtn = document.getElementById('prev')
var nextBtn = document.getElementById('next')
var pageNumInput = document.getElementById('pageNumber');
var maxPageTxt = document.getElementById('maxPage') 

var size = 10;
var page = 0;

window.addEventListener('DOMContentLoaded', (event) => { 
    requestApi(0)    
}) ;
  
function update(id) {
    saveSession('newsId', id);
    window.location.href = 'update.html';
}

function deleted(id, deleted) {  
    getAPIBody('delete', `${ROOT}/admin/news/delete?id=${id}&deleted=${deleted}`)
    .then(response => {
        window.location.href = '';
    });
}

function requestApi(page) {
    getAPIBody('get', `${ROOT}/admin/news/page?page=${page}&size=${size}`)
    .then(responseData => {
        var html = ``; 

        responseData.content.forEach(news => {
            html += `
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="flex-shrink-0">
                                <img class="rounded" src="${news.thumbnail}" alt="Avtar image" width="100">
                            </div> 
                        </div>
                    </td>
                    <td>${news.title}</td>
                    <td>${news.createdDate}</td> 
                    <td>
                        <a href="#" onclick="update(${news.id})" class="font-18 text-info me-2" data-bs-toggle="tooltip" data-bs-placement="top" title="Edit"><i class="uil uil-pen"></i></a>
                        <a href="#" onclick="deleted(${news.id}, ${news.deleted})" class="font-18 text-${news.deleted === false ? 'danger' : 'dark'}" data-bs-toggle="tooltip" data-bs-placement="top" title="${news.deleted === false ? 'Delete' : 'Restore'}"><i class="uil uil-${news.deleted === false ? 'trash' : 'redo'}"></i></a>
                    </td>
                </tr>`;
        });

        newsTbl.innerHTML += html;

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

function changePageNum() {
    newsTbl.innerHTML = '';
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
    newsTbl.innerHTML = '';
    requestApi(pageNumInput.value) 
}

function prevPage() {
    newsTbl.innerHTML = '';
    requestApi(pageNumInput.value-2)
}

function createNews() {
    window.location.href = "create-news.html";
}

function logout() { 
    window.location.href = 'logout.html';
}

 