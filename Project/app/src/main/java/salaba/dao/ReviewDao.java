package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Review;

@Mapper
public interface ReviewDao {

  void add(Review review);

  int delete(int no);

  List<Review> findAll(
      @Param("offset") int offset,
      @Param("rowCount") int rowCount);

  Review findBy(int no);

  int update(Review review);

  int countAll();
}
