package let_play.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int statusCode;
    private String error;
    private Object details;

    public ErrorResponse(int status, String error, String message) {
        this(message, status, error, null);
    }
}
