package let_play.dtos.response;

import let_play.entities.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private User user;
}
