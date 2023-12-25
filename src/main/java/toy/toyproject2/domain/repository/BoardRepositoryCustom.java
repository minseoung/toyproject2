package toy.toyproject2.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.toyproject2.controller.dto.BoardSearchCond;
import toy.toyproject2.domain.entity.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> searchBoards(BoardSearchCond cond, Pageable pageable);
}
