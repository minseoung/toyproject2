package toy.toyproject2.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.exception.DuplicatedLonginIdException;
import toy.toyproject2.exception.DuplicatedNicknameException;
import toy.toyproject2.exception.LoginFailedException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입 성공")
    void join() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        //when
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();
        //then
        Member findMember = memberService.findOne(savedMemberId).get();
        assertThat(findMember.getId()).isEqualTo(savedMemberId);
    }

    @Test
    @DisplayName("회원가입 실패")
    void joinfailed() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();
        //when
        MemberAddRequest requestDupLoginid = new MemberAddRequest("test1", "1234", "test2", "test1", 10, new Address("서울", "마포대로", "1111"));
        MemberAddRequest requestDupNickname = new MemberAddRequest("test3", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        //then
        assertThatThrownBy(() -> {
            memberService.join(requestDupLoginid);
        }).isInstanceOf(DuplicatedLonginIdException.class);
        assertThatThrownBy(() -> {
            memberService.join(requestDupNickname);
        }).isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();
        //when
        Long loginMemberId = memberService.login("test1", "1234");
        //then
        assertThat(loginMemberId).isEqualTo(savedMemberId);
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailed() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();
        //when
        //then
        assertThatThrownBy(() -> {
            Long loginMemberId1 = memberService.login("test2", "1234");
        }).isInstanceOf(LoginFailedException.class);
        assertThatThrownBy(() -> {
            Long loginMemberId2 = memberService.login("test1", "5555");
        }).isInstanceOf(LoginFailedException.class);
    }

    @Test
    @DisplayName("회원 리스트")
    void memberList() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        MemberAddRequest request2 = new MemberAddRequest("test2", "1234", "test2", "test2", 20, new Address("서울", "마포대로", "2222"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        em.flush();
        em.clear();
        //when
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "loginid"));
        Page<Member> memberPage = memberService.findMembers(pageRequest);
        List<Member> members = memberPage.getContent();
        System.out.println("memberPage = " + memberPage);
        for (Member member : members) {
            System.out.println("member = " + member.getLoginid());
        }
    }
}