package let_play.dtos.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "Product description cannot be empty")
    private String description;

    @NotBlank(message = "Product price cannot be empty")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    @DecimalMax(value = "1000000.00", message = "Price cannot exceed 1,000,000.00")
    private double Price;
}
