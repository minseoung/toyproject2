package toy.toyproject2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.toyproject2.controller.dto.*;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
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
        return new Result<>(new MemberLoginResponse(findMember.getId(), findMember.getNickname()));
    }

    @PostMapping("/member/logout")
    public Result<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new Result<>("로그아웃 되었습니다.");
    }

    @GetMapping("/member/list")
    public Result<Page<MemberListResponse>> list(@RequestBody @Validated MemberListRequest listRequest) {
        Page<Member> memberPage = memberService.findMemberList(listRequest);
        Page<MemberListResponse> result = memberPage.map(MemberListResponse::new);
        return new Result<>(result);
    }

    @GetMapping("/member/{memberId}")
    public Result<MemberAddResponse> member(@PathVariable(name = "memberId") Long memberId) {
        Member findMember = memberService.findMember(memberId);
        return new Result<>(new MemberAddResponse(findMember));
    }

    @GetMapping("/member/listV2")
    public Result<List<MemberResponse>> memberWithBoards() {
        List<Member> members = memberService.findMemberWithBoard();
        List<MemberResponse> collect = members.stream().map(member -> new MemberResponse(member)).collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/member/listV3")
    public Result<Page<MemberResponse>> membersWithBoardsV2(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size) {
        Page<MemberResponse> result = memberService.findMembersWithBoards(page, size);
        return new Result<>(result);
    }

}
