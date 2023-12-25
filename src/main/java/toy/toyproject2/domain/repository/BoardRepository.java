package toy.toyproject2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.toyproject2.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{
}
