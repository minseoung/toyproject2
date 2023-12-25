package toy.toyproject2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
}
