package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Order;
import toy.toyproject2.domain.entity.OrderItem;
import toy.toyproject2.domain.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddResponse {
    private Long id;
    private Long orderMemberId;
    private String orderMember;
    private Address address;
    private List<OrderItemDto> orderItems;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    public OrderAddResponse(Order order) {
        this.id = order.getId();
        this.orderMemberId = order.getMember().getId();
        this.orderMember = order.getMember().getNickname();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getCreatedDate();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class OrderItemDto {
        private Long id;
        private String itemName;
        private int orderPrice;
        private int orderCount;

        public OrderItemDto(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.orderCount = orderItem.getOrderCount();
        }
    }


}
