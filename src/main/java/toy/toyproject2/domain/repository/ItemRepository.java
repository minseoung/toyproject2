package toy.toyproject2.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.toyproject2.domain.entity.item.Book;
import toy.toyproject2.domain.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {


    @EntityGraph(attributePaths = {"member"})
    Optional<Item> findItemById(Long id);

    @EntityGraph(attributePaths = {"member"})
    @Query("select i from Item i")
    Page<Item> findItems(Pageable pageable);
}
