package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Community;

@Mapper
public interface CommunityDao {

  void add(Community community);

  int delete(int no);

  List<Community> findAll(
      @Param("category") int category,
      @Param("offset") int offset,
      @Param("rowCount") int rowCount);

  Community findBy(int no);

  int update(Community community);

  int countAll(int category);
}
