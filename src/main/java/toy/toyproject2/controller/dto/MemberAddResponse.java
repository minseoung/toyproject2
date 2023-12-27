package toy.toyproject2.controller.dto;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Grade;
import toy.toyproject2.domain.entity.Member;

@Data
@NoArgsConstructor
public class MemberAddResponse {
    private Long id;
    private String loginid;
    private String pw;
    private String nickname;
    private Grade grade;
    private String name;
    private int age;
    private Address address;
    private int money;
    public MemberAddResponse(Member member) {
        id = member.getId();
        loginid = member.getLoginid();
        pw = member.getPw();
        nickname = member.getNickname();
        grade = member.getGrade();
        name = member.getName();
        age = member.getAge();
        address = member.getAddress();
        money = member.getMoney();
    }
}
