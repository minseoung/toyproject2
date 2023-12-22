package toy.toyproject2.domain.entity.item;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Book extends Item{
    private String author;
    private String isbn;
}
