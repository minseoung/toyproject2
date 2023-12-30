package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.BoardAddRequest;
import toy.toyproject2.controller.dto.BoardEditRequest;
import toy.toyproject2.controller.dto.BoardSearchCond;
import toy.toyproject2.domain.entity.Board;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.repository.BoardRepository;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.exception.NotExistMemberException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long post(BoardAddRequest addRequest, Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Board board = new Board(addRequest.getTitle(), addRequest.getContent(), optionalMember.get());
            boardRepository.save(board);
            return board.getId();
        } else {
            throw new NotExistMemberException("존재하는 회원입니다.");
        }
    }

    public Optional<Board> findOne(Long id) {
        return boardRepository.findById(id);
    }

    public Board findBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return board;
        } else {
            throw new RuntimeException("존재하지 않는 게시물입니다.");
        }
    }

    public List<Board> findBoards(BoardSearchCond searchCond, Pageable pageable) {
        return boardRepository.searchBoards(searchCond, pageable);
    }

    public void edit(BoardEditRequest editRequest, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board findBoard = optionalBoard.get();
            findBoard.edit(editRequest.getTitle(), editRequest.getContent());
        }
    }

    public void editV2(BoardEditRequest editRequest, Long boardId, Long memberId) {
        Optional<Board> optionalBoard = boardRepository.findBoardWithMemberById(boardId);
        if (optionalBoard.isPresent()) {
            Board findBoard = optionalBoard.get();
            if (!findBoard.getMember().getId().equals(memberId)) {
                throw new RuntimeException("본인이 올린 게시물만 수정가능합니다.");
            }
            findBoard.edit(editRequest.getTitle(), editRequest.getContent());
        }
    }

    public List<Board> findBoardList() {
        List<Board> boardList = boardRepository.findBoardList();
        return boardList;
    }

    public List<Board> findBoardListV2(BoardSearchCond cond, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Board> boards = boardRepository.searchBoards(cond, pageRequest);
        return boards;
    }

}
