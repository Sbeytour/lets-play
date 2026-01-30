package let_play.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message="name cannot be empty")
    private String name;

    @NotBlank(message="email cannot be empty")
    @Email(message="invalid email format")
    private String email;

    @NotBlank(message="email cannot be empty")
    @Size(min=6, message="password must be at least 6 characters")
    private String password;
}
