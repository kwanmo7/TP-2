package salaba.controller;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import salaba.service.RentalHomeService;
import salaba.vo.Member;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomePhoto;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rentalHome")
public class RentalHomeController {

  private final static Log log = LogFactory.getLog(RentalHomeController.class);
  private final RentalHomeService rentalHomeService; // RentalHomeService

  @GetMapping("/search")
  public String rentalHomeMain( HttpSession httpSession, Model model,
      @RequestParam( value = "regionName", defaultValue = "all") String regionName,
      @RequestParam( value = "checkInDate", required = false)
      @DateTimeFormat( pattern = "yyyy-MM-dd") Date checkInDate,
      @RequestParam( value = "checkOutDate", required = false )
      @DateTimeFormat( pattern = "yyyy-MM-dd") Date checkOutDate,
      @RequestParam( value = "capacity", defaultValue = "1") int capacity) throws Exception{ // 메인화면
    Member loginUser = (Member) httpSession.getAttribute("loginUser");

    if(httpSession.getAttribute("themeList") == null ){
      httpSession.setAttribute("themeList", rentalHomeService.getAllThemes());
    }

    // LogIn User Check
    if(  loginUser == null   && ( regionName.equalsIgnoreCase("all") &&
        checkInDate == null && checkOutDate == null && capacity == 1 ) ){

      // 로그인하지 않은 경우 기본 숙소 목록 출력 검색 하지 않은 경우
      model.addAttribute("rentalHomeList", rentalHomeService.getRentalHomeMain());
    }
    else if(
         loginUser != null && loginUser.getThemes() != null &&
         regionName.equalsIgnoreCase("all") &&
        checkInDate == null && checkOutDate == null && capacity == 1 ){
      // 로그인한 유저 중 선호 사항을 고른 유저의 경우
      // 선호사항으로 숙소 목록 출력
      model.addAttribute("rentalHomeList",
          rentalHomeService.getRentalHomeMainForLoginUser(loginUser.getThemes()));
    }
    else{
      // 숙소 검색
      model.addAttribute("rentalHomeList",
          rentalHomeService.getRentalHomeConditionSearch(regionName,checkInDate,checkOutDate,capacity));

    }
    return "main";
  }

  @GetMapping("/theme")
  public String rentalHomeTheme( @RequestParam( value = "themeName") String themeName, Model model ){
    log.debug(String.format("테마명 : %s", themeName));
    List<RentalHome> rentalHome = rentalHomeService.getRentalHomeThemeSearch(themeName);
    model.addAttribute("rentalHomeList", rentalHome);

    for( RentalHome rh : rentalHome ){
      log.debug(String.format("rentalHome : %s", rh.getRentalHomeNo()));
      log.debug(String.format("rentalHome : %s", rh.getName()));
      for( RentalHomePhoto rp : rh.getRentalHomePhotos() ){
        log.debug(String.format("rentalHomePhoto : %s", rp.getPhotoNo()));
        log.debug(String.format("rentalHomePhoto : %s", rp.getUuidPhotoName()));
      }
    }

    return "main";
  }

  @GetMapping("/filter")
  public String rentalHomeFilterSearch( Model model,
      @RequestParam Theme theme,
      @RequestParam int minPrice,
      @RequestParam int maxPrice,
      @RequestParam int capacity){
    model.addAttribute("rentalHomeList",
        rentalHomeService.getRentalHomeFilterSearch(theme,minPrice,maxPrice,capacity));

    return "main";
  }

  @GetMapping("/view")
  public void rentalHomeView( int rentalHomeNo, Model model){ // 숙소 상세 조회
    model.addAttribute("rentalHome", rentalHomeService.getRentalHomeDetailView(rentalHomeNo));
    model.addAttribute("rentalHomeReview", rentalHomeService.getRentalHomeReviewList(rentalHomeNo));
    model.addAttribute("rentalHomePhoto", rentalHomeService.getRentalHomePhotos(rentalHomeNo));
    model.addAttribute("rentalHomeFacility", rentalHomeService.getRentalHomeFacilities(rentalHomeNo));
  }


//  @PostMapping("")
  public String rentalHomeReviewAdd( RentalHomeReview rentalHomeReview) {
    rentalHomeService.addRentalHomeReview(rentalHomeReview); // 숙소 리뷰 작성
    return ""; // 작성전 페이지로 돌아가기
  }

//  @GetMapping("")
  public void rentalHomeReviewList( int rentalHomeNo, Model model ){
    // 숙소 리뷰 조회
    model.addAttribute("rentalHomeReviewList",
        rentalHomeService.getRentalHomeReviewList(rentalHomeNo));
  }

  @PostMapping("/report")
  public String rentalHomeReportAdd( @RequestBody RentalHomeReport rentalHomeReport){
    rentalHomeService.addRentalHomeReport(rentalHomeReport); // 숙소 신고

    return "redirect:view?rentalHomeNo="+ rentalHomeReport.getRentalHomeNo(); // 작성전 페이지로 돌아가기
  }

}
