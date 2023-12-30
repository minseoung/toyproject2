package toy.toyproject2.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toy.toyproject2.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{

    @EntityGraph(attributePaths = {"member"})
    Optional<Board> findBoardWithMemberById(Long id);

    @EntityGraph(attributePaths = {"member"})
    @Query("select b from Board b")
    List<Board> findBoardList();
}
