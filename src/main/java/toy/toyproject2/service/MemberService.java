package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.controller.dto.MemberListRequest;
import toy.toyproject2.controller.dto.MemberLoginRequest;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.exception.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(MemberAddRequest addRequest) {
        validateDuplicateMember(addRequest.getLoginid(), addRequest.getNickname());
        Member member = new Member(addRequest.getLoginid(), addRequest.getPw(), addRequest.getNickname(), addRequest.getName(), addRequest.getAge(), addRequest.getAddress());
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }

    public Member findMember(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member;
        } else {
            throw new NotExistMemberException("존재하지 않는 회원입니다.");
        }
    }

    public Page<Member> findMemberList(MemberListRequest listRequest) {
        PageRequest pageRequest = PageRequest.of(listRequest.getPage(), listRequest.getSize(), Sort.by(Sort.Direction.ASC, "id"));
        return memberRepository.findAll(pageRequest);
    }

    public Page<Member> findMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Long login(MemberLoginRequest loginRequest) {
        log.info("로그인 로직 실행");
        Member findMember = memberRepository.findByLoginid(loginRequest.getLoginid());
        if (findMember == null || !findMember.getPw().equals(loginRequest.getPw())) {
            throw new LoginFailedException("아이디 또는 비밀번호가 올바르지 않습니다.");
        } else {
            return findMember.getId();
        }
    }

    public void validateDuplicateMember(String loginid, String nickname) {
        log.info("회원가입 중복 검증 시작");
        Member findMemberByloginid = memberRepository.findByLoginid(loginid);
        if (findMemberByloginid != null) {
            log.info("아이디 중복 오류 발생");
            throw new DuplicatedLonginIdException("이미 존재하는 아이디 입니다.");
        }
        Member findMemberByNickname = memberRepository.findByNickname(nickname);
        if (findMemberByNickname != null) {
            log.info("닉네임 중복 오류 발생");
            throw new DuplicatedNicknameException("이미 사용중인 닉네임 입니다.");
        }
    }
}
