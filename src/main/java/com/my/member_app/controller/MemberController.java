package com.my.member_app.controller;

import com.my.member_app.dto.MemberDto;
import com.my.member_app.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

   @GetMapping("/view")
   public String showAllMember(Model model){
       List<MemberDto> dtoList = memberService.findAll();
       model.addAttribute("title", "회원정보");
       model.addAttribute("lists", dtoList);
       return "showMember";
   }
    @GetMapping("/insertForm")
    public String insertForm(Model model){
        model.addAttribute("dto", new MemberDto());
        return "insertMember";
    }
    @PostMapping("/insert")
    public String insertMember(@ModelAttribute("dto") MemberDto dto,
                               RedirectAttributes redirectAttributes){
       // redirect: /member/view를 Get으로 다시 호출
        log.info("result : " + dto);
        // insert 서비스 호출
        memberService.insert(dto);
        redirectAttributes.addFlashAttribute("message", "등록이 완료 되었습니다");
       return "redirect:/member/view";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("deleteId")Long deleteId,
                         RedirectAttributes redirectAttributes){
       log.info("======= deleteId = " + deleteId);
       memberService.delete(deleteId);
       redirectAttributes.addFlashAttribute("message", "정상적으로 삭제 되었습니다.");
       return "redirect:/member/view";
    }
}
