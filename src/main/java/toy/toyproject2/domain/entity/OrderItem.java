package toy.toyproject2.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.toyproject2.domain.entity.item.Item;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "orderPrice", "orderCount", "item"})
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    private int orderPrice;
    private int orderCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int orderPrice, int orderCount, Item item) {
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.item = item;
        item.removeStock(orderCount);
    }

    public void cancel() {
        getItem().addStock(orderCount);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getOrderCount();
    }

    public void addOrder(Order order) {
        this.order = order;
    }
}
