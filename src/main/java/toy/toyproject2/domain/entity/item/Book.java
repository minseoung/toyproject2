package toy.toyproject2.domain.entity.item;

import jakarta.persistence.Entity;
import lombok.Getter;
import toy.toyproject2.domain.entity.Member;

@Entity
@Getter
public class Book extends Item{
    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity, Member member, String author, String isbn) {
        super(name, price, stockQuantity, member);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public void edit(String name, int price, int stockQuantity, String author, String isbn) {
        super.edit(name, price, stockQuantity, author, isbn);
        this.author = author;
        this.isbn = isbn;
    }
}
