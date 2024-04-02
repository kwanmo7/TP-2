package salaba.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.ReviewService;
import salaba.service.StorageService;
import salaba.vo.BoardFile;
import salaba.vo.Review;


@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {  // 후기 게시판 컨트롤러 - CRUD
  private static final Log log = LogFactory.getLog(ReviewController.class); //로그
  private final ReviewService reviewService;

  @Value("${ncp.ss.bucketname}")
  private String bucketName;

  @GetMapping("form")  // form
  public void form() throws Exception {
  }

  @PostMapping("add")
  public String add(Review review, HttpSession session) throws Exception {  // 입력: Create

  //  Member loginUser = (Member) session.getAttribute("loginUser");  // 회원 정보 - 권한 설정
  //  if (loginUser == null) {      // 로그인 요청
  //    throw new Exception("로그인하시기 바랍니다!");
  //  }
  //  review.setWriter(loginUser);

    reviewService.add(review);
    return "redirect:list";
  }

  @GetMapping("list")
  public void list(     // 보기 : Read
      int category,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      Model model) throws Exception {

    if (pageSize < 3 || pageSize > 20) {  // 페이지 규정 설정
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = reviewService.countAll();
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("list", reviewService.list(pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("view")
  public void view(int no, Model model) throws Exception {  // 상세 보기 : Read
    Review review = reviewService.get(no);
    if (review == null) {
      throw new Exception("후기 번호가 유효하지 않습니다.");
    }

    model.addAttribute("review", review);
  }

  @PostMapping("update")
  public String update(Review review, HttpSession session) throws Exception { // 수정, 변경 : Update

//    Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

    Review old = reviewService.get(review.getNo());
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
//    else if (old.getWriter().getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

    reviewService.update(review);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no, HttpSession session) throws Exception {  // 삭제 : Delete

//    Member loginUser = (Member) session.getAttribute("loginUser");
//   if (loginUser == null) {
//     throw new Exception("로그인하시기 바랍니다!");   }

    Review review = reviewService.get(no);
    if (review == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
//    else if (review.getWriter().getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

    reviewService.delete(no);
    return "redirect:list";
  }

}
