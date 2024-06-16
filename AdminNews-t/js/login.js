var email = document.getElementById('email')
var password = document.getElementById('password')

function login() {
    if(email.value.trim() === '')
        showToast('Blank Email');
    else if (password.value.trim() === '')
        showToast('Blank Password');
    else {
        fetch(`${ROOT}/login/admin?phone=${email.value.trim()}&password=${password.value.trim()}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                showToast('Wrong password or username');
            }
        })
        .then(body => {
            console.log(body);
            if (body.data.role.id !== 1) {
                showToast('Please use admin account');
            } else {
                saveSession('accessToken', body.accessToken);
                saveSession('refreshToken', body.refreshToken);
                saveSession('adminId', body.data.id);
                window.location.href = 'manage-news.html';
            }
        })
        .catch(error => {
            console.log(error);
            showToast(error.message);
        });
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