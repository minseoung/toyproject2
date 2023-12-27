package toy.toyproject2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.toyproject2.controller.dto.*;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.service.MemberService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/member/join")
    public Result<MemberAddResponse> joinMember(@RequestBody @Validated MemberAddRequest addRequest) {
        Long joinedMemberId = memberService.join(addRequest);
        Member findMember = memberService.findMember(joinedMemberId);
        return new Result<>(new MemberAddResponse(findMember));
    }

    @PostMapping("/member/login")
    public Result<MemberLoginResponse> login(@RequestBody @Validated MemberLoginRequest loginRequest, HttpServletRequest request) {
        Long loginMemberId = memberService.login(loginRequest);
        request.getSession().setAttribute("loginMemberId", loginMemberId);

        Member findMember = memberService.findMember(loginMemberId);
        return new Result<>(new MemberLoginResponse(findMember.getNickname()));
    }


}
