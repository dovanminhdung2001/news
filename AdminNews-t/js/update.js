var titleInput = document.getElementById('titleInput');
var thumbnailInput = document.getElementById('thumbnailInput');
var thumbnailImg = document.getElementById('thumbnailImg');
var editor = document.querySelector('#snow-editor .ql-editor');
var toast = document.getElementById('toast');
var hashtagInput = document.getElementById('hashtagInput');


window.addEventListener('DOMContentLoaded', (event) => {
    var id = getSession('newsId');

    getAPIBody('get', `${ROOT}/admin/news/get?id=${id}`)
    .then(responseData => { 
        titleInput.value = responseData.title;
        thumbnailImg.src = responseData.thumbnail;
        editor.innerHTML = responseData.contentHtml;
        
        
        if (responseData.hashtags.length) {
            var hashtagTxt = ''; 
            responseData.hashtags.forEach(hashtag => { 
                hashtagTxt += hashtag.name + ', '; 
            }); 
            hashtagTxt = hashtagTxt.substring(0, hashtagTxt.length - 2);
        
            console.log(hashtagTxt); // Kiểm tra xem chuỗi đã chuẩn chưa
            
            hashtagInput.value = hashtagTxt;
        }
    });
})


function create() {
    if (titleInput.value.trim() === '')
        showToast('Empty Title');
    else if (editor.innerText.trim() === '')
        showToast('Emtpy Content');
    else {
        var hashtagArr = [];
        var hashtagStr = hashtagInput.value.trim().replace(/[^a-zA-Z0-9À-ỹ ,_]/g, '');;

        if (hashtagStr !== '')  
            hashtagArr = hashtagStr.toLowerCase().replace(/\s+/g, '').split(',');  

        var payload = {
            id: getSession('newsId'),
            title: titleInput.value.trim(),
            thumbnail: thumbnailImg.src,
            contentHtml: editor.innerHTML,
            contentText: editor.innerText.trim(),
            hashtagListStr: hashtagArr
        } ;
        console.log(`Create news: `);
        console.log(payload);

        getAPIBody('put', `${ROOT}/admin/news/update`, payload)
        .then(responseData => {
            window.location.href = "/manage-news.html";
        })
        .catch(function(error) {
            // toast.innerText = error.response.message;

            showToast(error.response.message);
            // alert(error.response.message);
        });
    }
}



function handleFileSelect(event) {
    var input = event.target;
    if (input.files && input.files[0]) {
        var file = input.files[0];
        var reader = new FileReader();
        if (file && file.type.startsWith('image/')) {
            if (file.size <= 5 * 1024 * 1024) {

                reader.onload = function(event) {
                    thumbnailSrc = event.target.result;
                    thumbnailImg.src = thumbnailSrc;
                    console.log("Encoded image:", thumbnailSrc);
                };

                reader.readAsDataURL(file);
            } else {
                showToast('File size should be less than or equal to 5MB');
            }
        } else {
            showToast('Please select a valid image file');
        }
    } else {
        thumbnailImg.src = defaultImg64;
    }
}


function showToast(message) {
    toast.innerHTML = `<div class="toast-content">${message}</div>`
    toast.style.display = 'block';
    var header = document.createElement('div');
    header.classList.add('toast-header');
    toast.insertBefore(header, toast.firstChild);
    setTimeout(function() {
      toast.style.display = 'none';
      toast.removeChild(header);
    }, 3000); // 3 seconds
}


function logout() {
    window.location.href = 'logout.html';
}