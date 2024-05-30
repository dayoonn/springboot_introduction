package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/CreateMemberForm";
    }

    @PostMapping ("/members/new")
    public String create(MemberForm form){
        Member member=new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/"; //홈 화면으로 보냄

    }

    @GetMapping("members")
    public String list(Model model){
        List<Member> members=memberService.findMembers(); //회원 전체 조회
        model.addAttribute("members",members); //모델로 뷰에 넘김
        return "members/memberList";
    }
}
