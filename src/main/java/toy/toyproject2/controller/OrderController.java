package toy.toyproject2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.toyproject2.controller.argumentResolver.Login;
import toy.toyproject2.controller.dto.OrderAddRequest;
import toy.toyproject2.controller.dto.OrderAddResponse;
import toy.toyproject2.controller.dto.OrderCancelResponse;
import toy.toyproject2.controller.dto.Result;
import toy.toyproject2.domain.entity.Order;
import toy.toyproject2.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/item/{itemId}/order")
    public Result<OrderAddResponse> addOrder(@RequestBody @Validated OrderAddRequest addRequest, @Login Long loginMemberId,
                                             @PathVariable(name = "itemId") Long itemId) {
        Long savedOrder = orderService.order(addRequest, loginMemberId, itemId);
        Order order = orderService.findOne(savedOrder).get();
        return new Result<>(new OrderAddResponse(order));
    }

    @PostMapping("/order/{orderId}/cancel")
    public Result<OrderCancelResponse> cancelOrder(@PathVariable(name = "orderId") Long orderId, @Login Long loginMemberId) {
        orderService.cancelOrderV2(orderId, loginMemberId);
        return new Result<>(new OrderCancelResponse(orderId, "주문이 취소되었습니다."));
    }

    @GetMapping("/order/{orderId}")
    public Result<OrderAddResponse> findOrder(@PathVariable(name = "orderId") Long orderId, @Login Long loginMemberId) {
        Order order = orderService.findOrder(orderId, loginMemberId);
        return new Result<>(new OrderAddResponse(order));
    }

    @GetMapping("/order/list")
    public Result<List<OrderAddResponse>> list() {
        List<Order> orders = orderService.findOrderList();
        List<OrderAddResponse> collect = orders.stream().map(order -> new OrderAddResponse(order)).collect(Collectors.toList());
        return new Result<>(collect);
    }
}
