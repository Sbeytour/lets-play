package let_play.dtos.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int statusCode;
    private String error;
}
