package toy.toyproject2.domain.entity.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.toyproject2.domain.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@ToString(of = {"id", "name", "price", "stockQuantity"})
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Item(String name, int price, int stockQuantity, Member member) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        addMember(member);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getItems().add(this);
    }


    public void edit(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void removeStock(int orderCount) {
        this.stockQuantity -= orderCount;
    }

    public void addStock(int orderCount) {
        this.stockQuantity += orderCount;
    }
}
