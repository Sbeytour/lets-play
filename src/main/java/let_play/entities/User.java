package let_play.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document(collection = "User")
public class User {

    @Id
    private String id;

    @NotBlank(message = "the name is required")
    private String name;

    @Indexed(unique = true)
    @Email(message = "Email Non valid format")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role = Role.USER;

    @CreatedDate
    private LocalDate createdAt;
}
