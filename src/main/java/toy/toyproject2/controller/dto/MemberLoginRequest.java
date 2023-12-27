package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginRequest {
    private String loginid;
    private String pw;
}
