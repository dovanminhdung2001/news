var titleInput = document.getElementById('titleInput');
var thumbnailInput = document.getElementById('thumbnailInput');
var thumbnailImg = document.getElementById('thumbnailImg');
var editor = document.querySelector('#snow-editor .ql-editor');
var toast = document.getElementById('toast');

var thumbnailSrc = null;

window.addEventListener('DOMContentLoaded', (event) => { 
    thumbnailImg.src = defaultImg64;
})


function create() {   
    if (titleInput.value.trim() === '')
        showToast('Empty Title');
    else if (editor.innerText.trim() === '')
        showToast('Emtpy Content');
    else { 
        var payload = {
            title: titleInput.value.trim(),
            thumbnail: thumbnailImg.src,
            contentHtml: editor.innerHTML,  
            contentText: editor.innerText.trim(), 
        } ;
        console.log(`Create news: `);
        console.log(payload);

        getAPIBody('post', `${ROOT}/admin/news/create`, payload)
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
        // getSession('avatarSrc');
        // console.log('cancel input img');
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