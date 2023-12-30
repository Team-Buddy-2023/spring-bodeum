package buddy.springbodeum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserLoginResponseDTO {
    private HttpStatus status;
    private String token;
    private Long userId;
}
