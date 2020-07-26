package com.mzy.miaosha.vo;

import com.mzy.miaosha.validator.isMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginVo {

    /*用户登录的手机号*/
    @NotNull
    @isMobile
    String mobile;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*用户md5后的密码*/
    @NotNull
    String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public String toString() {
        return "user mobile:" + this.mobile + ", " + "user password:" + this.password;
    }
}
