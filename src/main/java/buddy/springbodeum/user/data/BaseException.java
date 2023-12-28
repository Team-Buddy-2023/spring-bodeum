package buddy.springbodeum.user.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    public final BaseResponseCode baseResponseCode;
}

