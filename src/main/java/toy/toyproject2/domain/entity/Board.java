package toy.toyproject2.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.toyproject2.domain.entity.auditing.AuditingDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "title", "content"})
public class Board extends AuditingDate {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        addMember(member);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
