package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.BoardFileDao;
import salaba.dao.CommunityDao;
import salaba.service.CommunityService;
import salaba.vo.BoardFile;
import salaba.vo.Community;

@RequiredArgsConstructor
@Service
public class DefaultCommunityService implements CommunityService {

  private final CommunityDao communityDao;
  private final BoardFileDao boardFileDao;

  @Transactional
  @Override
  public void add(Community community) {
    communityDao.add(community);
    if (community.getFileList() != null && community.getFileList().size() > 0) {
      for (BoardFile boardFile : community.getFileList()) {
        boardFile.setCommunityNo(community.getNo());
      }
      boardFileDao.addAll(community.getFileList());
    }
  }

  @Override
  public List<Community> list(int category, int pageNo, int pageSize) {
    return communityDao.findAll(category, pageSize * (pageNo - 1), pageSize);
  }

  @Override
  public Community get(int no) {
    return communityDao.findBy(no);
  }

  @Transactional
  @Override
  public int update(Community community) {
    int count = communityDao.update(community);
    if (community.getFileList() != null && community.getFileList().size() > 0) {
      for (BoardFile boardFile : community.getFileList()) {
        boardFile.setCommunityNo(community.getNo());
      }
      boardFileDao.addAll(community.getFileList());
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int no) {
    boardFileDao.deleteAll(no);
    return communityDao.delete(no);
  }

  @Override
  public List<BoardFile> getBoardFiles(int no) {
    return boardFileDao.findAllByCommnunityNo(no);
  }

  @Override
  public BoardFile getBoardFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public int deleteBoardFile(int fileNo) {
    return boardFileDao.delete(fileNo);
  }

  @Override
  public int countAll(int category) {
    return communityDao.countAll(category);
  }
}
