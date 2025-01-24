window.onload = function () {
    // 디스플레이 배율 값 얻기
    const devicePixelRatio = window.devicePixelRatio;

    // 배율에 따라 scale 값 계산 (예: 125% 배율은 0.8, 100% 배율은 1.0)
    let scaleValue = 1.0;  // 기본 값 1.0
    if (devicePixelRatio > 1) {
        scaleValue = 0.8;  // 배율이 100% 이상일 때 0.8로 축소
    }

    // 화면 축소 적용
    document.body.style.transform = `scale(${scaleValue})`;  // 배율에 맞게 축소
    document.body.style.transformOrigin = "0 0";  // 축소 기준을 왼쪽 상단으로 설정

    // 화면 크기 설정 (1920x1080)
    document.body.style.width = "1920px";
    document.body.style.height = "1080px";

    const token = localStorage.getItem('token'); // localStorage에서 token 확인

    const loginLink = document.getElementById('loginLink');
    const signupLink = document.getElementById('signupLink');
    const logoutLink = document.getElementById('logoutLink');

    if (token) {
        // 토큰이 있으면 로그인/회원가입 링크 숨기고 로그아웃 링크 보임
        loginLink.style.display = 'none';
        signupLink.style.display = 'none';
        logoutLink.style.display = 'flex';
    } else {
        // 토큰이 없으면 로그인/회원가입 링크 보임
        loginLink.style.display = 'flex';
        signupLink.style.display = 'flex';
        logoutLink.style.display = 'none';
    }
};