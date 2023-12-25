package toy.toyproject2.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import toy.toyproject2.domain.entity.Address;

@Data
public class MemberAddRequest {

    @NotBlank
    private String loginid;
    @NotBlank
    private String pw;
    @NotBlank
    private String nickname;
    @NotBlank
    private String name;
    @NotBlank
    private int age;
    @NotBlank
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
