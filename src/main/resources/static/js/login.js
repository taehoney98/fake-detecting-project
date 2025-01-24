function handleLoginSubmit(event) {
    event.preventDefault(); // 폼 제출 기본 동작 방지

    const formData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    // 로그인 요청을 POST 방식으로 보냄
    fetch('/login', {
        method: 'POST',  // 요청 방식은 'POST'
        headers: {
            'Content-Type': 'application/json',  // 서버가 JSON을 처리하도록
        },
        body: JSON.stringify(formData), // JSON 형식으로 데이터 전송
    })
        .then(response => {
            if (response.ok) {
                // 서버에서 Authorization 헤더에서 JWT를 가져옴
                const token = response.headers.get('Authorization');
                if (token) {
                    // JWT를 localStorage에 저장
                    localStorage.setItem('token', token.replace('Bearer ', ''));  // 'Bearer ' 접두어 제거하고 저장

                    alert('로그인 성공!');
                    // 로그인 성공 후 /home 페이지로 이동
                    window.location.href = '/';  // 로그인 성공 후 이동할 페이지
                } else {
                    alert('로그인 실패: 토큰이 반환되지 않았습니다.');
                }
            } else {
                return response.json().then(data => {
                    alert('로그인 실패: ' + data.message);
                });
            }
        })
        .catch(error => {
            console.error('에러 발생:', error);
            alert('서버와의 통신에 문제가 발생했습니다.');
        });
}