package buddy.springbodeum.user.base;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    public final BaseResponseCode baseResponseCode;
}

