package let_play.dtos.response;

import let_play.entities.Role;

import let_play.entities.User;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private Role role;

    public static UserResponse fromEntity(User user) {
        UserResponse userResp = new UserResponse();
        userResp.id = user.getId();
        userResp.name = user.getName();
        userResp.email = user.getEmail();
        userResp.role = user.getRole();

        return userResp;
    }
}
