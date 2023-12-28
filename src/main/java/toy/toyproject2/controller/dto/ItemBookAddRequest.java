package toy.toyproject2.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemBookAddRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;

    public ItemBookAddRequest(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
