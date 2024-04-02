package salaba.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Mybatis 가 사용할 기본 생성자를 만들어야 한다.
@AllArgsConstructor
@Builder
@Data
public class BoardFile { // 첨부파일

  private int no; // 파일 번호
  private String filePath; // 파일 경로
  private int communityNo; // 정보공유,자유 게시판 번호
  private int reviewNo; // 후기 게시판 번호
  private char oriFileName; // 원본 파일명
  private char uuidFileName; // UUID 파일명

}
