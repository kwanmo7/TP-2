package salaba.service;

import java.util.List;
import salaba.vo.BoardFile;
import salaba.vo.Community;

public interface CommunityService {
  void add(Community community);

  List<Community> list(int category, int pageNo, int pageSize);

  Community get(int no);

  int update(Community community);

  int delete(int no);

  List<BoardFile> getBoardFiles(int no);

  BoardFile getBoardFile(int fileNo);

  int deleteBoardFile(int fileNo);

  int countAll(int category);

}
