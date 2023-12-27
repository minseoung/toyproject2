package toy.toyproject2.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Address;

@Data
@NoArgsConstructor
public class MemberAddRequest {

    @NotBlank
    private String loginid;
    @NotBlank
    private String pw;
    @NotBlank
    private String nickname;
    @NotBlank
    private String name;
    @NotNull
    private int age;
    private Address address;

    public MemberAddRequest(String loginid, String pw, String nickname, String name, int age, Address address) {
        this.loginid = loginid;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
