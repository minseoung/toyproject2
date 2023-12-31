package toy.toyproject2.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSearchCond {
    private String title;
    private String writer;

    public BoardSearchCond(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }
}
