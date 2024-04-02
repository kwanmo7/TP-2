package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.Data;

@Data
public class Theme implements Serializable { // 테마
  private static final long serialVersionUID = 100L;

  private int theme_no; // 테마번호
  
  private String themeName; // 테마명
}
