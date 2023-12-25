package toy.toyproject2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.toyproject2.domain.entity.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
