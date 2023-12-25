package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            return null;
        }
    }

    public Optional<Board> findOne(Long id) {
        return boardRepository.findById(id);
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

}
