package toy.toyproject2.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemBookEditRequest {
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public ItemBookEditRequest(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
