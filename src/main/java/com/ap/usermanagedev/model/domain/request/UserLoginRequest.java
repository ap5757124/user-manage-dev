package com.ap.usermanagedev.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 699883264411606010L;
    private String userAccount;
    private String userPassword;
}
