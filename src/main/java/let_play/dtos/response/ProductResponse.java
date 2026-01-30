package let_play.dtos.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
