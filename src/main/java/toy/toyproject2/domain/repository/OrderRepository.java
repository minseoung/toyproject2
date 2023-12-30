package toy.toyproject2.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toy.toyproject2.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"member", "delivery"})
    Optional<Order> findOrderById(Long id);

//    @EntityGraph(attributePaths = {"member", "delivery", "orderItems"})
    @Query("select distinct o from Order o join fetch o.member join fetch o.delivery")
    List<Order> findOrderList();

    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    Page<Order> findOrderListV2(Pageable pageable);
}
