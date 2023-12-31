package buddy.springbodeum.user.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseCode {

    /**
     * 200 OK : 요청 성공
     */
    OK(HttpStatus.OK, "요청 성공하였습니다."),
    /**
     * 404 NOT FOUND
     */
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자 등록에 실패했습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다.");

    private HttpStatus httpStatus;
    private String message;
}
