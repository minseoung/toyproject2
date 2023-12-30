package toy.toyproject2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Board;
import toy.toyproject2.domain.entity.Grade;
import toy.toyproject2.domain.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String loginid;
    private String pw;
    private String nickname;
    private Grade grade;
    private String name;
    private int age;
    private Address address;
    private int money;

    private List<BoardDto> boards;

    public MemberResponse(Member member) {
        id = member.getId();
        loginid = member.getLoginid();
        pw = member.getPw();
        nickname = member.getNickname();
        grade = member.getGrade();
        name = member.getName();
        age = member.getAge();
        address = member.getAddress();
        money = member.getMoney();
        boards = member.getBoards().stream()
                .map(board -> new BoardDto(board)).collect(Collectors.toList());
    }

    @Data
    static class BoardDto {
        private Long boardId;
        private String boardTitle;

        public BoardDto(Board board) {
            this.boardId = board.getId();
            this.boardTitle = board.getTitle();
        }
    }

}
