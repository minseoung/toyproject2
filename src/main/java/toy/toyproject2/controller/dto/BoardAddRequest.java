package toy.toyproject2.controller.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import toy.toyproject2.domain.entity.Member;

@Data
public class BoardAddRequest {
    private String title;
    private String content;

    public BoardAddRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
