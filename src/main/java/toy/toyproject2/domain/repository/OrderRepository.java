package toy.toyproject2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.toyproject2.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
