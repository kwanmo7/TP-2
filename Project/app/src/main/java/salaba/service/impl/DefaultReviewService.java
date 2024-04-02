package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.BoardFileDao;
import salaba.dao.ReviewDao;
import salaba.service.ReviewService;
import salaba.vo.BoardFile;
import salaba.vo.Review;

@RequiredArgsConstructor
@Service
public class DefaultReviewService implements ReviewService {

  private final ReviewDao reviewDao;
  private final BoardFileDao boardFileDao;

  @Transactional
  @Override
  public void add(Review review) {
    reviewDao.add(review);
    if (review.getFileList() != null && review.getFileList().size() > 0) {
      for (BoardFile boardFile : review.getFileList()) {
        boardFile.setReviewNo(review.getNo());
      }
      boardFileDao.addAll(review.getFileList());
    }
  }

  @Override
  public List<Review> list(int pageNo, int pageSize) {
    return reviewDao.findAll(pageSize * (pageNo - 1), pageSize);
  }

  @Override
  public Review get(int no) {
    return reviewDao.findBy(no);
  }

  @Transactional
  @Override
  public int update(Review review) {
    int count = reviewDao.update(review);
    if (review.getFileList() != null && review.getFileList().size() > 0) {
      for (BoardFile boardFile : review.getFileList()) {
        boardFile.setReviewNo(review.getNo());
      }
      boardFileDao.addAll(review.getFileList());
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int no) {
    boardFileDao.deleteAll(no);
    return reviewDao.delete(no);
  }

  @Override
  public int countAll() {
    return reviewDao.countAll();
  }
}
