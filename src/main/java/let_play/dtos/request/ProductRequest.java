package let_play.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {
    
    @NotBlank(message="Product name cannot be empty")
    private String name;

    @NotBlank(message="Product description cannot be empty")
    private String description;
    
    @NotBlank(message="Product price cannot be empty")
    @Min(value = 0, message = "Price cannot be negative")
    private String Price;
}
