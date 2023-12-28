package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.OrderAddRequest;
import toy.toyproject2.domain.entity.*;
import toy.toyproject2.domain.entity.item.Item;
import toy.toyproject2.domain.repository.ItemRepository;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.domain.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long order(OrderAddRequest addRequest, Long memberId, Long itemId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();

            Delivery delivery = null;
            if (addRequest.getAddress() == null) {
                delivery = new Delivery(findMember.getAddress());
            } else {
                delivery = new Delivery(addRequest.getAddress());
            }

            if (optionalItem.isPresent()) {
                Item findItem = optionalItem.get();

                OrderItem orderItem = new OrderItem(findItem.getPrice(), addRequest.getOrderCount(), findItem);
                Order order = new Order(findMember, delivery, orderItem);

                orderRepository.save(order);
                return order.getId();
            }
        } return null;
    }

    public void cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order findOrder = optionalOrder.get();
            findOrder.cancel();
        }
    }

    public void cancelOrderV2(Long orderId, Long loginMemberId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order findOrder = optionalOrder.get();
            if (!findOrder.getMember().getId().equals(loginMemberId)) {
                throw new RuntimeException("본인이 주문한 상품만 열람 및 관리가능합니다.");
            }
            findOrder.cancel();
        }
    }

    public Optional<Order> findOne(Long id) {
        return orderRepository.findById(id);
    }

    public Order findOrder(Long id, Long loginMemberId) {
        Optional<Order> optionalOrder = orderRepository.findOrderById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getMember().getId().equals(loginMemberId)) {
                throw new RuntimeException("본인이 주문한게 아니면 열람 및 관리가 불가능합니다.");
            }
            return order;
        } else {
            throw new RuntimeException("해당 주문은 존재하지않습니다.");
        }
    }

    public Page<Order> findOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public List<Order> findOrderList() {
        List<Order> orders = orderRepository.findOrderList();
        return orders;
    }
}
