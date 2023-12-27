package toy.toyproject2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.controller.dto.MemberAddResponse;
import toy.toyproject2.controller.dto.MemberLoginRequest;
import toy.toyproject2.controller.dto.Result;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.service.MemberService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/member/join")
    public Result<MemberAddResponse> joinMember(@RequestBody @Validated MemberAddRequest addRequest, BindingResult bindingResult) {
        Long joinedMemberId = memberService.join(addRequest);
        //서비스 계층에서 findOne할때 optional인경우 널이면 어떻게 오류던질지 다 해놓고 반환하는건 Member로 하면 좋을듯 지저분함.
        Optional<Member> optionalMember = memberService.findOne(joinedMemberId);
        MemberAddResponse memberAddResponse = null;
        if (optionalMember.isPresent()) {
            memberAddResponse = optionalMember.map(MemberAddResponse::new).get();
        }
        return new Result<>(memberAddResponse);
    }

    @PostMapping("/member/login")
    public Result<MemberLoginRequest> login(@RequestBody @Validated MemberLoginRequest loginRequest, BindingResult bindingResult,
                                            HttpServletRequest request) {
        Long loginMemberId = memberService.login(loginRequest);
        HttpSession session = request.getSession();
        session.setAttribute("loginMemberId", loginMemberId);
        return null;
    }


}
