package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Community implements Serializable {
  private static final long serialVersionUID = 100L;

  private int category; // 카테고리
  private int no; // 게시글 번호
  private int headNo; // 말머리 번호
  private String title;  // 제목
  private String content; // 내용
//  private Member writer; // 작성자
  private Date createdDate; // 작성일
  private List<BoardFile> fileList; // 첨부파일
  private int fileCount; // 파일수
  private int likeCount; // 추천수
  private int viewCount; // 조회수
  private char state; // 상태
}

