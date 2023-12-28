package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginResponse {
    private Long id;
    private String nickname;
}
