package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Point_history;

@Mapper
public interface Point_historyDao {

  List<Point_history> findAll(Point_history pointHistory);

}