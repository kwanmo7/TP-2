// 썸네일
  function initSlider() {
  const sliders = document.querySelectorAll('.slider');
  sliders.forEach(slider => {
    const slides = slider.querySelector('.slides');
    const slideCount = slides.children.length;
    let index = 0;

    // 다음 슬라이드로 넘기기
    function nextSlide() {
      index = (index + 1) % slideCount;
      slides.style.transform = `translateX(-${index * 100}%)`;
    }

    // 5초마다 자동으로 슬라이드 넘기기
    setInterval(nextSlide, 5000);
  });
}

window.onload = function() {
  initSlider();
};


// 슬라이드 화면 (코드 통일성을 위해 바닐라 js로 변경함)
document.addEventListener('DOMContentLoaded', function() {
  const sliders = document.querySelectorAll('.slider');

  sliders.forEach(function(slider) {
    const slides = slider.querySelector('.slides');
    const slideCount = slides.children.length;
    let index = 0;

    setInterval(function() {
      index = (index + 1) % slideCount;
      slides.style.transform = `translateX(-${index * 100}%)`;
    }, 5000);
  });
});



// 후기 게시판 목록
// 카드 컨테이너에 대한 클릭 이벤트 리스너
document.querySelectorAll(".card-deck .card").forEach(card => {
  card.addEventListener("click", function(event) {
    // 이벤트가 발생한 위치에서 가장 가까운 a 태그를 찾기
    const link = card.querySelector("a");

    // a 태그가 있고, 이벤트가 a 태그 자체에서 발생하지 않았을 경우에만 동작
    if (link && event.target !== link) {
        event.preventDefault(); // 기본 이벤트 중지
        window.location.href = link.href; // 해당 링크로 페이지 이동
    }
  });
});

// 정보공유, 자유 게시판 목록
// 모든 행을 선택하여 클릭 이벤트를 추가
    document.querySelectorAll("table").forEach(table => {
      table.addEventListener("click", function(event) {
        let target = event.target;

        // 클릭된 요소에서 가장 가까운 tr 요소를 찾기
        while (target && target.nodeName !== "TR") {
          target = target.parentNode;
        }

        // tr 요소 내부에 있는 a 태그를 찾기
        if (target) {
          const link = target.querySelector("a");
          if (link && link.href) {
            event.preventDefault(); // 기본 이벤트 중지
            window.location.href = link.href; // 해당 링크로 페이지 이동
          }
        }
      });
    });


// 검색
    // 페이지가 로드될 때 저장된 검색어가 있는지 확인 -> 있으면 검색창에 남겨두기
  window.addEventListener('DOMContentLoaded', (event) => {
    initSlider();
    console.log("검색1")
    var savedKeyword = localStorage.getItem('searchKeyword');
    if (savedKeyword) {
      document.getElementById('search-input').value = savedKeyword;
      // 검색어를 가져왔으면 -> 다시 로컬 스토리지에서 삭제
      console.log("검색2")
      localStorage.removeItem('searchKeyword');
    }
  });


  // 검색 버튼 클릭 시 검색어를 로컬 스토리지에 저장
  document.getElementById('search-button').addEventListener('click', function(event) {
    event.preventDefault();
    var keyword = document.getElementById('search-input').value;
    localStorage.setItem('searchKeyword', keyword);
    document.getElementById('search-form').submit();
        console.log("검색3")
  });


// 상단으로 올라가기
let scrollToTopBtn = document.getElementById("scrollToTopBtn");

// 20px 내려가면 버튼이 보이도록 하기
window.onscroll = function() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    scrollToTopBtn.style.display = "block";
  } else {
    scrollToTopBtn.style.display = "none";
  }
};

// 상단으로 올리기
function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
}