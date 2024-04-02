package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.BoardFile;

@Mapper
public interface BoardFileDao { // 게시판 첨부파일 인터페이스

  void add(BoardFile file);

  int addAll(List<BoardFile> files);

  int delete(int no);

  int deleteAll(int communityNo);

  BoardFile findByNo(int no);

  List<BoardFile> findAllByCommnunityNo(int communityNo);
  List<BoardFile> findAllByReviewNo(int reviewNo);
}
