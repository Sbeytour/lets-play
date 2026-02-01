package let_play.dtos.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    String name;

    @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
    String description;

    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    @DecimalMax(value = "1000000.00", message = "Price cannot exceed 1,000,000.00")
    Double price;
}
