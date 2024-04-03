package salaba.dao;

import java.sql.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

@Mapper
public interface RentalHomeDao {
  
  // 숙소 조회 메인(추천수)
  RentalHome rentalHomeDefaultSelect();

  // 숙소 조회 메인(로그인 유저 선호 사항으로 목록 출력)
  RentalHome rentalHomeSelectForMember( List<Theme> themes );

  // 숙소 테마별 조회
  RentalHome rentalHomeThemeSelect( @Param("theme_name") String themeName);

  // 숙소 지역,기간,인원수 조회
  RentalHome rentalHomeConditionSelect(
      @Param("region_name") String region_name,
      @Param("check_in") Date checkIn,
      @Param("check_out") Date checkOut,
      @Param("capacity") int capacity );
  
  // 숙소 상세 조회
  RentalHome rentalHomeDetailSelect( @Param("rental_home_no") int rentalHomeNo );
  
  // 숙소 리뷰 조회
  List<RentalHomeReview> rentalHomeReviewSelect( @Param("rental_home_no") int rentalHomeNo );

  // 숙소 리뷰 작성
  void rentalHomeReviewAdd( RentalHomeReview rentalHomeReview );

  // 숙소 신고
  void rentalHomeReportAdd( RentalHomeReport rentalHomeReport);

}
