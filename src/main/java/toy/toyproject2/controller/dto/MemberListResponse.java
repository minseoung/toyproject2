package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {
    private Long id;
    private String loginid;
    private String pw;
    private String nickname;

    public MemberListResponse(Member m) {
        this.id = m.getId();
        this.loginid = m.getLoginid();
        this.pw = m.getPw();
        this.nickname = m.getNickname();
    }
}
