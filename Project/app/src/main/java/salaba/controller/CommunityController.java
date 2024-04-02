package salaba.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.CommunityService;
import salaba.service.StorageService;
import salaba.vo.BoardFile;
import salaba.vo.Community;

@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {  // 정보공유 및 자유게시판 컨트롤러

  private static final Log log = LogFactory.getLog(CommunityController.class);
  private final CommunityService communityService;
  private final StorageService storageService;
  private String uploadDir = "community/";

  @Value("${ncp.ss.bucketname}")
  private String bucketName;

  @GetMapping("form")
  public void form(int category, Model model) throws Exception {
    model.addAttribute("communityName", category == 1 ? "정보공유게시판" : "자유게시판");
    model.addAttribute("category", category);
  }

  @PostMapping("add")
  public String add(
      Community community,
      MultipartFile[] boardFiles,
      HttpSession session) throws Exception {

//    Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//    community.setWriter(loginUser);

    ArrayList<BoardFile> files = new ArrayList<>();
    if (community.getCategory() == 1) {
      for (MultipartFile file : boardFiles) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        files.add(BoardFile.builder().filePath(filename).build());
      }
    }
    if (files.size() > 0) {
      community.setFileList(files);
    }

    communityService.add(community);

    return "redirect:list?category=" + community.getCategory();

  }

  @GetMapping("list")
  public void list(
      int category,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      Model model) throws Exception {

    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = communityService.countAll(category);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("communityName", category == 1 ? "정보공유게시판" : "자유게시판");
    model.addAttribute("category", category);
    model.addAttribute("list", communityService.list(category, pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("view")
  public void view(int category, int no, Model model) throws Exception {
    Community community = communityService.get(no);
    if (community == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    model.addAttribute("communityName", category == 1 ? "정보공유게시판" : "자유게시판");
    model.addAttribute("category", category);
    model.addAttribute("community", community);
  }

  @PostMapping("update")
  public String update(
      Community community,
      MultipartFile[] boardFiles,
      HttpSession session) throws Exception {

 //   Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

    Community old = communityService.get(community.getNo());
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
//    else if (old.getWriter().getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

      ArrayList<BoardFile> files = new ArrayList<>();
      if (community.getCategory() == 1) {
        for (MultipartFile file : boardFiles) {
          if (file.getSize() == 0) {
            continue;
          }
          String filename = storageService.upload(this.bucketName, this.uploadDir, file);
          files.add(BoardFile.builder().filePath(filename).build());
        }
      }
      if (files.size() > 0) {
        community.setFileList(files);
      }

      communityService.update(community);

      return "redirect:list?category=" + community.getCategory();

    }

  @GetMapping("delete")
  public String delete(int category, int no, HttpSession session) throws Exception {

//    Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

    Community community = communityService.get(no);
    if (community == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
//    else if (community.getWriter().getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

    List<BoardFile> files = communityService.getBoardFiles(no);

    communityService.delete(no);

    for (BoardFile file : files) {
      storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());
    }

    return "redirect:list?category=" + category;
  }

  @GetMapping("file/delete")
  public String fileDelete(int category, int no, HttpSession session) throws Exception {

//    Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

    BoardFile file = communityService.getBoardFile(no);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

//    Member writer = communityService.get(file.getCommunityNo()).getWriter();
//    if (writer.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

    communityService.deleteBoardFile(no);

    storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());

    return "redirect:../view?category=" + category + "&no=" + file.getCommunityNo();
  }
}