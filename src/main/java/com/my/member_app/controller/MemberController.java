package com.my.member_app.controller;

import com.my.member_app.dto.MemberDto;
import com.my.member_app.dto.SearchDto;
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
    @GetMapping("/update")
    public String updateFormView(Model model,
                                 @RequestParam("updateId")Long updateId,
                                 RedirectAttributes redirectAttributes){
       // 1. 선택한 id를 가져오는지 확인
        log.info("======= updateId = " + updateId);
        // 2. 해당 id를 검색해서 dto 받아온다.
        MemberDto updateDto = memberService.findById(updateId);
        log.info("======= updateDto = " + updateDto);
        // 3. updateDto 비어있는지 확인 -> member/view
        if (updateDto == null){
            redirectAttributes.addFlashAttribute("message", "선택한 데이터가 없습니다.");
            return "redirect:/member/view";
        } else {
            // 4. 모델에 담아서 updateForm에 보낸다.
            model.addAttribute("dto", updateDto);
            return "updateMember";
        }
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("dto")MemberDto dto,
                         RedirectAttributes redirectAttributes){
       log.info("updatedDto : " + dto);
       memberService.insert(dto);
       redirectAttributes.addFlashAttribute("message", "정상적으로 수정 되었습니다.");
       return "redirect:/member/view";
    }

    @GetMapping("/search")
    public String search(SearchDto searchDto, Model model){
       log.info("SearchDto : " + searchDto);
       List<MemberDto> result = memberService.search(searchDto.getType(), searchDto.getKeyword());
       model.addAttribute("lists", result);
       return "showMember";
    }
}
