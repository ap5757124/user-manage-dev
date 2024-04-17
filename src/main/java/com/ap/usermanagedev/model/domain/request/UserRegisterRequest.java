package com.ap.usermanagedev.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 7110404918947613548L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
