package toy.toyproject2.controller.dto;

import lombok.Data;

@Data
public class BoardEditRequest {
    private String title;
    private String content;

    public BoardEditRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
