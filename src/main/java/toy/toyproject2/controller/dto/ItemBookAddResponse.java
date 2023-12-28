package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.item.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemBookAddResponse {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    private Long memberId;
    private String nickname;

    public ItemBookAddResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();

        this.memberId = book.getMember().getId();
        this.nickname = book.getMember().getNickname();
    }
}
