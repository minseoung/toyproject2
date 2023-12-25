package toy.toyproject2.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.BoardAddRequest;
import toy.toyproject2.controller.dto.BoardEditRequest;
import toy.toyproject2.controller.dto.BoardSearchCond;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Board;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Transactional
class BoardServiceTest {
    @Autowired BoardService boardService;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("게시물 올리기")
    void post() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();

        BoardAddRequest addRequest = new BoardAddRequest("첫번째 게시물 테스트", "테스트 입니당.");
        Long savedBoardId = boardService.post(addRequest, savedMemberId);
        em.flush();
        em.clear();

        //when
        Board findBoard = boardService.findOne(savedBoardId).get();
        //then
        assertThat(findBoard.getId()).isEqualTo(savedBoardId);
    }

    @Test
    @DisplayName("게시물 수정하기")
    void edit() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();

        BoardAddRequest addRequest = new BoardAddRequest("첫번째 게시물 테스트", "테스트 입니당.");
        Long savedBoardId = boardService.post(addRequest, savedMemberId);
        em.flush();
        em.clear();
        //when
        BoardEditRequest boardEditRequest = new BoardEditRequest("첫번째 게시물 수정완료", "수정 테스트 입니당.");
        boardService.edit(boardEditRequest, savedBoardId);
        em.flush();
        //then
        Board findBoard = boardService.findOne(savedBoardId).get();
        assertThat(findBoard.getTitle()).isEqualTo("첫번째 게시물 수정완료");
        assertThat(findBoard.getContent()).isEqualTo("수정 테스트 입니당.");
    }

    @Test
    @DisplayName("게시물 목록")
    void boards() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1234"));
        MemberAddRequest request2 = new MemberAddRequest("test2", "1234", "test2", "test2", 20, new Address("서울", "마포대로", "2222"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        em.flush();
        em.clear();

        BoardAddRequest addRequest1 = new BoardAddRequest("test1 첫번째 게시물 테스트", "테스트 입니당.");
        BoardAddRequest addRequest2 = new BoardAddRequest("test1 두번째 게시물 테스트", "테스트 입니당.");
        BoardAddRequest addRequest3 = new BoardAddRequest("test1 세번째 게시물 테스트", "테스트 입니당.");
        BoardAddRequest addRequest4 = new BoardAddRequest("test2 첫번째 게시물 테스트", "테스트 입니당.");
        BoardAddRequest addRequest5 = new BoardAddRequest("test2 두번째 게시물 테스트", "테스트 입니당.");
        Long savedBoardId1 = boardService.post(addRequest1, savedMemberId1);
        Long savedBoardId2 = boardService.post(addRequest2, savedMemberId1);
        Long savedBoardId3 = boardService.post(addRequest3, savedMemberId1);
        Long savedBoardId4 = boardService.post(addRequest4, savedMemberId2);
        Long savedBoardId5 = boardService.post(addRequest5, savedMemberId2);
        em.flush();
        em.clear();
        //when
        BoardSearchCond boardSearchCond1 = new BoardSearchCond("두번째", null);
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<Board> boards = boardService.findBoards(boardSearchCond1, pageRequest);
        for (Board board : boards) {
            System.out.println("board = " + board);
        }
    }
}