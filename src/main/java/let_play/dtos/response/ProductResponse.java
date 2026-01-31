package let_play.dtos.response;

import java.time.LocalDateTime;

import let_play.entities.Product;
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

    public static ProductResponse fromEntity(Product product) {
        ProductResponse productResp = new ProductResponse();
        productResp.id = product.getId();
        productResp.name = product.getName();
        productResp.description = product.getDescription();
        productResp.price = product.getPrice();
        productResp.userId = product.getUserId();
        productResp.createdAt = product.getCreatedAt();
        productResp.updatedAt = product.getUpdatedAt();
        return productResp;
    }
}
