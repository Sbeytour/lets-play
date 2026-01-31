package let_play.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection="Product")
public class Product {
    @Id
    private String id;

    @NotBlank(message="product name is required")
    private String name;

    @NotBlank(message="product should have a description")
    private String description;

    @NotNull(message="price is required")
    @Min(value = 0, message = "Price must be positive")
    private double price;

    private String userId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private  LocalDateTime updatedAt;
}
