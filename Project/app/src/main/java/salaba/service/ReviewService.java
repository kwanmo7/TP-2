package salaba.service;

import java.util.List;
import salaba.vo.Review;

public interface ReviewService {
  void add(Review review);

  List<Review> list(int pageNo, int pageSize);

  Review get(int no);

  int update(Review review);

  int delete(int no);

  int countAll();
}
