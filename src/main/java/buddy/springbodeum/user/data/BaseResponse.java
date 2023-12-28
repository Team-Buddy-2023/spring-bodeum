package buddy.springbodeum.user.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private HttpStatus httpStatus;  // 상태 코드 메세지

    private String message; // 에러 설명

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

}
