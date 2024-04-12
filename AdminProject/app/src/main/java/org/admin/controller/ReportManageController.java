package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.MemberService;
import org.admin.util.ReportType;
import org.admin.domain.Report;
import org.admin.service.RentalReportService;
import org.admin.service.TextReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportManageController {
    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final RentalReportService rentalReportService;
    private final TextReportService textReportService;
    private final MemberService memberService;
    @GetMapping("report/list")
    public String reportList(@RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        log.debug("관리자 - report/list");
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        switch (menu) {
            case 1:
                model.addAttribute("list", rentalReportService.getAll());
                model.addAttribute("menuName", "숙소 신고내역");
                break;
            case 2:
                model.addAttribute("list", textReportService.getAllBy(ReportType.BOARD.getValue()));
                model.addAttribute("menuName", "게시글 신고내역");
                break;
            case 3:
                // 댓글과 답글리스트를 합쳐서 model에 담는다.
                List<Report> comments = textReportService.getAllBy(ReportType.COMMENT.getValue());
                List<Report> replies = textReportService.getAllBy(ReportType.REPLY.getValue());
                comments.addAll(replies);
                model.addAttribute("list", comments);
                model.addAttribute("menuName", "댓글 신고내역");
                break;
            case 4:
                model.addAttribute("menuName", "1:1 문의내역");
                break;

        }
        return "report/list";
    }


    @GetMapping("report/detail")
    public String reportDetail(@RequestParam("no") int no,
                               @RequestParam("type") String type,
                               @RequestParam("mno") int memberNo,
                               HttpSession session,
                               Model model) {

        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("type", type);
        if (type.equals("0")) {
            Report report = textReportService.getBy(type, no, memberNo);
            log.debug(report);
            model.addAttribute("report", report);
            model.addAttribute("name","게시글 신고");
        } else if (type.equals("1")|| type.equals("2")) {
            Report report = textReportService.getBy(type, no, memberNo);
            log.debug(report);
            model.addAttribute("report", report);
            model.addAttribute("name", "댓글 신고");
        } else {
            Report report = rentalReportService.get(no, memberNo);
            log.debug("abcdefg: " + report);
            model.addAttribute("report", report);
            model.addAttribute("name", "숙소 신고");
        }
        return "report/detail";

    }

    @Transactional
    @PostMapping("report/update")
    public String dealReport(@RequestParam("selection") String selection,
                             Report report,
                             HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }

        textReportService.updateState(report.getReportNo());
        memberService.updateWarningCount(report.getReportNo());
        textReportService.updateBoardState(report, selection);


        return "redirect:list";

    }



}