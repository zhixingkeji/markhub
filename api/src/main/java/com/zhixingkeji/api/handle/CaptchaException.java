package com.zhixingkeji.api.handle;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
