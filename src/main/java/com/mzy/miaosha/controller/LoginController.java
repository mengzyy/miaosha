package com.mzy.miaosha.controller;


import com.mzy.miaosha.result.Result;
import com.mzy.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 控制用户登录的Controller层
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /* slf4j常量*/
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * @return 返回初始化登录页面
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录逻辑主体
     *
     * @param loginVo 用户登录表单信息
     * @return 返回页面信息
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(@Valid LoginVo loginVo) {
        log.info(loginVo.toString());

        return Result.success("success");
    }


}
