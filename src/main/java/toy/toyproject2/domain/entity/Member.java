package toy.toyproject2.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.toyproject2.domain.entity.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "constraintName", columnNames = {"loginid", "nickname"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "nickname", "address", "money"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginid;
    private String pw;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.MEMBER;
    private String name;
    private int age;
    @Embedded
    private Address address;
    private int money = 100000;
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    public Member(String loginid, String pw, String nickname, String name, int age, Address address) {
        this.loginid = loginid;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.address = address;

        if (nickname.equals("운영자")) {
            grade = Grade.ADMIN;
            money = 100000;
        }
    }

    public void expend(int totalPrice) {
        money -= totalPrice;
    }

    public void cancelExpand(int totalPrice) {
        money += totalPrice;
    }

    public void profit(int sellPrice) {
        money += sellPrice;
    }

    public void returnProfit(int sellPrice) {
        money -= sellPrice;
    }
}
