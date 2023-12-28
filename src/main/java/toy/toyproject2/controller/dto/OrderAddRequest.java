package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Address;

@Data
@NoArgsConstructor
public class OrderAddRequest {
    private Address address;
    private int orderCount;

    public OrderAddRequest(Address address, int orderCount) {
        this.address = address;
        this.orderCount = orderCount;
    }
}
