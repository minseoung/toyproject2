package toy.toyproject2.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import toy.toyproject2.domain.entity.auditing.AuditingDate;
import toy.toyproject2.exception.CanNotCancelOrderException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "member", "orderStatus", "delivery", "orderItems"})
public class Order extends AuditingDate {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Member member, Delivery delivery, OrderItem... orderItems) {
        addMember(member);
        addDelivery(delivery);
        this.orderStatus = OrderStatus.ORDER;
        for (OrderItem orderItem : orderItems) {
            addOrderItem(orderItem);
        }
        if (member.getMoney() < getTotalPrice()) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        member.expend(getTotalPrice());
        for (OrderItem orderItem : orderItems) {
            orderItem.getItem().getMember().profit(orderItem.getTotalPrice());
        }
    }

    public void addMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.addOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    public void cancel() {
        if (this.getDelivery().getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new CanNotCancelOrderException("이미 배송된 상품은 취소가 불가능합니다.");
        }
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
            orderItem.getItem().getMember().returnProfit(orderItem.getTotalPrice());
        }
        member.cancelExpand(getTotalPrice());
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
